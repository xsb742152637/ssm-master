package model.core.dicinfo.service.impl;

import model.core.dicinfo.dao.CoreDicInfoDetailDao;
import model.core.dicinfo.entity.CoreDicInfoDetailEntity;
import model.core.dicinfo.service.CoreDicDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.datamanage.GenericService;

import java.util.List;

/**
 * @ClassName CoreDicDetailServiceImpl
 * @Description TODO
 * @Author Jane
 * @Date 2020/4/8 14:01
 * @Version 1.0
 */
@Service
public class CoreDicDetailServiceImpl extends GenericService implements CoreDicDetailService {
    @Autowired
    CoreDicInfoDetailDao dao;

    @Override
    @Transactional
    public int insert(CoreDicInfoDetailEntity entity) {
        return dao.insert(convertList(entity));
    }

    @Override
    @Transactional
    public int update(CoreDicInfoDetailEntity entity) {
        return dao.update(convertList(entity));
    }

    @Override
    @Transactional
    public int delete(String mainId) {
        return dao.delete(mainId);
    }

    @Override
    public CoreDicInfoDetailEntity findOne(String mainId) {
        return dao.findOne(mainId);
    }

    @Override
    public List<CoreDicInfoDetailEntity> findAll(String dicId) {
        return dao.findAll(dicId);
    }
}
