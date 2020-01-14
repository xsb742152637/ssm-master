package model.memberinfo.service.impl;

import model.memberinfo.dao.CoreMemberInfoDao;
import model.memberinfo.entity.CoreMemberInfoEntity;
import model.memberinfo.service.CoreMemberInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoreMemberInfoServiceImpl implements CoreMemberInfoService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
