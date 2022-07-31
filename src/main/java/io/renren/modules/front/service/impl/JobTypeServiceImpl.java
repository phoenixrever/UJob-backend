package io.renren.modules.front.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.front.dao.JobTypeDao;
import io.renren.modules.front.entity.JobTypeEntity;
import io.renren.modules.front.service.JobTypeService;


@Service("menuService")
public class JobTypeServiceImpl extends ServiceImpl<JobTypeDao, JobTypeEntity> implements JobTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<JobTypeEntity> page = this.page(
                new Query<JobTypeEntity>().getPage(params),
                new QueryWrapper<JobTypeEntity>()
        );

        return new PageUtils(page);
    }

}