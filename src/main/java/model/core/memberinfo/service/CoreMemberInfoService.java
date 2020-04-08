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
    CoreMemberInfoEntity findOneByTreeId(String treeId);
    CoreMemberInfoEntity findOne(String mainId);
    List<CoreMemberInfoEntity> findAll();

    void insert(CoreMemberInfoEntity entity);
    int update( CoreMemberInfoEntity entity);
    void delete(String mainId);
}
