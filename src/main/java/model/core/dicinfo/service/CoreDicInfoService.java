package model.core.dicinfo.service;

import model.core.dicinfo.entity.CoreDicInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @InterfaceName CoreDicInfoService
 * @Description TODO
 * @Author Jane
 * @Date 2020/4/8 13:59
 * @Version 1.0
 */
public interface CoreDicInfoService {
    List<CoreDicInfoEntity> getMainInfo(String searchKey,int page,int rows);
    Integer getMainCount(String searchKey);
    CoreDicInfoEntity findOne(String primaryId);

    int insert(CoreDicInfoEntity entity);
    int update(CoreDicInfoEntity entity);
    int delete(String primaryId);
}
