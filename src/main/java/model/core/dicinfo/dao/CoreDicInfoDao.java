package model.core.dicinfo.dao;


import model.core.dicinfo.entity.CoreDicInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CoreDicInfoDao
 * @Description TODO
 * @Author Jane
 * @Date 2020/4/8 13:54
 * @Version 1.0
 */
public interface CoreDicInfoDao {
    List<CoreDicInfoEntity> getMainInfo(@Param("searchKey") String searchKey,@Param("page") int page,@Param("rows") int rows);
    Integer getMainCount(@Param("searchKey") String searchKey);
    CoreDicInfoEntity findOne(@Param("primaryId") String primaryId);

    int insert(@Param("map") Map<String,Object> map);
    int update(@Param("map") Map<String,Object> map);
    int delete(@Param("primaryId") String primaryId);
}
