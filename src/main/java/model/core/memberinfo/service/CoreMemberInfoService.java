package model.core.memberinfo.service;

import model.core.memberinfo.entity.CoreMemberInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CoreMemberInfoService {

    /**
     * 根据用户名和密码验证登录是否成功
     * @param account
     * @param password
     * @return
     */
    CoreMemberInfoEntity loginCheck(String account, String password);

    /**
     * 用于判断账号是否存在
     * @param account
     * @return
     */
    int checkAccount(String account);
    CoreMemberInfoEntity findOneByTreeId(String treeId);
    CoreMemberInfoEntity findOne(String primaryId);
    List<CoreMemberInfoEntity> findAll();

    void insert(CoreMemberInfoEntity entity);
    void insert(List<CoreMemberInfoEntity> entity);
    int update( CoreMemberInfoEntity entity);
    void delete(String primaryId);
}
