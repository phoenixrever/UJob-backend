package io.renren.modules.front.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.front.dao.JapaneseDao;
import io.renren.modules.front.entity.JapaneseEntity;
import io.renren.modules.front.service.JapaneseService;


@Service("japaneseService")
public class JapaneseServiceImpl extends ServiceImpl<JapaneseDao, JapaneseEntity> implements JapaneseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<JapaneseEntity> page = this.page(
                new Query<JapaneseEntity>().getPage(params),
                new QueryWrapper<JapaneseEntity>()
        );

        return new PageUtils(page);
    }

}