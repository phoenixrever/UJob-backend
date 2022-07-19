package io.renren.modules.front.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.front.entity.*;
import io.renren.modules.front.service.*;
import io.renren.modules.front.vo.ItCaseDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    public ItCaseDetailVo getDetailById(Integer id) {
        ItCaseEntity jobEntity = this.getById(id);
        ItCaseDetailVo jobDetailVo = changeIdToName(jobEntity);
        return jobDetailVo;
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