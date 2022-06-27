package io.renren.modules.front.dao;

import io.renren.modules.front.entity.GoodsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品管理
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-06-26 11:41:01
 */
@Mapper
public interface GoodsDao extends BaseMapper<GoodsEntity> {
	
}
