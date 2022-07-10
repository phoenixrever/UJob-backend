package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.ExperienceEntity;

import java.util.Map;

/**
 * 工作经验
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
public interface ExperienceService extends IService<ExperienceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

