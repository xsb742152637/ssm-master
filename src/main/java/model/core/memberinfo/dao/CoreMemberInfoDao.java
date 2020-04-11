package model.core.memberinfo.dao;

import model.core.memberinfo.entity.CoreMemberInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CoreMemberInfoDao{

    CoreMemberInfoEntity findOne(@Param("primaryId") String primaryId);
    List<CoreMemberInfoEntity> findAll();
    CoreMemberInfoEntity findOneByTreeId(@Param("treeId") String treeId);
    int checkAccount(@Param("account") String account);
    CoreMemberInfoEntity loginCheck(@Param("account") String account, @Param("password")  String password);

    List<CoreMemberInfoEntity> findSons(@Param("primaryId") String primaryId);
    CoreMemberInfoEntity findNeighborEntity(@Param("entity")CoreMemberInfoEntity entity,@Param("moveOn") Boolean moveOn);

    int insert(@Param("map") Map<String,Object> map);
    int update(@Param("map") Map<String,Object> map);
    int delete(@Param("primaryId") String primaryId);
}
