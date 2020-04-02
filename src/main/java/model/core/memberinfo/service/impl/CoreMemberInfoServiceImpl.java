package model.core.memberinfo.service.impl;

import model.core.memberinfo.dao.CoreMemberInfoDao;
import model.core.memberinfo.entity.CoreMemberInfoEntity;
import model.core.memberinfo.service.CoreMemberInfoService;
import model.core.menutree.entity.CoreMenuTreeInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.datamanage.GenericService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CoreMemberInfoServiceImpl extends GenericService<CoreMemberInfoEntity> implements CoreMemberInfoService {
    @Autowired
    private CoreMemberInfoDao dao;
    @Override
    public CoreMemberInfoEntity findOne(String account, String password) {
        return dao.findOne(account,password);
    }

    @Override
    public void insert(CoreMemberInfoEntity entity,CoreMemberInfoEntity fEntity) {
        List<CoreMemberInfoEntity> list = new ArrayList<>();
        list.add(entity);
        dao.insertList(convertList(list));

        //改前面的
        dao.insert_updateBefore(fEntity.getMemberLeft(),fEntity.getMemberLeft());
        //改后面的
        dao.insert_updateAfter(entity.getMemberLeft());
    }

    @Override
    public int insertList(List<CoreMemberInfoEntity> list) {
        return dao.insertList(convertList(list));
    }


    @Override
    @Transactional
    public int updateList(List<CoreMemberInfoEntity> list) {
        return dao.updateList(list);
    }

    @Override
    public void moveMainInfo(String mainId, Boolean moveOn) {
        CoreMemberInfoEntity entity = findOnebyId(mainId);
        CoreMemberInfoEntity neighborEntity = dao.findNeighborEntity(entity,moveOn);
        if(neighborEntity == null || entity == null){
            return;
        }
        List<CoreMemberInfoEntity> moveEntitySons = dao.findSons(mainId);
        List<CoreMemberInfoEntity> neighborEntitySons = dao.findSons(neighborEntity.getMemberId());
        moveEntitySons.add(entity);
        neighborEntitySons.add(neighborEntity);
        if(moveOn){
            for (CoreMemberInfoEntity e:moveEntitySons){
                e.setMemberLeft(e.getMemberLeft() - 2 * neighborEntitySons.size());
                e.setMemberRight(e.getMemberRight() - 2 * neighborEntitySons.size());
            }
            for (CoreMemberInfoEntity e:neighborEntitySons){
                e.setMemberLeft(e.getMemberLeft() + 2 * moveEntitySons.size());
                e.setMemberRight(e.getMemberRight() + 2 * moveEntitySons.size());
            }
        }else {
            for (CoreMemberInfoEntity e:moveEntitySons){
                e.setMemberLeft(e.getMemberLeft() + 2 * neighborEntitySons.size());
                e.setMemberRight(e.getMemberRight() + 2 * neighborEntitySons.size());
            }
            for (CoreMemberInfoEntity e:neighborEntitySons){
                e.setMemberLeft(e.getMemberLeft() - 2 * moveEntitySons.size());
                e.setMemberRight(e.getMemberRight() - 2 * moveEntitySons.size());
            }
        }
        updateList(neighborEntitySons);
        updateList(moveEntitySons);
    }

    @Override
    public int update(CoreMemberInfoEntity entity) {
        return dao.update(entity);
    }

    @Override
    public CoreMemberInfoEntity findOnebyId(String mainId) {
        return dao.findOneById(mainId);
    }


    @Override
    public void deleteMain(String mainId) {
        CoreMemberInfoEntity entitySelf = dao.findOneById(mainId);
        List<CoreMemberInfoEntity> list = dao.findSons(mainId);
        CoreMemberInfoEntity entityLastSon;
        if(list.size()>0){
            entityLastSon = list.get(0);
        }   else {
            entityLastSon = entitySelf;
        }
        Integer sonCount = list.size()+1;

        //删除自己及子孙节点
        dao.delete(mainId);

        //改前面的左右值
        dao.delete_updateBefor(entitySelf.getMemberLeft(),entitySelf.getMemberRight(),sonCount);

        //改后面的左右值
        dao.delete_updateAfter(entityLastSon.getMemberLeft(),sonCount);
    }


    @Override
    public List<Map<String, Object>> getMainInfo(String parentId) {
        List<Map<String,Object>> list = dao.getMainInfo(parentId);
        for(Map<String,Object> map:list){
            map.put("id",map.get("memberId"));
            map.put("title",map.get("memberName")+"  ("+map.get("memberLeft")+"-"+map.get("memberRight")+")");
            map.put("children", getMainInfo(map.get("memberId").toString()));


//            if ("root".equalsIgnoreCase(map.get("memberId").toString())) {
//                map.put("children", getMainInfo(map.get("memberId").toString()));
//                map.put("spread",true);
//            }else{
////                    map.put("children", new ArrayList<>());
//                map.put("children", getMainInfo(map.get("memberId").toString()));
//                map.put("spread",false);
//            }
//
        }
        return list;
    }

    @Transactional
    public void add(CoreMemberInfoEntity entity){
        try{

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}
