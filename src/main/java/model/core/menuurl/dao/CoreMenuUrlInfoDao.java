package model.core.menuurl.dao;


import model.core.menuurl.entity.CoreMenuUrlInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface CoreMenuUrlInfoDao {
    List<CoreMenuUrlInfoEntity> getMainInfo(@Param("primaryId") String primaryId,@Param("searchKey") String searchKey,@Param("page") int page,@Param("rows") int rows);
    Integer getMainCount(@Param("primaryId") String primaryId,@Param("searchKey") String searchKey);

    CoreMenuUrlInfoEntity findOne(@Param("primaryId") String primaryId);
    CoreMenuUrlInfoEntity findOneByCode(@Param("code") String code);

    int insert(@Param("map") Map<String,Object> map);

    int update(@Param("map") Map<String,Object> map);

    int delete(@Param("primaryId") String primaryId);

}
