package model.core.dicinfo.service;

import model.core.dicinfo.entity.CoreDicInfoDetailEntity;

import java.util.List;

/**
 * @InterfaceName CoreDicDetailService
 * @Description TODO
 * @Author Jane
 * @Date 2020/4/8 14:00
 * @Version 1.0
 */

public interface CoreDicDetailService {
    List<CoreDicInfoDetailEntity> getDetailInfo(String dicId,String searchKey,int page,int rows);
    Integer getDetailCount(String dicId,String searchKey);

    int insert(CoreDicInfoDetailEntity entity);
    int update(CoreDicInfoDetailEntity entity);
    int delete(String primaryId);
    int deleteByDicId(String dicId);
}
