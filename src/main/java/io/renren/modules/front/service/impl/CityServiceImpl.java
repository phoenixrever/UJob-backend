package io.renren.modules.front.service.impl;

import io.renren.common.utils.Constant;
import io.renren.modules.front.entity.FeatureEntity;
import io.renren.modules.front.entity.JapaneseEntity;
import io.renren.modules.front.entity.JobTypeEntity;
import io.renren.modules.front.service.*;
import io.renren.modules.front.vo.SelectionsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.front.dao.AreaDao;
import io.renren.modules.front.entity.CityEntity;


@Service("cityService")
public class CityServiceImpl extends ServiceImpl<AreaDao, CityEntity> implements CityService {
    @Autowired
    private FeatureService featureService;

    @Autowired
    private JapaneseService japaneseService;
    @Autowired
    private JobTypeService jobTypeService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CityEntity> page = this.page(
                new Query<CityEntity>().getPage(params),
                new QueryWrapper<CityEntity>()
        );

        return new PageUtils(page);
    }

    //todo 后端管理模板更新selection的时候记得更新缓存 呃这个项目不需要但是也最好页加个锁
    //CACHE:selections::getSelections
    @Cacheable(value = Constant.CACHE_PREFIX+"selections", key = "#root.methodName")
    public SelectionsVo getCacheSelections() {
        List<CityEntity> cityEntities = this.list();
        List<JapaneseEntity> japaneseEntities = japaneseService.list();
        List<JobTypeEntity> jobTypeEntities = jobTypeService.list();
        List<FeatureEntity> featureEntities = featureService.list();

        SelectionsVo selectionsVo = new SelectionsVo();
        selectionsVo.setJobTypes(jobTypeEntities);
        selectionsVo.setCities(cityEntities);
        selectionsVo.setJapaneses(japaneseEntities);
        selectionsVo.setFeatureEntities(featureEntities);
        return selectionsVo;
    }

}