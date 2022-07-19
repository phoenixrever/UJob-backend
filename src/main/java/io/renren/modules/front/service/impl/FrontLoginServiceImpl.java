package io.renren.modules.front.service.impl;

import io.renren.common.utils.Constant;
import io.renren.common.utils.RedisUtils;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.front.service.FrontLoginService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("frontLoginService")
public class FrontLoginServiceImpl implements FrontLoginService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public String createJWtToken(String username, String userType) {
        String token = JwtUtils.generateToken(username, userType);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        redisUtils.set(Constant.GENERAL_USER_TOKEN_PREFIX+username, token);
        return token;
    }
}
