package model.core.memberInfo.service;

import model.core.memberInfo.entity.CoreMemberInfoEntity;

public interface CoreMemberInfoService {
    /**
     * 根据用户名和密码验证登录是否成功
     * @param account
     * @param password
     * @return
     */
    CoreMemberInfoEntity findOne(String account, String password);
}
