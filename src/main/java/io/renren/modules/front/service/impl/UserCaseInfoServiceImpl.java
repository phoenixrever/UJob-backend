package io.renren.modules.front.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.front.dao.UserCaseInfoDao;
import io.renren.modules.front.entity.UserCaseInfoEntity;
import io.renren.modules.front.service.UserCaseInfoService;


@Service("userCaseInfoService")
public class UserCaseInfoServiceImpl extends ServiceImpl<UserCaseInfoDao, UserCaseInfoEntity> implements UserCaseInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserCaseInfoEntity> page = this.page(
                new Query<UserCaseInfoEntity>().getPage(params),
                new QueryWrapper<UserCaseInfoEntity>()
        );

        return new PageUtils(page);
    }

}