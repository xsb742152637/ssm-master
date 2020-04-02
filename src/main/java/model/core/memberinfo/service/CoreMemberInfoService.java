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
    CoreMemberInfoEntity findOne(String account, String password);

    CoreMemberInfoEntity findOnebyId(String mainId);

    List<Map<String,Object>> getMainInfo(String parentId);

    void deleteMain( String mainId);

    void insert(CoreMemberInfoEntity entity,CoreMemberInfoEntity fEntity);

    int insertList(List<CoreMemberInfoEntity> list);

    int update( CoreMemberInfoEntity entity);

    void moveMainInfo(String mainId,Boolean moveOn);

    int updateList(List<CoreMemberInfoEntity> list);

}
