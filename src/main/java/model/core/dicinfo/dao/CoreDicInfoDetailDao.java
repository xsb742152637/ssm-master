package model.core.dicinfo.dao;

import model.core.dicinfo.entity.CoreDicInfoDetailEntity;
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
public interface CoreDicInfoDetailDao {
    int insert(@Param("map") Map<String,Object> map);
    int update(@Param("map") Map<String,Object> map);
    int delete(@Param("mainId") String mainId);
    CoreDicInfoDetailEntity findOne(@Param("mainId") String mainId);
    List<CoreDicInfoDetailEntity> findAll(@Param("dicId")String dicId);
}
