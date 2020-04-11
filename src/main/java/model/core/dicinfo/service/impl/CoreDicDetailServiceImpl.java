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
    public List<CoreDicInfoDetailEntity> getDetailInfo(String dicId,String searchKey,int page,int rows) {
        return dao.getDetailInfo(dicId,searchKey,page,rows);
    }

    @Override
    public Integer getDetailCount(String primaryId, String searchKey) {
        return dao.getDetailCount(primaryId,searchKey);
    }

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
    public int delete(String primaryId) {
        return dao.delete(primaryId);
    }

    @Override
    @Transactional
    public int deleteByDicId(String dicId) {
        return dao.deleteByDicId(dicId);
    }

}
