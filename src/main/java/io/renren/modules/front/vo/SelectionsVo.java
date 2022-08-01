package io.renren.modules.front.vo;

import io.renren.modules.front.entity.CityEntity;
import io.renren.modules.front.entity.FeatureEntity;
import io.renren.modules.front.entity.JapaneseEntity;
import io.renren.modules.front.entity.JobTypeEntity;
import lombok.Data;

import java.util.List;

@Data
public class SelectionsVo {
    private List<CityEntity> cities;
    private List<JobTypeEntity> jobTypes;
    private List<JapaneseEntity> japaneses;
    private List<FeatureEntity> featureEntities;

}
