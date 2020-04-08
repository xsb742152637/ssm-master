package model.core.dictionary.dao;

import model.core.dictionary.entity.CoreDicDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CoreDicDetailDao
 * @Description TODO
 * @Author Jane
 * @Date 2020/4/8 13:54
 * @Version 1.0
 */
public interface CoreDicDetailDao {
    int insert(@Param("map") Map<String,Object> map);
    int update(@Param("map") Map<String,Object> map);
    int delete(@Param("mainId") String mainId);
    CoreDicDetailEntity findOne(@Param("mainId") String mainId);
    List<CoreDicDetailEntity> findAll(@Param("typeId")String typeId);
    Integer findAllCount(@Param("typeId")String typeId);
}
