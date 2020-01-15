package model.core.memberInfo.service.impl;

import model.core.memberInfo.dao.CoreMemberInfoDao;
import model.core.memberInfo.entity.CoreMemberInfoEntity;
import model.core.memberInfo.service.CoreMemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.dataManage.GenericService;

@Service
public class CoreMemberInfoServiceImpl extends GenericService implements CoreMemberInfoService {
    @Autowired
    private CoreMemberInfoDao dao;
    @Override
    public CoreMemberInfoEntity findOne(String account, String password) {
        return dao.findOne(account,password);
    }

    @Transactional
    public void add(CoreMemberInfoEntity entity){
        try{

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}
