package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.UserCaseInfoEntity;
import io.renren.modules.front.vo.DeliveryVo;
import io.renren.modules.front.vo.ItCaseDetailVo;
import io.renren.modules.front.vo.JobDetailVo;

import java.util.Map;

/**
 * 用户案件信息表
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-25 20:44:20
 */
public interface UserCaseInfoService extends IService<UserCaseInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils querySearchPage(Map<String, Object> params, String search);

    void setDeliveryWithIds(DeliveryVo deliveryVo);

    ItCaseDetailVo getItCaseDetailVo(Long id);

    JobDetailVo getJobDetailVo(Long id);
}

