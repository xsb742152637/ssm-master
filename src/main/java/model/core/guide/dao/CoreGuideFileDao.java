package model.core.guide.dao;


import model.core.guide.entity.CoreGuideFileEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CoreGuideFileDao {
    CoreGuideFileEntity findOne(@Param("primaryId") String primaryId);
    List<CoreGuideFileEntity> findAll();

    int insert(@Param("map") Map<String, Object> map);

    int update(@Param("map") Map<String, Object> map);

    int delete(@Param("primaryId") String primaryId);
    int deleteAll();

}
