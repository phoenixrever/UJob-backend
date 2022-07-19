package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.FeatureEntity;

import java.util.Map;

/**
 * 
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-16 23:27:38
 */
public interface FeatureService extends IService<FeatureEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

