package io.renren.modules.front.dao;

import io.renren.modules.front.entity.CityEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工作地点
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
@Mapper
public interface AreaDao extends BaseMapper<CityEntity> {
	
}
