package model.core.dictionary.service.impl;

import model.core.dictionary.dao.CoreDicInfoDao;
import model.core.dictionary.entity.CoreDicInfoEntity;
import model.core.dictionary.service.CoreDicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.datamanage.GenericService;

import java.util.List;

/**
 * @ClassName CoreDicInfoServiceImpl
 * @Description TODO
 * @Author Jane
 * @Date 2020/4/8 14:01
 * @Version 1.0
 */
@Service
public class CoreDicInfoServiceImpl extends GenericService implements CoreDicInfoService {

    @Autowired
    CoreDicInfoDao dao;
    @Override
    public int insert(CoreDicInfoEntity entity) {
        return dao.insert(convertList(entity));
    }

    @Override
    public int update(CoreDicInfoEntity entity) {
        return dao.update(convertList(entity));
    }

    @Override
    public int delete(String mainId) {
        return dao.delete(mainId);
    }

    @Override
    public CoreDicInfoEntity findOne(String mainId) {
        return dao.findOne(mainId);
    }

    @Override
    public List<CoreDicInfoEntity> findAll() {
        return dao.findAll();
    }

    @Override
    public Integer findAllCount() {
        return dao.findAllCount();
    }
}
