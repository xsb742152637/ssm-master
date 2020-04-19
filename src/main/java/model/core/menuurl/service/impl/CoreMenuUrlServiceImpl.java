package model.core.menuurl.service.impl;

import model.core.menuurl.dao.CoreMenuUrlInfoDao;
import model.core.menuurl.entity.CoreMenuUrlInfoEntity;
import model.core.menuurl.service.CoreMenuUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.context.ApplicationContext;
import util.datamanage.GenericService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@Service
public class CoreMenuUrlServiceImpl extends GenericService<CoreMenuUrlInfoEntity> implements CoreMenuUrlService {
    @Autowired
    private CoreMenuUrlInfoDao dao;
    /**
     * 获取实例
     */
    public static CoreMenuUrlServiceImpl getInstance() {
        return ApplicationContext.getCurrent().getBean(CoreMenuUrlServiceImpl.class);
    }

    @Override
    public List<CoreMenuUrlInfoEntity> getMainInfo(String primaryId,String searchKey,int page,int rows){
        return dao.getMainInfo(primaryId,searchKey, page,rows);
    }
    @Override
    public Integer getMainCount(String primaryId, String searchKey) {
        return dao.getMainCount(primaryId,searchKey);
    }

    @Override
    public CoreMenuUrlInfoEntity findOne(String primaryId) {
        return dao.findOne(primaryId);
    }

    @Override
    public CoreMenuUrlInfoEntity findOneByCode(String code) {
        return dao.findOneByCode(code);
    }

    @Override
    @Transactional
    public int insert(CoreMenuUrlInfoEntity entity){
        return dao.insert(convertList(entity));
    }
    @Override
    @Transactional
    public int insert(List<CoreMenuUrlInfoEntity> list) {
        return dao.insert(convertList(list));
    }

    @Override
    @Transactional
    public int update(CoreMenuUrlInfoEntity entity) {
        return dao.update(convertList(entity));
    }
    @Override
    @Transactional
    public int update(List<CoreMenuUrlInfoEntity> list) {
        return dao.update(convertList(list));
    }

    @Override
    @Transactional
    public int delete(String primaryId) {
        return dao.delete(primaryId);
    }

}
