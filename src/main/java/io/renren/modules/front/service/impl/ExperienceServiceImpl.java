package io.renren.modules.front.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.front.dao.ExperienceDao;
import io.renren.modules.front.entity.ExperienceEntity;
import io.renren.modules.front.service.ExperienceService;


@Service("experienceService")
public class ExperienceServiceImpl extends ServiceImpl<ExperienceDao, ExperienceEntity> implements ExperienceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ExperienceEntity> page = this.page(
                new Query<ExperienceEntity>().getPage(params),
                new QueryWrapper<ExperienceEntity>()
        );

        return new PageUtils(page);
    }

}