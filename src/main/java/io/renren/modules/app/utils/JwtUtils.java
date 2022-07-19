/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@ConfigurationProperties(prefix = "renren.jwt")
@Component
public class JwtUtils {
    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private static String secret;
    private static long expire;
    private static  String header;

    /**
     * 生成jwt token
     *
     * realm 根据subject的类型来判断，
     * userType  如果是一般用户，则为generalUser，如果是管企业用户，则为businessUser
     *
     * renren fast 并没有使用这个util 直接自己生成token字符串 过期时间存sql里面了
     * app 里面倒是用了
     * 有点奇怪 暂时不管他
     *
     * 暂时先用着 不行再换另外一个 jwt util
     *
     * 坑 将set方法的静态static 去点即可，因为@ConfigurationProperties只会调用 非静态的set方法
     */
    public static String generateToken(String username,String userType) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("userType", userType);
        //先用 claims Subject 暂时不知道怎么取出来

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            logger.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public  boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    public   String getSecret() {
        return secret;
    }


    public  long getExpire() {
        return expire;
    }


    public  String getHeader() {
        return header;
    }


    public  void setSecret(String secret) {
        JwtUtils.secret = secret;
    }

    public  void setExpire(long expire) {
        JwtUtils.expire = expire;
    }

    public  void setHeader(String header) {
        JwtUtils.header = header;
    }
}
