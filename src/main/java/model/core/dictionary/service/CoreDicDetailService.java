package model.core.dictionary.service;

import model.core.dictionary.entity.CoreDicDetailEntity;

import java.util.List;

/**
 * @InterfaceName CoreDicDetailService
 * @Description TODO
 * @Author Jane
 * @Date 2020/4/8 14:00
 * @Version 1.0
 */

public interface CoreDicDetailService {
    int insert(CoreDicDetailEntity entity);
    int update(CoreDicDetailEntity entity);
    int delete(String mainId);
    CoreDicDetailEntity findOne(String mainId);
    List<CoreDicDetailEntity> findAll(String typeId);
    Integer findAllCount(String typeId);
}
