package io.renren.modules.front.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.front.dao.DbDao;
import io.renren.modules.front.entity.DbEntity;
import io.renren.modules.front.service.DbService;


@Service("dbService")
public class DbServiceImpl extends ServiceImpl<DbDao, DbEntity> implements DbService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DbEntity> page = this.page(
                new Query<DbEntity>().getPage(params),
                new QueryWrapper<DbEntity>()
        );

        return new PageUtils(page);
    }

}