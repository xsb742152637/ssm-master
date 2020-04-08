package model.core.dictionary.service.impl;

import model.core.dictionary.dao.CoreDicDetailDao;
import model.core.dictionary.entity.CoreDicDetailEntity;
import model.core.dictionary.service.CoreDicDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    CoreDicDetailDao dao;

    @Override
    public int insert(CoreDicDetailEntity entity) {
        return dao.insert(convertList(entity));
    }

    @Override
    public int update(CoreDicDetailEntity entity) {
        return dao.update(convertList(entity));
    }

    @Override
    public int delete(String mainId) {
        return dao.delete(mainId);
    }

    @Override
    public CoreDicDetailEntity findOne(String mainId) {
        return dao.findOne(mainId);
    }

    @Override
    public List<CoreDicDetailEntity> findAll(String typeId) {
        return dao.findAll(typeId);
    }

    @Override
    public Integer findAllCount(String typeId) {
        return dao.findAllCount(typeId);
    }
}
