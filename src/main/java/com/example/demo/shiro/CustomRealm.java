package com.example.demo.shiro;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/30 10:40 AM
 **/
public class CustomRealm extends AuthorizingRealm{
    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection){
        String name = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.getUserByName(name);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(user.getRole() == 1 && user.getStatus() == 1)
        simpleAuthorizationInfo.addStringPermission("normalUser");
        if(user.getRole() == 2 && user.getStatus() == 1)
        simpleAuthorizationInfo.addStringPermission("vip");
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)throws AuthenticationException{
        if(StringUtils.isEmpty(authenticationToken.getPrincipal())){
            return null;
        }
        String name = authenticationToken.getPrincipal().toString();
        User user = userService.getUserByName(name);
        if(user == null)
            return null;
        else{
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword(),getName());
            super.clearCachedAuthorizationInfo(simpleAuthenticationInfo.getPrincipals());
            SecurityUtils.getSubject().getSession().setAttribute("login",user);
            return simpleAuthenticationInfo;
        }
    }
}
