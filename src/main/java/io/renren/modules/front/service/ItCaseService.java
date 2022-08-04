package io.renren.modules.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.front.entity.*;
import io.renren.modules.front.vo.ItCaseDetailVo;

import java.util.List;
import java.util.Map;

/**
 * 案件列表
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
public interface ItCaseService extends IService<ItCaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryDetailPage(Map<String, Object> params);

    ItCaseDetailVo getDetailById(Long id);

    @Deprecated
    PageUtils queryHistoryPage(Map<String, Object> params);

    ItCaseDetailVo changeIdToName(ItCaseEntity itCaseEntity, List<LanguageEntity> languages, List<OsEntity> osEntities, List<JapaneseEntity> japaneses, List<ExperienceEntity> experiences, List<DbEntity> dbEntities, List<CityEntity> cities);
}

