package model.core.menuurl.dao;


import model.core.menuurl.entity.CoreMenuUrlInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface CoreMenuUrlInfoDao {
    List<CoreMenuUrlInfoEntity> getMainInfo(@Param("mainId") String mainId,@Param("searchKey") String searchKey,@Param("page") int page,@Param("rows") int rows);
    Integer getMainCount(@Param("mainId") String mainId,@Param("searchKey") String searchKey);

    CoreMenuUrlInfoEntity findOne(@Param("mainId") String mainId);

    int insert(@Param("map") Map<String,Object> map);

    int update(@Param("map") Map<String,Object> map);

    int delete(@Param("mainId") String mainId);

}
