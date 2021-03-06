package model.core.menutree.dao;


import model.core.menutree.entity.CoreMenuTreeInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CoreMenuTreeInfoDao {

    int insert(@Param("map") Map<String,Object> map);

    int update(@Param("map") Map<String,Object> map);

    int delete(@Param("primaryId") String primaryId);

    int updateAfterDelete(@Param("before") String before,@Param("after") String after);

    void updatebigBrother(@Param("outlineLevel") String outlineLevel);

    //当参数需要在if等标签中作为条件进行判断时，需要在此用@Param指定
    List<Map<String,Object>> getMenuTree(@Param(value = "isShow") Boolean isShow);

    List<CoreMenuTreeInfoEntity> findOneByCode(@Param(value = "code") String code);

    CoreMenuTreeInfoEntity findOne(@Param("primaryId") String primaryId);

    CoreMenuTreeInfoEntity findOneByOutlineLevel(@Param("outlineLevel") String outlineLevel);

    void moveChildren(@Param("oldParen")String oldParen,@Param("newParen")String newParen);

    Integer getMenuLevelByParLevel(@Param("outlineLevel")String outlineLevel);
}
