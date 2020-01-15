package model.core.menuTree.service;

import java.util.List;
import java.util.Map;

public interface CoreMenuTreeService {
    /**
     * 得到全部显示的菜单
     * @return
     */
    List<Map<String,Object>> getMenuTree();
}
