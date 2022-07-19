/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.config;

import io.renren.common.utils.Constant;
import io.renren.modules.front.shiro.BusinessUserRealm;
import io.renren.modules.front.shiro.GeneralUserRealm;
import io.renren.modules.front.shiro.MyModularRealmAuthenticator;
import io.renren.modules.front.shiro.MyModularRealmAuthorizer;
import io.renren.modules.sys.oauth2.OAuth2Filter;
import io.renren.modules.sys.oauth2.OAuth2Realm;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * Shiro配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
public class ShiroConfig {

    //给realm 取名字 方便和userType比较
    @Bean(Constant.GENERAL_USER)
    public GeneralUserRealm generalUserRealm() {
        return new GeneralUserRealm();
    }

    @Bean(Constant.BUSINESS_USER)
    public BusinessUserRealm businessUserRealm() {
        return new BusinessUserRealm();
    }

    //原来在component  注册  写在这里一起好管理
    @Bean(Constant.SYS_USER)
    public OAuth2Realm oauth2Realm() {return new OAuth2Realm();}

    //注册 自定义的多realms 管理
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        MyModularRealmAuthenticator myModularRealmAuthenticator = new MyModularRealmAuthenticator();
        myModularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return myModularRealmAuthenticator;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(OAuth2Realm oAuth2Realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //securityManager.setRealm(oAuth2Realm);
        //添加多个认证realm
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(oauth2Realm());
        realms.add(generalUserRealm());
        realms.add(businessUserRealm());
        // 需要再realm定义前
        securityManager.setAuthenticator(modularRealmAuthenticator());
        //增加授权控制
        securityManager.setAuthorizer(new MyModularRealmAuthorizer());

        securityManager.setRealms(realms);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        //oauth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2", new OAuth2Filter());
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/front/**", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/druid/**", "anon");
        filterMap.put("/app/**", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/captcha.jpg", "anon");
        filterMap.put("/aaa.txt", "anon");
        filterMap.put("/**", "oauth2");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
