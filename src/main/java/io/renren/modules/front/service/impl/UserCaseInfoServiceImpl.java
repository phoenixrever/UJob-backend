package io.renren.modules.front.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.exception.RRException;
import io.renren.modules.front.entity.GeneralUserEntity;
import io.renren.modules.front.entity.ItCaseEntity;
import io.renren.modules.front.entity.JobEntity;
import io.renren.modules.front.service.CityService;
import io.renren.modules.front.service.ItCaseService;
import io.renren.modules.front.service.JobService;
import io.renren.modules.front.vo.ItCaseDetailVo;
import io.renren.modules.front.vo.JobDetailVo;
import io.renren.modules.front.vo.SelectionsVo;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private JobService jobService;
    @Autowired
    private ItCaseService itCaseService;
    @Autowired
    private CityService cityService;



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserCaseInfoEntity> page = this.page(
                new Query<UserCaseInfoEntity>().getPage(params),
                new QueryWrapper<UserCaseInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils querySearchPage(Map<String, Object> params, String search) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            throw new RRException("请登录");
        }
        GeneralUserEntity user = (GeneralUserEntity) principal;

        QueryWrapper<UserCaseInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getUserId());
        switch (search) {
            case "history":
                wrapper.gt("visited",0).isNotNull("visited_time").orderByDesc("visited_time");
                break;
            case "favorite":
                wrapper.gt("favorite",0).isNotNull("favorite_time").orderByDesc("favorite_time");
                break;
                //todo delivery reject communication interview
        }

        IPage<UserCaseInfoEntity> page = this.page(
                new Query<UserCaseInfoEntity>().getPage(params),
                wrapper
        );

        List<UserCaseInfoEntity> userCaseInfoEntities = page.getRecords();

        if(userCaseInfoEntities.size() == 0) {
            return new PageUtils(page);
        }
        //0 职位  1 IT案件
        ArrayList<Object> result = new ArrayList<>();
        ArrayList<Long> jobIds = new ArrayList<>();
        ArrayList<Long> itCaseIds = new ArrayList<>();
        userCaseInfoEntities.forEach(userCaseInfoEntity -> {
            if(userCaseInfoEntity.getCaseType()==0){
                jobIds.add(userCaseInfoEntity.getCaseId());
            }else{
                itCaseIds.add(userCaseInfoEntity.getCaseId());
            }
        });

        SelectionsVo selections = cityService.getCacheSelections();

        List<JobDetailVo> jobDetailVos=null;
        if(jobIds.size()>0){
            List<JobEntity> jobEntities = jobService.listByIds(jobIds);

            jobDetailVos = jobEntities.stream().map(jobEntity -> {
                JobDetailVo jobDetailVo = jobService.changeIdToName(jobEntity, selections.getJapaneses(), selections.getCities(), selections.getJobTypes(), selections.getFeatureEntities());
                return jobDetailVo;
            }).collect(Collectors.toList());
        }

        List<ItCaseDetailVo> itCaseDetailVos =null;
        if(itCaseIds.size()>0){
            List<ItCaseEntity> itCaseEntities = itCaseService.listByIds(itCaseIds);

             itCaseDetailVos = itCaseEntities.stream().map(itCaseEntity -> {
                ItCaseDetailVo itCaseDetailVo = itCaseService.changeIdToName(itCaseEntity, selections.getLanguages(), selections.getOsEntities(), selections.getJapaneses(), selections.getExperiences(), selections.getDbEntities(), selections.getCities());
                return itCaseDetailVo;
            }).collect(Collectors.toList());
        }



        //todo 历史记录的话应该是 userJobInfoEntity user 和 case 测合并vo 暂时先这样 还没想好历史记录显示什么

        //再次遍历
        List<JobDetailVo> finalJobDetailVos = jobDetailVos;
        List<ItCaseDetailVo> finalItCaseDetailVos = itCaseDetailVos;
        userCaseInfoEntities.forEach(userCaseInfoEntity -> {
            if(userCaseInfoEntity.getCaseType()==0 && finalJobDetailVos!=null){
                for(JobDetailVo jobDetailVo: finalJobDetailVos){
                    if(jobDetailVo.getId().equals(userCaseInfoEntity.getCaseId())){
                        result.add(jobDetailVo);
                    }
                }
            }else if(userCaseInfoEntity.getCaseType()==1 && finalItCaseDetailVos!=null){
                for(ItCaseDetailVo itCaseDetailVo: finalItCaseDetailVos){
                    if(itCaseDetailVo.getId().equals(userCaseInfoEntity.getCaseId())){
                        result.add(itCaseDetailVo);
                    }
                }
            }
        });
        IPage<Object> objectIPage =new Page<>();
        BeanUtils.copyProperties(page,objectIPage);
        objectIPage.setRecords(result);
        return new PageUtils(objectIPage);
    }
}