package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.JapaneseEntity;

import java.util.Map;

/**
 * 日语程度
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
public interface JapaneseService extends IService<JapaneseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

