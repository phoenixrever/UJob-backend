package io.renren.modules.front.dao;

import io.renren.modules.front.entity.GeneralUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 一般用户
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-18 14:22:59
 */
@Mapper
public interface GeneralUserDao extends BaseMapper<GeneralUserEntity> {
	
}
