package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.AreaEntity;

import java.util.Map;

/**
 * 工作地点
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
public interface AreaService extends IService<AreaEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

