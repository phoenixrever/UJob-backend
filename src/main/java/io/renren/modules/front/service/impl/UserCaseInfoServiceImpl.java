package io.renren.modules.front.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.exception.RRException;
import io.renren.common.utils.R;
import io.renren.common.utils.ShiroUtils;
import io.renren.modules.front.entity.GeneralUserEntity;
import io.renren.modules.front.entity.ItCaseEntity;
import io.renren.modules.front.entity.JobEntity;
import io.renren.modules.front.service.CityService;
import io.renren.modules.front.service.ItCaseService;
import io.renren.modules.front.service.JobService;
import io.renren.modules.front.vo.DeliveryVo;
import io.renren.modules.front.vo.ItCaseDetailVo;
import io.renren.modules.front.vo.JobDetailVo;
import io.renren.modules.front.vo.SelectionsVo;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.front.dao.UserCaseInfoDao;
import io.renren.modules.front.entity.UserCaseInfoEntity;
import io.renren.modules.front.service.UserCaseInfoService;
import org.springframework.validation.annotation.Validated;


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
                wrapper.gt("visited", 0).isNotNull("visited_time").orderByDesc("visited_time");
                break;
            case "favorite":
                wrapper.gt("favorite", 0).isNotNull("favorite_time").orderByDesc("favorite_time");
                break;
            //todo delivery reject communication interview
            case "delivery":
                wrapper.gt("delivery", 0).isNotNull("delivery_time").orderByDesc("delivery_time");
                break;
            case "unqualified":
                wrapper.gt("unqualified", 0).isNotNull("unqualified_time").orderByDesc("unqualified_time");
                break;
            case "communication":
                wrapper.gt("communicate", 0).isNotNull("communicate_time").orderByDesc("communicate_time");
                break;
            case "checked":
                wrapper.gt("checked", 0).isNotNull("checked_time").orderByDesc("checked_time");
                break;
            case "interview":
                wrapper.gt("interview", 0).isNotNull("interview_time").orderByDesc("interview_time");
                break;
            default:
                wrapper.orderByDesc("update_time");
        }

        IPage<UserCaseInfoEntity> page = this.page(
                new Query<UserCaseInfoEntity>().getPage(params),
                wrapper
        );

        List<UserCaseInfoEntity> userCaseInfoEntities = page.getRecords();

        if (userCaseInfoEntities.size() == 0) {
            return new PageUtils(page);
        }
        //0 职位  1 IT案件
        ArrayList<Object> result = new ArrayList<>();
        ArrayList<Long> jobIds = new ArrayList<>();
        ArrayList<Long> itCaseIds = new ArrayList<>();
        userCaseInfoEntities.forEach(userCaseInfoEntity -> {
            if (userCaseInfoEntity.getCaseType() == 0) {
                jobIds.add(userCaseInfoEntity.getCaseId());
            } else {
                itCaseIds.add(userCaseInfoEntity.getCaseId());
            }
        });

        SelectionsVo selections = cityService.getCacheSelections();

        List<JobDetailVo> jobDetailVos = null;
        if (jobIds.size() > 0) {
            List<JobEntity> jobEntities = jobService.listByIds(jobIds);
            if(jobEntities.size() > 0){
                jobDetailVos = jobEntities.stream().map(jobEntity -> {
                    JobDetailVo jobDetailVo = jobService.changeIdToName(jobEntity, selections.getJapaneses(), selections.getCities(), selections.getJobTypes(), selections.getFeatureEntities());
                    return jobDetailVo;
                }).collect(Collectors.toList());
            }
        }

        List<ItCaseDetailVo> itCaseDetailVos = null;
        if (itCaseIds.size() > 0) {
            List<ItCaseEntity> itCaseEntities = itCaseService.listByIds(itCaseIds);

            if(itCaseEntities.size()>0){
                itCaseDetailVos = itCaseEntities.stream().map(itCaseEntity -> {
                    ItCaseDetailVo itCaseDetailVo = itCaseService.changeIdToName(itCaseEntity, selections.getLanguages(), selections.getOsEntities(), selections.getJapaneses(), selections.getExperiences(), selections.getDbEntities(), selections.getCities());
                    return itCaseDetailVo;
                }).collect(Collectors.toList());
            }
        }


        //todo 历史记录的话应该是 userJobInfoEntity user 和 case 测合并vo 暂时先这样 还没想好历史记录显示什么

        //再次遍历
        List<JobDetailVo> finalJobDetailVos = jobDetailVos;
        List<ItCaseDetailVo> finalItCaseDetailVos = itCaseDetailVos;
        userCaseInfoEntities.forEach(userCaseInfoEntity -> {
            if (userCaseInfoEntity.getCaseType() == 0 && finalJobDetailVos != null) {
                for (JobDetailVo jobDetailVo : finalJobDetailVos) {
                    if (jobDetailVo.getId().equals(userCaseInfoEntity.getCaseId())) {
                        result.add(jobDetailVo);
                    }
                }
            } else if (userCaseInfoEntity.getCaseType() == 1 && finalItCaseDetailVos != null) {
                for (ItCaseDetailVo itCaseDetailVo : finalItCaseDetailVos) {
                    if (itCaseDetailVo.getId().equals(userCaseInfoEntity.getCaseId())) {
                        result.add(itCaseDetailVo);
                    }
                }
            }
        });
        IPage<Object> objectIPage = new Page<>();
        BeanUtils.copyProperties(page, objectIPage);
        objectIPage.setRecords(result);
        return new PageUtils(objectIPage);
    }

    @Override
    public void setDeliveryWithIds(@Validated DeliveryVo deliveryVo) {
        Integer caseType = deliveryVo.getCaseType();

        List<Long> caseIds = deliveryVo.getCaseIds();

        List<UserCaseInfoEntity> toUpdate = new ArrayList<>();
        List<UserCaseInfoEntity> toSave = new ArrayList<>();
        Date date = new Date();
        caseIds.forEach(caseId -> {
            UserCaseInfoEntity userCaseInfoEntity = this.query()
                    .eq("case_id",caseId).
                    eq("case_type", deliveryVo.getCaseType()).one();
            if (userCaseInfoEntity != null) {
                Integer delivery = userCaseInfoEntity.getDelivery();
                userCaseInfoEntity.setDelivery(delivery+1);
                userCaseInfoEntity.setDeliveryTime(date);
                toUpdate.add(userCaseInfoEntity);
            }else {
                //投递简历可以不需要点进详情投递 userCaseInfoEntity 不一定存在
                GeneralUserEntity generalUser = (GeneralUserEntity) ShiroUtils.getSubject().getPrincipal();
                UserCaseInfoEntity entity = new UserCaseInfoEntity();
                entity.setCaseId(caseId);
                entity.setDelivery(1);
                entity.setUserId(generalUser.getUserId());
                entity.setCaseType(caseType);
                entity.setDeliveryTime(date);
                toSave.add(entity);
            }
        });
        if(toUpdate.size() > 0) {
            this.updateBatchById(toUpdate);
        }
        if(toSave.size() > 0) {
            this.saveBatch(toSave);
        }
    }

    //caseType 方法不合并成一个也没关系
    @Override
    public ItCaseDetailVo getItCaseDetailVo(Long id) {
        int CASE_TYPE = 1;

        ItCaseDetailVo itCaseDetailVo = itCaseService.getDetailById(id);

        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal != null) {
            //先查看对应关系是否存在
            UserCaseInfoEntity caseInfoEntity = this.query().eq("case_id", id).eq("case_type", CASE_TYPE).one();
            if (caseInfoEntity == null) {
                GeneralUserEntity generalUser = (GeneralUserEntity) principal;
                Long userId = generalUser.getUserId();
                UserCaseInfoEntity userCaseInfoEntity = new UserCaseInfoEntity();
                userCaseInfoEntity.setCaseId(id);
                userCaseInfoEntity.setUserId(userId);
                userCaseInfoEntity.setCaseType(CASE_TYPE);
                userCaseInfoEntity.setVisited(1);
                userCaseInfoEntity.setBusinessUserId(itCaseDetailVo.getBusinessUserId());
                this.save(userCaseInfoEntity);
            } else {
                caseInfoEntity.setVisited(caseInfoEntity.getVisited() + 1);
                caseInfoEntity.setVisitedTime(new Date());
                this.updateById(caseInfoEntity);
                itCaseDetailVo.setDelivered(caseInfoEntity.getDelivery());
                itCaseDetailVo.setDeliveredTime(caseInfoEntity.getDeliveryTime());
                itCaseDetailVo.setFavorite(caseInfoEntity.getFavorite());
            }
        }
        return itCaseDetailVo;
    }

    @Override
    public JobDetailVo getJobDetailVo(Long id) {
        int CASE_TYPE = 0; //职位
        JobDetailVo jobDetailVo = jobService.getDetailById(id);
        //如果已经登录 记录到历史记录里面 每次查询都会记录一次
        //todo 如果未登录用 需不需要保存记录 登陆后合并 待决定
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal != null) {
            //先查看对应关系是否存在
            UserCaseInfoEntity caseInfoEntity = this.query().eq("case_id", id).eq("case_type", CASE_TYPE).one();
            if (caseInfoEntity == null) {
                GeneralUserEntity generalUser = (GeneralUserEntity) principal;
                Long userId = generalUser.getUserId();
                UserCaseInfoEntity userCaseInfoEntity = new UserCaseInfoEntity();
                userCaseInfoEntity.setCaseId(id);
                userCaseInfoEntity.setUserId(userId);
                userCaseInfoEntity.setCaseType(CASE_TYPE);
                userCaseInfoEntity.setVisited(1);
                userCaseInfoEntity.setVisitedTime(new Date());
                userCaseInfoEntity.setBusinessUserId(jobDetailVo.getBusinessUserId());
                this.save(userCaseInfoEntity);
            } else {
                caseInfoEntity.setVisited(caseInfoEntity.getVisited() + 1);
                caseInfoEntity.setVisitedTime(new Date());
                this.updateById(caseInfoEntity);
                jobDetailVo.setDelivered(caseInfoEntity.getDelivery());
                jobDetailVo.setDeliveredTime(caseInfoEntity.getDeliveryTime());
                jobDetailVo.setFavorite(caseInfoEntity.getFavorite());
            }
        }
        return jobDetailVo;
    }
}