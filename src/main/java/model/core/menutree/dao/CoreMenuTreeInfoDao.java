package model.core.menutree.dao;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

import model.core.menutree.entity.CoreMenuTreeInfoEntity;


public interface CoreMenuTreeInfoDao {
    int insert(@Param("pojo") CoreMenuTreeInfoEntity pojo);

    int insertSelective(@Param("pojo") CoreMenuTreeInfoEntity pojo);

    int insertList(@Param("pojos") List<CoreMenuTreeInfoEntity> pojo);

    int update(@Param("pojo") CoreMenuTreeInfoEntity pojo);

    int delete(@Param("menuId") String menuId);

    int updateAfterDelete(@Param("before") String before,@Param("after") String after);

    void updatebigBrother(@Param("outlineLevel") String outlineLevel);

    //当参数需要在if等标签中作为条件进行判断时，需要在此用@Param指定
    List<Map<String,Object>> getMenuTree(@Param(value = "isShow") Boolean isShow);

    List<CoreMenuTreeInfoEntity> findOneByCode(String code);

    CoreMenuTreeInfoEntity findOneById(@Param("menuId") String menuId);

    CoreMenuTreeInfoEntity findOneByOutlineLevel(@Param("outlineLevel") String outlineLevel);

    void moveChildren(@Param("oldParen")String oldParen,@Param("newParen")String newParen);

    Integer getMenuLevelByParLevel(@Param("outlineLevel")String outlineLevel);

}
