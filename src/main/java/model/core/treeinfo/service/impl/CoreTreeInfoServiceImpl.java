package model.core.treeinfo.service.impl;

import model.core.memberinfo.MemberType;
import model.core.memberinfo.entity.CoreMemberInfoEntity;
import model.core.memberinfo.service.CoreMemberInfoService;
import model.core.memberinfo.service.impl.CoreMemberInfoServiceImpl;
import model.core.treeinfo.TreeType;
import model.core.treeinfo.dao.CoreTreeInfoDao;
import model.core.treeinfo.entity.CoreTreeInfoEntity;
import model.core.treeinfo.service.CoreTreeInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.context.ApplicationContext;
import util.datamanage.GenericService;

import java.util.*;

@Service
public class CoreTreeInfoServiceImpl extends GenericService implements CoreTreeInfoService {
    @Autowired
    private CoreTreeInfoDao dao;
    @Autowired
    private CoreMemberInfoService memberService;

    static {
        String name = ApplicationContext.get("name");

        System.out.println("初始化树，系统名称：" + name);
        CoreTreeInfoServiceImpl that = CoreTreeInfoServiceImpl.getInstance();
        List<CoreTreeInfoEntity> list = that.findRoots();
        List<String> rootIds = new ArrayList<>();
        for(CoreTreeInfoEntity entity : list){
            rootIds.add(entity.getTreeId());
        }
        CoreMemberInfoEntity mem = null;
        List<CoreTreeInfoEntity> addList = new ArrayList<>();
        for(TreeType en : TreeType.values()){
            if(!rootIds.contains(en.toString())){
                //为各类型树新增根节点
                CoreTreeInfoEntity entity = new CoreTreeInfoEntity();
                entity.setTreeId(en.toString());
                entity.setParentId(null);
                entity.setTreeName(name);
                entity.setTreeLeft(1);
                entity.setTreeRight(entity.getTreeLeft() + 1);
                entity.setTreeType(en.getCode());
                addList.add(entity);

                //为根成员新增信息
                if(TreeType.MemberInfo.toString().equals(en.toString())){
                    mem = new CoreMemberInfoEntity();
                    mem.setMemberId(en.toString());
                    mem.setMemberName(name);
                    mem.setMemberType(MemberType.Group.getCode());
                    mem.setMemberState(true);
                    mem.setTreeId(entity.getTreeId());
                }
            }
        }
        if(addList.size() > 0){
            that.insert(addList);
        }if(mem != null){
            CoreMemberInfoServiceImpl.getInstance().insert(mem);
        }
    }

    public static CoreTreeInfoServiceImpl getInstance() {
        return ApplicationContext.getCurrent().getBean(CoreTreeInfoServiceImpl.class);
    }

    public void insert(List<CoreTreeInfoEntity> list){
        dao.insert(convertList(list));
    }

    public CoreTreeInfoEntity findOne(String mainId) {
        return dao.findOne(mainId);
    }

    public List<CoreTreeInfoEntity> findRoots() {
        return dao.findRoots();
    }

    @Override
    @Transactional
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
    @Transactional
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
    @Transactional
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
    public List<Map<String, Object>> getMainInfo(String treeType) {
        return getMainInfo(treeType,null,1);
    }
    @Override
    public List<Map<String, Object>> getMainInfo(String treeType,String parentId,int openLevel) {
        Map<String,Map<String,Object>> mapOther = new HashMap<>();
        if(String.valueOf(TreeType.MemberInfo.getCode()).equals(treeType)){
            //得到全部成员
            List<CoreMemberInfoEntity> memList= memberService.findAll();
            for(CoreMemberInfoEntity entity : memList){
                Map<String,Object> m = entityToMap(entity);
                if(String.valueOf(MemberType.Group.getCode()).equals(m.get("memberType").toString())){
                    m.put("icon","layui-icon-group");
                }if(String.valueOf(MemberType.Person.getCode()).equals(m.get("memberType").toString())){
                    m.put("icon","layui-icon-username");
                }
                mapOther.put(entity.getTreeId(),m);
            }
        }

        List<CoreTreeInfoEntity> list = dao.getMainInfo(treeType,parentId);
        LinkedHashMap<String,List<Map<String, Object>>> map = new LinkedHashMap<>();
        List<Map<String, Object>> listRoot = new ArrayList<>();
        for(CoreTreeInfoEntity entity : list){
            Map<String, Object> m = entityToMap(entity);
            m.put("id", m.get("treeId"));
            m.put("title",m.get("treeName")+"  ("+m.get("treeLeft")+"-"+m.get("treeRight")+")");
            if(StringUtils.isBlank(entity.getParentId())){
                m.put("readonly",true);
                m.put("icon", "layui-icon-tree");
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
        int thisLevel = 0;

        getTrees(listRoot,map,mapOther,openLevel,thisLevel);
        return listRoot;
    }

    private void getTrees(List<Map<String, Object>> listPar,LinkedHashMap<String,List<Map<String, Object>>> map,Map<String,Map<String,Object>> mapOther,int openLevel,int thisLevel){
        thisLevel++;
        for(Map<String, Object> parMap : listPar){
            String treeId = parMap.get("treeId").toString();
            List<Map<String, Object>> ch = map.get(treeId);
            if(ch != null && ch.size() > 0){
                getTrees(ch,map,mapOther,openLevel,thisLevel);
            }
            if(openLevel== -1 || thisLevel <= openLevel){
                parMap.put("spread",true);
            }
            parMap.put("children", ch);
            if(mapOther.get(treeId) != null){
                Map<String,Object> m = mapOther.get(treeId);
                m.putAll(parMap);
                parMap.putAll(m);
            }
        }
    }

}
