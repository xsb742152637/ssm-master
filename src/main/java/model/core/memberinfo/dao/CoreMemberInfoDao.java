package model.core.memberinfo.dao;

import model.core.memberinfo.entity.CoreMemberInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CoreMemberInfoDao{

    List<Map<String,Object>> getMainInfo(@Param("parentId") String parentId);


    CoreMemberInfoEntity findOne(@Param("account") String account, @Param("password")  String password);
    CoreMemberInfoEntity findOneById(@Param("mainId") String mainId);

    List<CoreMemberInfoEntity> findSons(@Param("mainId") String mainId);
    CoreMemberInfoEntity findNeighborEntity(@Param("entity")CoreMemberInfoEntity entity,@Param("moveOn") Boolean moveOn);

    int insert_updateBefore(@Param("left") Integer left,@Param("right")Integer right);
    int insert_updateAfter(@Param("left") Integer left);

    int insert(@Param("map") Map<String,Object> map);

    int update(@Param("map") Map<String,Object> map);

    int delete(@Param("mainId") String mainId);

    int delete_updateBefor(@Param("left") Integer left,@Param("right")Integer right,@Param("sonCount")Integer sonCount);
    int delete_updateAfter(@Param("left") Integer left,@Param("sonCount")Integer sonCount);

}
