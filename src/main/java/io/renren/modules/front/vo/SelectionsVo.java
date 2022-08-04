package io.renren.modules.front.vo;

import io.renren.modules.front.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class SelectionsVo {
    private List<CityEntity> cities;
    private List<JobTypeEntity> jobTypes;
    private List<JapaneseEntity> japaneses;
    private List<FeatureEntity> featureEntities;
    private List<LanguageEntity> languages;
    private List<OsEntity> osEntities;
    private List<ExperienceEntity> experiences;
    private List<DbEntity> dbEntities;
}
