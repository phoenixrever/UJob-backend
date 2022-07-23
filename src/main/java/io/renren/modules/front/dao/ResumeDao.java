package io.renren.modules.front.dao;

import io.renren.modules.front.entity.ResumeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 简历表，备注简历信息可能和个人信息填的不一样。
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-23 17:11:00
 */
@Mapper
public interface ResumeDao extends BaseMapper<ResumeEntity> {
	
}
