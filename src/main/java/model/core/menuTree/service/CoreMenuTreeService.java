package model.core.menuTree.service;

import model.core.menuTree.entity.CoreMenuTreeInfoEntity;

import java.util.List;
import java.util.Map;

public interface CoreMenuTreeService {
    /**
     * 得到全部显示的菜单
     * @return
     */
    List<Map<String,Object>> getMenuTree();

    CoreMenuTreeInfoEntity findOneByCode(String code);
}
