package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.CertificateEntity;

import java.util.Map;

/**
 * 资格证书表
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-23 17:11:00
 */
public interface CertificateService extends IService<CertificateEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

