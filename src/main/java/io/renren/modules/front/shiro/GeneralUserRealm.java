package io.renren.modules.front.shiro;

import io.jsonwebtoken.Claims;
import io.renren.common.utils.Constant;
import io.renren.common.utils.RedisUtils;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.front.entity.GeneralUserEntity;
import io.renren.modules.sys.oauth2.OAuth2Token;
import io.renren.modules.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 前端一般用户登录Realm
 */
public class GeneralUserRealm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;
    @Autowired
    private RedisUtils  redisUtils;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        GeneralUserEntity user = (GeneralUserEntity)principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        //用户权限列表
        Set<String> permsSet = shiroService.getGeneralUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        Claims claim = JwtUtils.getClaimByToken(accessToken);
        if (claim == null) {
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        String username = (String) claim.get("username");

        //去redis里面查下是否有这个token 没有就是失效了 或者token不正确
        String redis_token = redisUtils.get(Constant.GENERAL_USER_TOKEN_PREFIX + username);
        if(StringUtils.isBlank(redis_token)){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        //查询用户信息
        GeneralUserEntity generalUser = shiroService.getGeneralUser(username);

        if (generalUser == null) {
            throw new UnknownAccountException("用户不存在");
        }

        //账号锁定
        if(generalUser.getStatus() == 0){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(generalUser, accessToken, getName());
        return info;
    }
}
