package model.core.menuurl.service.impl;

import model.core.menuurl.dao.CoreMenuUrlDao;
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
    private CoreMenuUrlDao dao;
    /**
     * 获取实例
     */
    public static CoreMenuUrlServiceImpl getInstance() {
        return ApplicationContext.getCurrent().getBean(CoreMenuUrlServiceImpl.class);
    }

    @Override
    public List<CoreMenuUrlInfoEntity> findAll(){
        return dao.findAll();
    }
}
