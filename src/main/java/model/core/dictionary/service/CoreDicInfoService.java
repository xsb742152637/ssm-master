package model.core.dictionary.service;

import model.core.dictionary.entity.CoreDicInfoEntity;

import java.util.List;

/**
 * @InterfaceName CoreDicInfoService
 * @Description TODO
 * @Author Jane
 * @Date 2020/4/8 13:59
 * @Version 1.0
 */
public interface CoreDicInfoService {
    int insert(CoreDicInfoEntity entity);
    int update(CoreDicInfoEntity entity);
    int delete(String mainId);
    CoreDicInfoEntity findOne(String mainId);
    List<CoreDicInfoEntity> findAll();
    Integer findAllCount();
}
