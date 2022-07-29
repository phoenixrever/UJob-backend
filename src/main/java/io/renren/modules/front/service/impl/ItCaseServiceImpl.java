package io.renren.modules.front.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.exception.RRException;
import io.renren.modules.front.entity.*;
import io.renren.modules.front.service.*;
import io.renren.modules.front.vo.CaseDetailVo;
import io.renren.modules.front.vo.ItCaseDetailVo;
import io.renren.modules.front.vo.UserCaseInfoVo;
import io.renren.modules.front.vo.UserItCaseInfoVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.front.dao.ItCaseDao;


@Service("itCaseService")
public class ItCaseServiceImpl extends ServiceImpl<ItCaseDao, ItCaseEntity> implements ItCaseService {
    @Autowired
    private LanguageService languageService;
    @Autowired
    private OsService osService;
    @Autowired
    private JapaneseService japaneseService;
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private DbService dbService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private UserCaseInfoService userCaseInfoService;

    private final static int CASE_TYPE = 1;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ItCaseEntity> page = this.page(
                new Query<ItCaseEntity>().getPage(params),
                new QueryWrapper<ItCaseEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public PageUtils queryDetailPage(Map<String, Object> params) {

        System.out.println(params);
        QueryWrapper<ItCaseEntity> wrapper = new QueryWrapper<>();

        int experienceId = 1;
        int area = 1;
        int japanese = 1;

        if(params.get("experience")!=null){
            experienceId = Integer.parseInt((String) params.get("experience"));
            //1 是不限 所有都查询 注意里面不包含本身选项就是 无限 的 1
            if(experienceId!=1){
                wrapper.eq("experience",experienceId);
            }

        }
        if(params.get("area")!=null){
            area = Integer.parseInt((String) params.get("area"));
            //System.out.println(area);
            //System.out.println(area == 1);
            //1 是不限 所有都查询
            if(area!=1){
                wrapper.eq("area",area);
            }
        }
        if(params.get("japanese")!=null){
            japanese = Integer.parseInt((String) params.get("japanese"));
            if(japanese!=1){
                wrapper.eq("japanese",japanese);
            }
        }


        //处理分页参数和其他参数
        IPage<ItCaseEntity> page = this.page(
                new Query<ItCaseEntity>().getPage(params),
                wrapper.orderByDesc("created_time")
        );
        List<ItCaseEntity> records = page.getRecords();
        List<ItCaseDetailVo> jobDetailVos = new ArrayList<>();

        for (ItCaseEntity record : records) {
            ItCaseDetailVo jobDetailVo = changeIdToName(record);
            jobDetailVos.add(jobDetailVo);
        }

        //UserPage<T> extends Page<T>  自定义setRecords 方法
        Page<ItCaseDetailVo> jobDetailVoPage = new Page<>();
        //老的分页属性全部复制过来 (records T 泛型不同不能复制 )
        BeanUtils.copyProperties(page, jobDetailVoPage);

        jobDetailVoPage.setRecords(jobDetailVos);
        return new PageUtils(jobDetailVoPage);
    }

    @Override
    public ItCaseDetailVo getDetailById(Long id) {
        ItCaseEntity jobEntity = this.getById(id);
        ItCaseDetailVo jobDetailVo = changeIdToName(jobEntity);
        return jobDetailVo;
    }

    @Override
    public PageUtils queryHistoryPage(Map<String, Object> params) {
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

        IPage<ItCaseEntity> page = this.page(
                new Query<ItCaseEntity>().getPage(params),
                new QueryWrapper<ItCaseEntity>().in("id",list).last(builder.toString())
        );

        List<ItCaseEntity> records = page.getRecords();
        List<UserItCaseInfoVo> userItCaseInfoVos = new ArrayList<>();

        for (int i = 0; i < records.size() ; i++) {
            ItCaseDetailVo itCaseDetailVo = changeIdToName(records.get(i));
            UserItCaseInfoVo userItCaseInfoVo = new UserItCaseInfoVo();
            //2边id顺序是一样的
            BeanUtils.copyProperties(itCaseDetailVo, userItCaseInfoVo);
            BeanUtils.copyProperties(userCaseInfoEntities.get(i), userItCaseInfoVo);
            userItCaseInfoVos.add(userItCaseInfoVo);
        }

        //UserPage<T> extends Page<T>  自定义setRecords 方法
        Page<UserItCaseInfoVo> userItCaseInfoVoPage = new Page<>();
        //老的分页属性全部复制过来 (records T 泛型不同不能复制 )
        BeanUtils.copyProperties(page, userItCaseInfoVoPage);

        userItCaseInfoVoPage.setRecords(userItCaseInfoVos);
        return new PageUtils(userItCaseInfoVoPage);
    }

    //公共方法 所有select id 换成对应name
    //todo 线性查这么多表太慢了 改成异步多线程查询 并且利用spring cache 存到redis里面
    public ItCaseDetailVo changeIdToName(ItCaseEntity jobEntity) {

        List<LanguageEntity> languageEntities = languageService.list();
        List<OsEntity> osEntities = osService.list();
        List<JapaneseEntity> japaneseEntities = japaneseService.list();
        List<ExperienceEntity> experienceEntities = experienceService.list();
        List<DbEntity> dbEntities = dbService.list();
        List<AreaEntity> areaEntities = areaService.list();


        ItCaseDetailVo jobDetailVo = new ItCaseDetailVo();
        BeanUtils.copyProperties(jobEntity, jobDetailVo);
        ArrayList<String> temp = new ArrayList<>();

        String language = jobEntity.getLanguage();
        String[] split = language.split(",");
        List<String> ids = Arrays.asList(split);
        languageEntities.forEach(languageEntity -> {
            if (ids.contains(String.valueOf(languageEntity.getId()))) {
                temp.add(languageEntity.getName());
            }
        });
        jobDetailVo.setLanguage(String.join(",", temp));
        temp.clear();

        String os = jobEntity.getOs();
        List<String> osIds = Arrays.asList(os.split(","));
        osEntities.forEach(osEntity -> {
            if (osIds.contains(String.valueOf(osEntity.getId()))) {
                temp.add(osEntity.getName());
            }
        });
        jobDetailVo.setOs(String.join(",", temp));
        temp.clear();

        String db = jobEntity.getDb();
        List<String> dbIds = Arrays.asList(db.split(","));
        dbEntities.forEach(dbEntity -> {
            if (dbIds.contains(String.valueOf(dbEntity.getId()))) {
                temp.add(dbEntity.getName());
            }
        });
        jobDetailVo.setDb(String.join(",", temp));
        temp.clear();

        Integer area = jobEntity.getArea();
        areaEntities.forEach(areaEntity -> {
            if (area.equals(areaEntity.getId())) {
                jobDetailVo.setArea(areaEntity.getName());
            }
        });


        Integer japanese = jobEntity.getJapanese();
        japaneseEntities.forEach(japaneseEntity -> {
            if (japanese.equals(japaneseEntity.getId())) {
                jobDetailVo.setJapanese(japaneseEntity.getName());
            }
        });

        Integer experience = jobEntity.getExperience();
        experienceEntities.forEach(experienceEntity -> {
            if (experience.equals(experienceEntity.getId())) {
                jobDetailVo.setExperience(experienceEntity.getName());
            }
        });

        return jobDetailVo;
    }

}