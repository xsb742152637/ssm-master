package model.core.menuTree.dao;

import model.core.menuTree.entity.CoreMenuTreeInfoEntity;

import java.util.List;
import java.util.Map;

public interface CoreMenuTreeDao{

    List<Map<String,Object>> getMenuTree();
    List<CoreMenuTreeInfoEntity> findOneByCode(String code);
}
