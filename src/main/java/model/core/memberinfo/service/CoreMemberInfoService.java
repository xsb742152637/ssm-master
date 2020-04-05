package model.core.memberinfo.service;

import model.core.memberinfo.entity.CoreMemberInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CoreMemberInfoService {

    CoreMemberInfoEntity findOnebyId(String mainId);

    List<Map<String,Object>> getMainInfo(String parentId);

    /**
     * 根据用户名和密码验证登录是否成功
     * @param account
     * @param password
     * @return
     */
    CoreMemberInfoEntity findOne(String account, String password);

    void insert(CoreMemberInfoEntity entity,CoreMemberInfoEntity fEntity);

    int insert(List<CoreMemberInfoEntity> list);

    int update( CoreMemberInfoEntity entity);
    int update(List<CoreMemberInfoEntity> list);

    void moveMainInfo(String mainId,Boolean moveOn);

    void delete(String mainId);
}
