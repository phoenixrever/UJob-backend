package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.GeneralUserEntity;

import java.util.Map;

/**
 * 一般用户
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-18 14:22:59
 */
public interface GeneralUserService extends IService<GeneralUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

