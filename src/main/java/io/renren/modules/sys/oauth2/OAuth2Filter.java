/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.oauth2;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.renren.common.utils.Constant;
import io.renren.common.utils.HttpContextUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.utils.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * oauth2过滤器
 *
 * @author Mark sunlightcs@gmail.com
 */
public class OAuth2Filter extends AuthenticatingFilter {

    /**
     * onAccessDenied 中 executeLogin 会调用这个方法
     * createToken token 字符串 转为 OAuth2Token 对象
     *  这边 OAuth2Token implements AuthenticationToken
     *  一般token实现的话用 UsernamePasswordToken 对象来实现
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        //null 是可以的 空字符串直接返回
        if(StringUtils.isBlank(token)){
            return null;
        }
        //从token中获取用户类型
        String userType = getUserType(token);

        return new OAuth2Token(token, userType);
    }

    private String getUserType(String token) {
        //获取token中的用户类型
        Claims claim = JwtUtils.getClaimByToken(token);
        String userType =null;
        if (claim != null) {
            userType = (String)claim.get("userType");
        }
        return userType;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }

        return false;
    }

    /**
     * 所有请求都会经过这里 请求不是anon 会被设置的路径filter 拦截
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        // 获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        //判断token是否存在
        if(StringUtils.isBlank(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

            String json = new Gson().toJson(R.error(HttpStatus.SC_UNAUTHORIZED, "invalid token"));

            httpResponse.getWriter().print(json);

            return false;
        }

        // executeLogin 里面 会调用createToken(request, response) 方法
        // subject.login 过程 会调用realm的doGetAuthenticationInfo方法
        return executeLogin(request, response);
    }

    /**
     * 第一次请求 没有token 会登录失败 抛出 onLoginFailure 来带到此方法
     * 并且在此处处理所有shiro登录失败异常
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            R r = R.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());

            String json = new Gson().toJson(r);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {

        }

        return false;
    }

    /**
     * 获取请求的token
     *
     * nuxt axios 传会的是 Authorization Bearer 被我false了
     * 这个 不知道怎么改 暂时不改了
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("token");
        }

        if (StringUtils.isBlank(token)) {
            token = httpRequest.getHeader("Authorization");
        }

        return token;
    }


}
