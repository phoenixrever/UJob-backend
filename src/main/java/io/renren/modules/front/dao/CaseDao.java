package io.renren.modules.front.dao;

import io.renren.modules.front.entity.CaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 案件列表
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-16 23:27:38
 */
@Mapper
public interface CaseDao extends BaseMapper<CaseEntity> {
	
}
