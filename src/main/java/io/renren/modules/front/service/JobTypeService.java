package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.JobTypeEntity;

import java.util.Map;

/**
 * 所属分类
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
public interface JobTypeService extends IService<JobTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

