package model.core.type.dao;

import model.core.type.entity.AppTypeInfoEntity;
import org.apache.ibatis.annotations.Param;

public interface AppTypeInfoDao {
    AppTypeInfoEntity findOne(@Param("type_id") String type_id);
}
