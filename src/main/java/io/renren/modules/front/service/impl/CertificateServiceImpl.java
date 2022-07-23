package io.renren.modules.front.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.front.dao.CertificateDao;
import io.renren.modules.front.entity.CertificateEntity;
import io.renren.modules.front.service.CertificateService;


@Service("certificateService")
public class CertificateServiceImpl extends ServiceImpl<CertificateDao, CertificateEntity> implements CertificateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CertificateEntity> page = this.page(
                new Query<CertificateEntity>().getPage(params),
                new QueryWrapper<CertificateEntity>()
        );

        return new PageUtils(page);
    }

}