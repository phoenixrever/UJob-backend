package io.renren.modules.front.service.impl;

import io.renren.modules.front.entity.OsEntity;
import io.renren.modules.front.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.front.dao.LanguageDao;
import io.renren.modules.front.entity.LanguageEntity;


@Service("languageService")
public class LanguageServiceImpl extends ServiceImpl<LanguageDao, LanguageEntity> implements LanguageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LanguageEntity> page = this.page(
                new Query<LanguageEntity>().getPage(params),
                new QueryWrapper<LanguageEntity>()
        );

        return new PageUtils(page);
    }

}