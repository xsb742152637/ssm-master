package model.core.menuTree.dao;

import model.core.menuTree.entity.CoreMenuTreeInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CoreMenuTreeDao{

    //当参数需要在if等标签中作为条件进行判断时，需要在此用@Param指定
    List<Map<String,Object>> getMenuTree(@Param(value = "isShow") Boolean isShow);
    List<CoreMenuTreeInfoEntity> findOneByCode(String code);
}
