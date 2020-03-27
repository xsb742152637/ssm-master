package model.core.menutree.service;

import model.core.menutree.entity.CoreMenuTreeInfoEntity;

import java.util.List;
import java.util.Map;

public interface CoreMenuTreeService {
    /**
     * 得到全部显示的菜单
     * @return
     */
    List<Map<String,Object>> getMenuTree(Boolean isTop,Boolean isShow);

    CoreMenuTreeInfoEntity findOneByCode(String code);
}
