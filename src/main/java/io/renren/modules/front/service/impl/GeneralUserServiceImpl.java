package io.renren.modules.front.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.front.dao.GeneralUserDao;
import io.renren.modules.front.entity.GeneralUserEntity;
import io.renren.modules.front.service.GeneralUserService;


@Service("generalUserService")
public class GeneralUserServiceImpl extends ServiceImpl<GeneralUserDao, GeneralUserEntity> implements GeneralUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GeneralUserEntity> page = this.page(
                new Query<GeneralUserEntity>().getPage(params),
                new QueryWrapper<GeneralUserEntity>()
        );

        return new PageUtils(page);
    }

}