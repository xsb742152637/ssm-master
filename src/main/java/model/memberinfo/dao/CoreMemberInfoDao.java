package model.memberinfo.dao;

import model.memberinfo.entity.CoreMemberInfoEntity;
import org.apache.ibatis.annotations.Param;

public interface CoreMemberInfoDao{
    CoreMemberInfoEntity findOne(@Param("account") String account,@Param("password")  String password);
}
