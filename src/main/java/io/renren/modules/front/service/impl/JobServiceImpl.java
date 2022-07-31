package io.renren.modules.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.front.dao.JobDao;
import io.renren.modules.front.entity.*;
import io.renren.modules.front.service.*;
import io.renren.modules.front.vo.JobDetailVo;
import io.renren.modules.front.vo.UserJobInfoVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("caseService")
public class JobServiceImpl extends ServiceImpl<JobDao, JobEntity> implements JobService {
    @Autowired
    private FeatureService featureService;

    @Autowired
    private JapaneseService japaneseService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private CityService cityService;
    @Autowired
    private JobTypeService jobTypeService;

    @Autowired
    private UserCaseInfoService userCaseInfoService;

    private final static int CASE_TYPE = 0;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<JobEntity> page = this.page(
                new Query<JobEntity>().getPage(params),
                new QueryWrapper<JobEntity>()
        );

        return new PageUtils(page);
    }

    //todo  检查一下 插个职位数据好像太多sql语句了 还有前面的select选项表查的全是page 也要改了
    @Override
    public PageUtils queryDetailPage(Map<String, Object> params) {

        QueryWrapper<JobEntity> wrapper = new QueryWrapper<>();

        int jobType = 1;
        int city = 1;
        int japanese = 1;


        if(params.get("jobType")!=null){
            jobType = Integer.parseInt((String) params.get("jobType"));
            //1 是不限 所有都查询 注意里面不包含本身选项就是 无限 的 1
            if(jobType!=1){
                wrapper.eq("job_type",jobType);
            }

        }
        if(params.get("city")!=null){
            city = Integer.parseInt((String) params.get("city"));
            if(city!=1){
                wrapper.eq("city",city);
            }
        }
        if(params.get("japanese")!=null){
            japanese = Integer.parseInt((String) params.get("japanese"));
            if(japanese!=1){
                wrapper.eq("japanese",japanese);
            }
        }


        //处理分页参数和其他参数
        IPage<JobEntity> page = this.page(
                new Query<JobEntity>().getPage(params),
                //todo 测试数据时间爬取时间都设置成一样了 暂时先按id倒序排序 以后记得改
                //wrapper.orderByDesc("created_time")
                wrapper.orderByDesc("id")
        );
        List<JobEntity> records = page.getRecords();
        List<JobDetailVo> jobDetailVos = new ArrayList<>();

        for (JobEntity record : records) {
            JobDetailVo jobDetailVo = changeIdToName(record);
            jobDetailVos.add(jobDetailVo);
        }

        //UserPage<T> extends Page<T>  自定义setRecords 方法
        Page<JobDetailVo> caseDetailVoPage = new Page<>();
        //老的分页属性全部复制过来 (records T 泛型不同不能复制 )
        BeanUtils.copyProperties(page, caseDetailVoPage);

        caseDetailVoPage.setRecords(jobDetailVos);
        return new PageUtils(caseDetailVoPage);
    }

    @Override
    public JobDetailVo getDetailById(Long id) {
        JobEntity jobEntity = this.getById(id);
        JobDetailVo jobDetailVo = changeIdToName(jobEntity);
        return jobDetailVo;
    }

    //这边要把所有都查出来 我的投递也要用到
    //为了用 in 不在for循环里面查多做了一点
    @Override
    public PageUtils    queryHistoryPage(Map<String, Object> params) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
           throw new RRException("请登录");
        }

        GeneralUserEntity user = (GeneralUserEntity) principal;
        List<UserCaseInfoEntity> userCaseInfoEntities = userCaseInfoService.query().eq("user_id", user.getUserId()).eq("case_type", CASE_TYPE).orderByDesc("updated_time").list();
        if(userCaseInfoEntities.size()==0){
            return new PageUtils(new Page<>());
        }
        List<Long> list = userCaseInfoEntities.stream().map(userCaseInfoEntity -> userCaseInfoEntity.getCaseId()).collect(Collectors.toList());

        //拼接Sql按照id的顺序排序 因为有分页需求 不能所有数据查询出来再排序
        StringBuilder builder = new StringBuilder();
        builder.append("order by field(id,");
        int length = list.size();
        for(int i= 0; i<length; i++){
            if(i==0){
                builder.append(list.get(i));
            }else{
                builder.append(",")
                        .append(list.get(i));
            }
            if (i==length-1){
                builder.append(")");
            }
        }

        IPage<JobEntity> page = this.page(
                new Query<JobEntity>().getPage(params),
                new QueryWrapper<JobEntity>().in("id",list).last(builder.toString())
        );

        List<JobEntity> records = page.getRecords();
        List<UserJobInfoVo> userJobInfoVos = new ArrayList<>();

        for (int i = 0; i < records.size() ; i++) {
            JobDetailVo jobDetailVo = changeIdToName(records.get(i));
            UserJobInfoVo userJobInfoVo = new UserJobInfoVo();
            //2边id顺序是一样的
            BeanUtils.copyProperties(jobDetailVo, userJobInfoVo);
            BeanUtils.copyProperties(userCaseInfoEntities.get(i), userJobInfoVo);
            userJobInfoVos.add(userJobInfoVo);
        }

        //UserPage<T> extends Page<T>  自定义setRecords 方法
        Page<UserJobInfoVo> caseDetailVoPage = new Page<>();
        //老的分页属性全部复制过来 (records T 泛型不同不能复制 )
        BeanUtils.copyProperties(page, caseDetailVoPage);

        caseDetailVoPage.setRecords(userJobInfoVos);
        return new PageUtils(caseDetailVoPage);
    }

    private JobDetailVo changeIdToName(JobEntity jobEntity) {
        List<JapaneseEntity> japaneseEntities = japaneseService.list();
        List<CityEntity> cityEntities = cityService.list();
        List<JobTypeEntity> jobTypeEntities = jobTypeService.list();
        List<FeatureEntity> featureEntities = featureService.list();

        JobDetailVo jobDetailVo = new JobDetailVo();
        BeanUtils.copyProperties(jobEntity, jobDetailVo);
        ArrayList<String> temp = new ArrayList<>();

        String feature = jobEntity.getFeature();
        String[] split = feature.split(",");
        List<String> ids = Arrays.asList(split);
        featureEntities.forEach(featureEntity -> {
            if (ids.contains(String.valueOf(featureEntity.getId()))) {
                temp.add(featureEntity.getName());
            }
        });
        jobDetailVo.setFeature(String.join(",", temp));
        temp.clear();


        Integer jobType = jobEntity.getJobType();
        jobTypeEntities.forEach(jobTypeEntity -> {
            if (jobTypeEntity.getId().equals(jobType)) {
                jobDetailVo.setJobType(jobTypeEntity.getName());
            }
        });

        Integer city = jobEntity.getCity();
        cityEntities.forEach(cityEntity -> {
            if (city.equals(cityEntity.getId())) {
                jobDetailVo.setCity(cityEntity.getName());
            }
        });


        Integer japanese = jobEntity.getJapanese();
        japaneseEntities.forEach(japaneseEntity -> {
            if (japanese.equals(japaneseEntity.getId())) {
                jobDetailVo.setJapanese(japaneseEntity.getName());
            }
        });

        return jobDetailVo;
    }

}