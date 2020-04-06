package model.core.treeinfo.service.impl;

import model.core.treeinfo.TreeType;
import model.core.treeinfo.dao.CoreTreeInfoDao;
import model.core.treeinfo.entity.CoreTreeInfoEntity;
import model.core.treeinfo.service.CoreTreeInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.datamanage.GenericService;

import java.util.*;

@Service
public class CoreTreeInfoServiceImpl extends GenericService<CoreTreeInfoEntity> implements CoreTreeInfoService {
    @Autowired
    private CoreTreeInfoDao dao;

    public CoreTreeInfoEntity findOne(String mainId) {
        return dao.findOne(mainId);
    }

    @Override
    public String save(TreeType treeType, String parentId,String treeId, String treeName){
        if(StringUtils.isBlank(treeId)){
            treeId = UUID.randomUUID().toString();
            CoreTreeInfoEntity parEntity = findOne(parentId);

            CoreTreeInfoEntity entity = new CoreTreeInfoEntity();
            entity.setTreeId(treeId);
            entity.setParentId(parEntity.getTreeId());
            entity.setTreeName(treeName);
            entity.setTreeLeft(parEntity.getTreeRight());
            entity.setTreeRight(entity.getTreeLeft() + 1);
            entity.setTreeType(treeType.getCode());

            dao.insert(convertList(entity));
            //改前面的
            dao.insert_updateBefore(parEntity.getTreeType(),parEntity.getTreeLeft(),parEntity.getTreeRight());
            //改后面的
            dao.insert_updateAfter(parEntity.getTreeType(),entity.getTreeLeft());
        }else{
            CoreTreeInfoEntity entity = findOne(treeId);
            entity.setTreeName(treeName);
            dao.update(convertList(entity));
        }
        return treeId;
    }

    @Override
    public void move(String mainId, Boolean moveOn) {
        CoreTreeInfoEntity entity = findOne(mainId);
        CoreTreeInfoEntity neighborEntity = dao.findNeighborEntity(entity,moveOn);
        if(neighborEntity == null || entity == null){
            return;
        }
        List<CoreTreeInfoEntity> moveEntitySons = dao.findSons(mainId);
        List<CoreTreeInfoEntity> neighborEntitySons = dao.findSons(neighborEntity.getTreeId());
        moveEntitySons.add(entity);
        neighborEntitySons.add(neighborEntity);
        if(moveOn){
            for (CoreTreeInfoEntity e:moveEntitySons){
                e.setTreeLeft(e.getTreeLeft() - 2 * neighborEntitySons.size());
                e.setTreeRight(e.getTreeRight() - 2 * neighborEntitySons.size());
            }
            for (CoreTreeInfoEntity e:neighborEntitySons){
                e.setTreeLeft(e.getTreeLeft() + 2 * moveEntitySons.size());
                e.setTreeRight(e.getTreeRight() + 2 * moveEntitySons.size());
            }
        }else {
            for (CoreTreeInfoEntity e:moveEntitySons){
                e.setTreeLeft(e.getTreeLeft() + 2 * neighborEntitySons.size());
                e.setTreeRight(e.getTreeRight() + 2 * neighborEntitySons.size());
            }
            for (CoreTreeInfoEntity e:neighborEntitySons){
                e.setTreeLeft(e.getTreeLeft() - 2 * moveEntitySons.size());
                e.setTreeRight(e.getTreeRight() - 2 * moveEntitySons.size());
            }
        }

        List<CoreTreeInfoEntity> list = new ArrayList<>();
        list.addAll(neighborEntitySons);
        list.addAll(moveEntitySons);

        dao.update(convertList(list));
    }

    @Override
    public void delete(String mainId) {
        CoreTreeInfoEntity entitySelf = findOne(mainId);
        List<CoreTreeInfoEntity> list = dao.findSons(mainId);
        CoreTreeInfoEntity entityLastSon;
        if(list.size()>0){
            entityLastSon = list.get(0);
        }   else {
            entityLastSon = entitySelf;
        }
        Integer sonCount = list.size()+1;

        //删除自己及子孙节点
        dao.delete(mainId);
        //改前面的左右值
        dao.delete_updateBefor(entitySelf.getTreeType(),entitySelf.getTreeLeft(),entitySelf.getTreeRight(),sonCount);
        //改后面的左右值
        dao.delete_updateAfter(entityLastSon.getTreeType(),entityLastSon.getTreeLeft(),sonCount);
    }

    @Override
    public List<Map<String, Object>> getMainInfo(String treeType,String parentId) {
        List<CoreTreeInfoEntity> list = dao.getMainInfo(treeType,parentId);
        LinkedHashMap<String,List<Map<String, Object>>> map = new LinkedHashMap<>();
        List<Map<String, Object>> listRoot = new ArrayList<>();
        for(CoreTreeInfoEntity entity : list){
            Map<String, Object> m = entityToMap(entity);
            m.put("id", m.get("treeId"));
            m.put("title",m.get("treeName")+"  ("+m.get("treeLeft")+"-"+m.get("treeRight")+")");
            if(StringUtils.isBlank(entity.getParentId())){
                listRoot.add(m);
                continue;
            }
            List<Map<String, Object>> li = map.get(entity.getParentId());
            if(li == null){
                li = new ArrayList<>();
            }
            li.add(m);
            map.put(entity.getParentId(), li);
        }
        getTrees(listRoot,map);
        return listRoot;
    }

    private void getTrees(List<Map<String, Object>> listPar,LinkedHashMap<String,List<Map<String, Object>>> map){
        for(Map<String, Object> parMap : listPar){
            List<Map<String, Object>> ch = map.get(parMap.get("treeId").toString());
            if(ch != null && ch.size() > 0){
                getTrees(ch,map);
            }
            parMap.put("children", ch);
        }
    }

}