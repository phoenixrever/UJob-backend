package io.renren.modules.front.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.front.dao.OsDao;
import io.renren.modules.front.entity.OsEntity;
import io.renren.modules.front.service.OsService;


@Service("osService")
public class OsServiceImpl extends ServiceImpl<OsDao, OsEntity> implements OsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OsEntity> page = this.page(
                new Query<OsEntity>().getPage(params),
                new QueryWrapper<OsEntity>()
        );

        return new PageUtils(page);
    }

}