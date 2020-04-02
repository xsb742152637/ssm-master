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
    List<Map<String,Object>> getMenuTree(Boolean isTop,Boolean isShow);

    CoreMenuTreeInfoEntity findOneByCode(String code);

    CoreMenuTreeInfoEntity findOne(String mainId);

    int insert(CoreMenuTreeInfoEntity pojo);

    int insertSelective(CoreMenuTreeInfoEntity pojo);

    int insertList(List<CoreMenuTreeInfoEntity> pojo);


    int update(CoreMenuTreeInfoEntity pojo);

    void moveTree(String treeId,boolean type);

    void delete(String mainId);

    /**
     * //根据上级，得到子级下一个排序数字
     * @param outlineLevel
     * @return
     */
    Integer getMenuLevelByParLevel(String outlineLevel);

    int updateAfterDelete(String before,String after);

}
