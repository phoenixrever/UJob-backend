/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.oauth2;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 *
 * userType 辨别token 类型 一般用户 系统用户 企业用户
 */
public class OAuth2Token implements AuthenticationToken {
    private String token;

    private String userType;

    public OAuth2Token(String token, String userType) {
        this.token = token;
        this.userType = userType;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public String getUserType() {
        return userType;
    }

}
