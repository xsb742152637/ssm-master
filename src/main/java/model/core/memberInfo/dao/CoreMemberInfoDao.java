package model.core.memberInfo.dao;

import model.core.memberInfo.entity.CoreMemberInfoEntity;
import org.apache.ibatis.annotations.Param;

public interface CoreMemberInfoDao{
    CoreMemberInfoEntity findOne(@Param("account") String account,@Param("password")  String password);
}
