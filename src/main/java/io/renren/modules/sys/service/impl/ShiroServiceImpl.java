/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import io.renren.common.utils.Constant;
import io.renren.modules.front.entity.GeneralUserEntity;
import io.renren.modules.front.service.GeneralUserService;
import io.renren.modules.sys.dao.SysMenuDao;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.dao.SysUserTokenDao;
import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.SysUserTokenEntity;
import io.renren.modules.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserTokenDao sysUserTokenDao;

    @Autowired
    private GeneralUserService generalUserService;


    //获取sys 系统用户权限列表
    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(SysMenuEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    //获取前端一般用户权限列表
    @Override
    public Set<String> getGeneralUserPermissions(long userId) {
        GeneralUserEntity generalUser = generalUserService.getById(userId);
        Set<String> permsSet = new HashSet<>();
        permsSet.add(generalUser.getPermissionsIds());
        //todo 创建用户对应的权限表，用户id为主键，权限为值，比如：1:front:user:add,front:user:update,front:user:delete
        //不过前端比较简单好像不需要啥权限 暂时简单返回ids
        return permsSet;
    }

    //获取前端企业用户权限列表
    @Override
    public Set<String> getBusinessUserPermissions(long userId) {
        return null;
    }


    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    @Override
    public SysUserEntity queryUser(Long userId) {
        return sysUserDao.selectById(userId);
    }

    //todo 更新用户信息的时候记得更新缓存
    @Cacheable(value = Constant.CACHE_PREFIX+"user", key = "#root.args[0]")
    @Override
    public GeneralUserEntity getGeneralUser(String username) {
        GeneralUserEntity  generalUser = generalUserService.query().eq("username", username).one();

        return generalUser;
    }
}
