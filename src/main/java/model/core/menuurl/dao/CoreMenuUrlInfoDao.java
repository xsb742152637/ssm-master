package model.core.menuurl.dao;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import model.core.menuurl.entity.CoreMenuUrlInfoEntity;


public interface CoreMenuUrlInfoDao {
    int insert(@Param("pojo") CoreMenuUrlInfoEntity pojo);

    int insertSelective(@Param("pojo") CoreMenuUrlInfoEntity pojo);

    int insertList(@Param("pojos") List<CoreMenuUrlInfoEntity> pojo);

    int update(@Param("pojo") CoreMenuUrlInfoEntity pojo);

    int delete(@Param("mainId") String mainId);

    List<CoreMenuUrlInfoEntity> getMainInfo(@Param("mainId") String mainId,@Param("searchKey") String searchKey,@Param("page") int page,@Param("rows") int rows);

    Integer getMainCount(@Param("mainId") String mainId,@Param("searchKey") String searchKey);

    CoreMenuUrlInfoEntity findOne(@Param("mainId") String mainId);
}
