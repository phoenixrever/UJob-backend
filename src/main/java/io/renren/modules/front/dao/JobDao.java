package io.renren.modules.front.dao;

import io.renren.modules.front.entity.JobEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 案件列表
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-16 23:27:38
 */
@Mapper
public interface JobDao extends BaseMapper<JobEntity> {
	List<JobEntity> getCaseByIdOrder(List<Long> ids);
}
