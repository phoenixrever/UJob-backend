package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.GoodsEntity;

import java.util.Map;

/**
 * 商品管理
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-06-26 11:41:01
 */
public interface GoodsService extends IService<GoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

