package model.core.memberinfo.dao;

import model.core.memberinfo.entity.CoreMemberInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CoreMemberInfoDao{

    CoreMemberInfoEntity findOne(@Param("mainId") String mainId);
    List<CoreMemberInfoEntity> findAll();
    CoreMemberInfoEntity findOneByTreeId(@Param("treeId") String treeId);
    CoreMemberInfoEntity loginCheck(@Param("account") String account, @Param("password")  String password);

    List<CoreMemberInfoEntity> findSons(@Param("mainId") String mainId);
    CoreMemberInfoEntity findNeighborEntity(@Param("entity")CoreMemberInfoEntity entity,@Param("moveOn") Boolean moveOn);

    int insert(@Param("map") Map<String,Object> map);
    int update(@Param("map") Map<String,Object> map);
    int delete(@Param("mainId") String mainId);
}
