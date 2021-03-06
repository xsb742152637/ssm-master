package model.core.menutree.service;

import model.core.menutree.entity.CoreMenuTreeInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CoreMenuTreeService {
    /**
     * 得到全部显示的菜单
     * @return
     */
    List<Map<String,Object>> getMenuTree(Boolean needGuide,Boolean isTop,Boolean isShow);

    CoreMenuTreeInfoEntity findOneByCode(String code);

    CoreMenuTreeInfoEntity findOne(String primaryId);

    int insert(CoreMenuTreeInfoEntity entity);

    int insert(List<CoreMenuTreeInfoEntity> list);

    int update(CoreMenuTreeInfoEntity entity);
    int update(List<CoreMenuTreeInfoEntity> list);

    void moveTree(String treeId,boolean type);

    void delete(String primaryId);

    /**
     * //根据上级，得到子级下一个排序数字
     * @param outlineLevel
     * @return
     */
    Integer getMenuLevelByParLevel(String outlineLevel);

    int updateAfterDelete(String before,String after);

}
