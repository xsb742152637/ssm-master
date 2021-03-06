package util.shiro;

import model.core.guide.service.core.MenuEx;
import model.core.memberinfo.entity.CoreMemberInfoEntity;
import model.core.memberinfo.service.CoreMemberInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import util.context.Context;

import java.util.Map;
import java.util.Set;

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private CoreMemberInfoService service;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        System.out.println("授权方法");
        //获取当前登录的用户
        CoreMemberInfoEntity member = (CoreMemberInfoEntity) arg0.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(info.getStringPermissions() == null || info.getStringPermissions().size() == 0){
            ////指示当前用户能访问的资源
            Set<String> set = Context.getGuide();
            for(String s : set){
                info.addStringPermission(s);
            }
        }

        //获取所有权限集合，循环添加
        //info.addStringPermission("/core/*");
        //info.addStringPermission("user:add");
        //获取所有角色集合，循环添加
        //info.addRole("admin");
        //info.addRole("经理");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        System.out.println("认证方法");
        UsernamePasswordToken token =(UsernamePasswordToken)arg0;
        String username = token.getUsername();
        String pwd = new String(token.getPassword());

        CoreMemberInfoEntity entity = service.loginCheck(username,pwd);
        if(entity == null){
            throw new UnknownAccountException();
        }else if(!entity.getMemberState()){
            // 帐号未启用(或账号被锁定)
            throw new LockedAccountException();
        }else{
            return new SimpleAuthenticationInfo(entity, token.getCredentials(), AuthRealm.class.getName());
        }
    }
}