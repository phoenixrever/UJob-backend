package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.CaseEntity;
import io.renren.modules.front.vo.CaseDetailVo;
import org.apache.poi.ss.formula.functions.T;


import java.util.Map;

/**
 * 案件列表
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-16 23:27:38
 */
public interface CaseService extends IService<CaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryDetailPage(Map<String, Object> params);

    CaseDetailVo getDetailById(Long id);

    PageUtils queryHistoryPage(Map<String, Object> params);
}

