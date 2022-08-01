package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.JobEntity;
import io.renren.modules.front.vo.JobDetailVo;
import io.renren.modules.front.vo.SelectionsVo;


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

    PageUtils queryHistoryPage(Map<String, Object> params);

}

