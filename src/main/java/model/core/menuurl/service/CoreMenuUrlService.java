package model.core.menuurl.service;

import model.core.menuurl.entity.CoreMenuUrlInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CoreMenuUrlService {

    List<CoreMenuUrlInfoEntity> getMainInfo(String mainId,String searchKey,int page,int rows);

    Integer getMainCount(String mainId,String searchKey);

    int insert(CoreMenuUrlInfoEntity pojo);

    int insertList(List<CoreMenuUrlInfoEntity> pojo);

    int delete(String mainId);

    int update(CoreMenuUrlInfoEntity pojo);

    CoreMenuUrlInfoEntity findOne(String mainId);

}
