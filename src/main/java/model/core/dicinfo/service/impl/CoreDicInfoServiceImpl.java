package model.core.dicinfo.service.impl;

import model.core.dicinfo.dao.CoreDicInfoDao;
import model.core.dicinfo.entity.CoreDicInfoEntity;
import model.core.dicinfo.service.CoreDicDetailService;
import model.core.dicinfo.service.CoreDicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    CoreDicDetailService detailService;

    @Override
    public List<CoreDicInfoEntity> getMainInfo(String searchKey,int page,int rows) {
        return dao.getMainInfo(searchKey,page,rows);
    }

    @Override
    public Integer getMainCount(String searchKey) {
        return dao.getMainCount(searchKey);
    }

    @Override
    public CoreDicInfoEntity findOne(String primaryId) {
        return dao.findOne(primaryId);
    }

    @Override
    @Transactional
    public int insert(CoreDicInfoEntity entity) {
        return dao.insert(convertList(entity));
    }

    @Override
    @Transactional
    public int update(CoreDicInfoEntity entity) {
        return dao.update(convertList(entity));
    }

    @Override
    @Transactional
    public int delete(String primaryId) {
        detailService.deleteByDicId(primaryId);
        return dao.delete(primaryId);
    }

}
