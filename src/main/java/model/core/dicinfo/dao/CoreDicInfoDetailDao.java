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
    List<CoreDicInfoDetailEntity> getDetailInfo(@Param("primaryId") String primaryId,@Param("searchKey") String searchKey,@Param("page") int page,@Param("rows") int rows);
    Integer getDetailCount(@Param("primaryId") String primaryId,@Param("searchKey") String searchKey);

    int insert(@Param("map") Map<String,Object> map);
    int update(@Param("map") Map<String,Object> map);
    int delete(@Param("primaryId") String primaryId);
    int deleteByDicId(@Param("dicId") String dicId);
}
