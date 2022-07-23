package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.ResumeEntity;

import java.util.Map;

/**
 * 简历表，备注简历信息可能和个人信息填的不一样。
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-23 17:11:00
 */
public interface ResumeService extends IService<ResumeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveWithCertificate(ResumeEntity resume);

    ResumeEntity getWithCertificateById(Long id);
}

