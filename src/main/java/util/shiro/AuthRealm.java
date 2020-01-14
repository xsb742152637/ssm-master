package util.shiro;

import model.memberinfo.entity.CoreMemberInfoEntity;
import model.memberinfo.service.CoreMemberInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private CoreMemberInfoService service;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        System.out.println("授权方法");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        CoreMemberInfoEntity member = (CoreMemberInfoEntity) arg0.getPrimaryPrincipal();//获得当前登陆的用户
        //Set<Role> roles = user.getRoles();//当前用户拥有的角色，根据自己的entity
        ////指示当前用户能访问的资源
        //for(Role role : roles) {
        //    info.addStringPermission(role.getName());
        //}
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        System.out.println("认证方法");
        UsernamePasswordToken token =(UsernamePasswordToken)arg0;
        String username = token.getUsername();
        String pwd = new String(token.getPassword());

        try{
            CoreMemberInfoEntity entity = service.findOne(username,pwd);
            if(entity != null)
                return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), AuthRealm.class.getName());
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}