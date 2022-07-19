package io.renren.modules.front.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.front.dao.FeatureDao;
import io.renren.modules.front.entity.FeatureEntity;
import io.renren.modules.front.service.FeatureService;


@Service("featureService")
public class FeatureServiceImpl extends ServiceImpl<FeatureDao, FeatureEntity> implements FeatureService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FeatureEntity> page = this.page(
                new Query<FeatureEntity>().getPage(params),
                new QueryWrapper<FeatureEntity>()
        );

        return new PageUtils(page);
    }

}