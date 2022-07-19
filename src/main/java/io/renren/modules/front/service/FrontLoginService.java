package io.renren.modules.front.service;


public interface FrontLoginService {
    String createJWtToken(String username, String userType);
}
