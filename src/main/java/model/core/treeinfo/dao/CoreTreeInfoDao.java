package model.core.treeinfo.dao;

import model.core.treeinfo.entity.CoreTreeInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CoreTreeInfoDao {

    List<CoreTreeInfoEntity> getMainInfo(@Param("treeType") String treeType, @Param("parentId") String parentId);

    CoreTreeInfoEntity findOne(@Param("primaryId") String primaryId);

    List<CoreTreeInfoEntity> findRoots();
    List<CoreTreeInfoEntity> findSons(@Param("primaryId") String primaryId);
    CoreTreeInfoEntity findNeighborEntity(@Param("entity") CoreTreeInfoEntity entity, @Param("moveOn") Boolean moveOn);

    int insert(@Param("map") Map<String, Object> map);
    int insert_updateBefore(@Param("treeType") Integer treeType, @Param("left") Integer left, @Param("right") Integer right);
    int insert_updateAfter(@Param("treeType") Integer treeType,@Param("left") Integer left);

    int update(@Param("map") Map<String, Object> map);

    int delete(@Param("primaryId") String primaryId);
    int delete_updateBefor(@Param("treeType") Integer treeType,@Param("left") Integer left, @Param("right") Integer right, @Param("sonCount") Integer sonCount);
    int delete_updateAfter(@Param("treeType") Integer treeType,@Param("left") Integer left, @Param("sonCount") Integer sonCount);

}
