package model.core.menuurl.service;

import model.core.menuurl.entity.CoreMenuUrlInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CoreMenuUrlService {

    List<CoreMenuUrlInfoEntity> getMainInfo(String mainId,String searchKey,int page,int rows);
    Integer getMainCount(String mainId,String searchKey);

    CoreMenuUrlInfoEntity findOne(String mainId);

    int insert(CoreMenuUrlInfoEntity entity);
    int insert(List<CoreMenuUrlInfoEntity> list);

    int update(CoreMenuUrlInfoEntity entity);
    int update(List<CoreMenuUrlInfoEntity> list);

    int delete(String mainId);
}
