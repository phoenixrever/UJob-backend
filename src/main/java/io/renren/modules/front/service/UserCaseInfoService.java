package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.UserCaseInfoEntity;

import java.util.Map;

/**
 * 用户案件信息表
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-25 20:44:20
 */
public interface UserCaseInfoService extends IService<UserCaseInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

