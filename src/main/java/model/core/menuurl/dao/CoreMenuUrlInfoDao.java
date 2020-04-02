package model.core.menuurl.dao;


import model.core.menuurl.entity.CoreMenuUrlInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CoreMenuUrlInfoDao {
    int insertList(@Param("list") List<CoreMenuUrlInfoEntity> list);

    int update(@Param("entity") CoreMenuUrlInfoEntity entity);

    int delete(@Param("mainId") String mainId);

    List<CoreMenuUrlInfoEntity> getMainInfo(@Param("mainId") String mainId,@Param("searchKey") String searchKey,@Param("page") int page,@Param("rows") int rows);

    Integer getMainCount(@Param("mainId") String mainId,@Param("searchKey") String searchKey);

    CoreMenuUrlInfoEntity findOne(@Param("mainId") String mainId);
}
