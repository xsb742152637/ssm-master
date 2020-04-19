package model.core.menuurl.service;

import model.core.menuurl.entity.CoreMenuUrlInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CoreMenuUrlService {

    List<CoreMenuUrlInfoEntity> getMainInfo(String primaryId,String searchKey,int page,int rows);
    Integer getMainCount(String primaryId,String searchKey);

    CoreMenuUrlInfoEntity findOne(String primaryId);
    CoreMenuUrlInfoEntity findOneByCode(String code);

    int insert(CoreMenuUrlInfoEntity entity);
    int insert(List<CoreMenuUrlInfoEntity> list);

    int update(CoreMenuUrlInfoEntity entity);
    int update(List<CoreMenuUrlInfoEntity> list);

    int delete(String primaryId);
}
