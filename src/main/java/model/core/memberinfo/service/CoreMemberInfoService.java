package model.core.memberinfo.service;

import model.core.memberinfo.entity.CoreMemberInfoEntity;

public interface CoreMemberInfoService {
    /**
     * 根据用户名和密码验证登录是否成功
     * @param account
     * @param password
     * @return
     */
    CoreMemberInfoEntity findOne(String account, String password);
}
