package com.example.demo.config;

import com.example.demo.shiro.CustomRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/30 10:42 AM
 **/
@Configuration
public class shiroConfig {

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    //将自己的验证方式加入容器
    @Bean
    public CustomRealm myShiroRealm(){
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }

    //权限管理，配置Realm的管理认证
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myShiroRealm());
        return defaultWebSecurityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String, String> map = new HashMap<>();
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        //不会被拦截的链接
        map.put("/hotel/getList","anon");
        map.put("/hotel/getDetail","anon");
        map.put("/hotel/searchByName","anon");
        map.put("/hotel/screen","anon");
        map.put("/hotel/screenAndSearch","anon");
        //vip用户权限配置，待写   map.put("/","权限码")

        //authc:所有url都必须认证通过才可以访问；anon：所有url都可以匿名访问
        map.put("/user/logout","logout");
        map.put("/","user");
        map.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
}
