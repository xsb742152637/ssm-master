package model.core.memberinfo.service.impl;

import model.core.memberinfo.dao.CoreMemberInfoDao;
import model.core.memberinfo.entity.CoreMemberInfoEntity;
import model.core.memberinfo.service.CoreMemberInfoService;
import model.core.menutree.entity.CoreMenuTreeInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.context.ApplicationContext;
import util.datamanage.GenericService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CoreMemberInfoServiceImpl extends GenericService<CoreMemberInfoEntity> implements CoreMemberInfoService {
    @Autowired
    private CoreMemberInfoDao dao;

    public static CoreMemberInfoServiceImpl getInstance() {
        return ApplicationContext.getCurrent().getBean(CoreMemberInfoServiceImpl.class);
    }

    @Override
    public CoreMemberInfoEntity findOne(String mainId) {
        return dao.findOne(mainId);
    }

    @Override
    public List<CoreMemberInfoEntity> findAll() {
        return dao.findAll();
    }
    @Override
    public CoreMemberInfoEntity findOneByTreeId(String treeId) {
        return dao.findOneByTreeId(treeId);
    }
    @Override
    public CoreMemberInfoEntity loginCheck(String account, String password) {
        return dao.loginCheck(account,password);
    }

    @Override
    @Transactional
    public void insert(CoreMemberInfoEntity entity) {
        dao.insert(convertList(entity));
    }

    @Override
    @Transactional
    public int update(CoreMemberInfoEntity entity) {
        return dao.update(convertList(entity));
    }

    @Override
    @Transactional
    public void delete(String mainId) {
        dao.delete(mainId);
    }
}
