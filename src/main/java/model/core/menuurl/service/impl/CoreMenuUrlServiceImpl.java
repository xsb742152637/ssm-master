package model.core.menuurl.service.impl;

import model.core.menuurl.dao.CoreMenuUrlInfoDao;
import model.core.menuurl.entity.CoreMenuUrlInfoEntity;
import model.core.menuurl.service.CoreMenuUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.context.ApplicationContext;
import util.datamanage.GenericService;

import java.util.List;

@Service
public class CoreMenuUrlServiceImpl extends GenericService implements CoreMenuUrlService {
    @Autowired
    private CoreMenuUrlInfoDao dao;
    /**
     * 获取实例
     */
    public static CoreMenuUrlServiceImpl getInstance() {
        return ApplicationContext.getCurrent().getBean(CoreMenuUrlServiceImpl.class);
    }

    @Override
    public List<CoreMenuUrlInfoEntity> getMainInfo(String mainId,String searchKey,int page,int rows){
        return dao.getMainInfo(mainId,searchKey, page,rows);
    }

    @Override
    public Integer getMainCount(String mainId, String searchKey) {
        return dao.getMainCount(mainId,searchKey);
    }

    @Override
    public int insert(CoreMenuUrlInfoEntity pojo) {
        return dao.insert(pojo);
    }

    @Override
    public int insertSelective(CoreMenuUrlInfoEntity pojo) {
        return dao.insertSelective(pojo);
    }

    @Override
    public int insertList(List<CoreMenuUrlInfoEntity> pojo) {
        return dao.insertList(pojo);
    }

    @Override
    public CoreMenuUrlInfoEntity findOne(String mainId) {
        return dao.findOne(mainId);
    }

    @Override
    public int delete(String mainId) {
        return dao.delete(mainId);
    }

    @Override
    public int update(CoreMenuUrlInfoEntity pojo) {
        return dao.update(pojo);
    }
}
