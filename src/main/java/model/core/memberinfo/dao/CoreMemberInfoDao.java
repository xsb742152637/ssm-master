package model.core.memberinfo.dao;

import model.core.memberinfo.entity.CoreMemberInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CoreMemberInfoDao{
    CoreMemberInfoEntity findOne(@Param("account") String account, @Param("password")  String password);

    List<Map<String,Object>> getMainInfo(@Param("parentId") String parentId);

    int delete(@Param("mainId") String mainId);
    int delete_updateBefor(@Param("left") Integer left,@Param("right")Integer right,@Param("sonCount")Integer sonCount);
    int delete_updateAfter(@Param("left") Integer left,@Param("sonCount")Integer sonCount);

    CoreMemberInfoEntity findOneById(@Param("mainId") String mainId);

    List<CoreMemberInfoEntity> findSons(@Param("mainId") String mainId);

    int insert(@Param("pojo") CoreMemberInfoEntity pojo);

    int insert_updateBefore(@Param("left") Integer left,@Param("right")Integer right);
    int insert_updateAfter(@Param("left") Integer left);

    int insertList(@Param("pojos") List<CoreMemberInfoEntity> pojo);

    int update(@Param("pojo") CoreMemberInfoEntity pojo);

    int updateList(@Param("pojo") List<CoreMemberInfoEntity> pojo);

    CoreMemberInfoEntity findNeighborEntity(@Param("pojo")CoreMemberInfoEntity pojo,@Param("moveOn") Boolean moveOn);



}
