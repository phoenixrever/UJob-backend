package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.*;
import io.renren.modules.front.vo.JobDetailVo;
import io.renren.modules.front.vo.SelectionsVo;


import java.util.List;
import java.util.Map;

/**
 * 案件列表
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-16 23:27:38
 */
public interface JobService extends IService<JobEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryDetailPage(Map<String, Object> params);

    JobDetailVo getDetailById(Long id);

    @Deprecated
    PageUtils queryHistoryPage(Map<String, Object> params);

     JobDetailVo changeIdToName(JobEntity jobEntity, List<JapaneseEntity> japaneseEntities
            , List<CityEntity> cityEntities, List<JobTypeEntity> jobTypeEntities, List<FeatureEntity> featureEntities);


}

