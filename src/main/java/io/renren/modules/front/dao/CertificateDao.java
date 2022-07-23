package io.renren.modules.front.dao;

import io.renren.modules.front.entity.CertificateEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资格证书表
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-23 17:11:00
 */
@Mapper
public interface CertificateDao extends BaseMapper<CertificateEntity> {
	
}
