/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service;

import io.renren.modules.front.entity.GeneralUserEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * shiro相关接口
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    //获取前端一般用户权限列表
    Set<String> getGeneralUserPermissions(long userId);

    //获取前端企业用户权限列表
    Set<String> getBusinessUserPermissions(long userId);


    SysUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUserEntity queryUser(Long userId);

    /**
     * 根据用户username，查询前端一般用户
     * @param username
     */
    GeneralUserEntity  getGeneralUser(String username);

}
