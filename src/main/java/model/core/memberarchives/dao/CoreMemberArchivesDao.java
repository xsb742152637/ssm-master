package model.core.memberarchives.dao;

import model.core.memberarchives.entity.CoreMemberArchivesEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface CoreMemberArchivesDao {
    CoreMemberArchivesEntity findOne(@Param("primaryId") String primaryId);

    int insert(@Param("map") Map<String, Object> map);
    int update(@Param("map") Map<String, Object> map);
    int delete(@Param("primaryId") String primaryId);
}
