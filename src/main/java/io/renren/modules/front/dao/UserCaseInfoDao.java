package io.renren.modules.front.dao;

import io.renren.modules.front.entity.UserCaseInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户案件信息表
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-25 20:44:20
 */
@Mapper
public interface UserCaseInfoDao extends BaseMapper<UserCaseInfoEntity> {
	
}
