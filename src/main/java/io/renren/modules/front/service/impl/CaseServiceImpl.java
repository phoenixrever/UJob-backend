package io.renren.modules.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.front.dao.CaseDao;
import io.renren.modules.front.entity.*;
import io.renren.modules.front.service.*;
import io.renren.modules.front.vo.CaseDetailVo;
import io.renren.modules.front.vo.UserCaseInfoVo;
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
public class CaseServiceImpl extends ServiceImpl<CaseDao, CaseEntity> implements CaseService {
    @Autowired
    private FeatureService featureService;

    @Autowired
    private JapaneseService japaneseService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private AreaService areaService;
    @Autowired
    private MenuService menuService;

    @Autowired
    private UserCaseInfoService userCaseInfoService;

    private final static int CASE_TYPE = 0;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CaseEntity> page = this.page(
                new Query<CaseEntity>().getPage(params),
                new QueryWrapper<CaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryDetailPage(Map<String, Object> params) {

        QueryWrapper<CaseEntity> wrapper = new QueryWrapper<>();

        int menuId = 1;
        int area = 1;
        int japanese = 1;


        if(params.get("menu")!=null){
            menuId = Integer.parseInt((String) params.get("menu"));
            //1 是不限 所有都查询 注意里面不包含本身选项就是 无限 的 1
            if(menuId!=1){
                wrapper.eq("menu",menuId);
            }

        }
        if(params.get("area")!=null){
            area = Integer.parseInt((String) params.get("area"));
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
        IPage<CaseEntity> page = this.page(
                new Query<CaseEntity>().getPage(params),
                wrapper.orderByDesc("created_time")
        );
        List<CaseEntity> records = page.getRecords();
        List<CaseDetailVo> caseDetailVos = new ArrayList<>();

        for (CaseEntity record : records) {
            CaseDetailVo caseDetailVo = changeIdToName(record);
            caseDetailVos.add(caseDetailVo);
        }

        //UserPage<T> extends Page<T>  自定义setRecords 方法
        Page<CaseDetailVo> caseDetailVoPage = new Page<>();
        //老的分页属性全部复制过来 (records T 泛型不同不能复制 )
        BeanUtils.copyProperties(page, caseDetailVoPage);

        caseDetailVoPage.setRecords(caseDetailVos);
        return new PageUtils(caseDetailVoPage);
    }

    @Override
    public CaseDetailVo getDetailById(Long id) {
        CaseEntity caseEntity = this.getById(id);
        CaseDetailVo caseDetailVo = changeIdToName(caseEntity);
        return caseDetailVo;
    }

    //这边要把所有都查出来 我的投递也要用到
    //为了用 in 不在for循环里面查多做了一点
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

        IPage<CaseEntity> page = this.page(
                new Query<CaseEntity>().getPage(params),
                new QueryWrapper<CaseEntity>().in("id",list).last(builder.toString())
        );

        List<CaseEntity> records = page.getRecords();
        List<UserCaseInfoVo> userCaseInfoVos = new ArrayList<>();

        for (int i = 0; i < records.size() ; i++) {
            CaseDetailVo caseDetailVo = changeIdToName(records.get(i));
            UserCaseInfoVo userCaseInfoVo = new UserCaseInfoVo();
            //2边id顺序是一样的
            BeanUtils.copyProperties(caseDetailVo, userCaseInfoVo);
            BeanUtils.copyProperties(userCaseInfoEntities.get(i), userCaseInfoVo);
            userCaseInfoVos.add(userCaseInfoVo);
        }

        //UserPage<T> extends Page<T>  自定义setRecords 方法
        Page<UserCaseInfoVo> caseDetailVoPage = new Page<>();
        //老的分页属性全部复制过来 (records T 泛型不同不能复制 )
        BeanUtils.copyProperties(page, caseDetailVoPage);

        caseDetailVoPage.setRecords(userCaseInfoVos);
        return new PageUtils(caseDetailVoPage);
    }

    private CaseDetailVo changeIdToName(CaseEntity caseEntity) {
        List<JapaneseEntity> japaneseEntities = japaneseService.list();
        List<ExperienceEntity> experienceEntities = experienceService.list();
        List<AreaEntity> areaEntities = areaService.list();
        List<MenuEntity> menuEntities = menuService.list();
        List<FeatureEntity> featureEntities = featureService.list();

        CaseDetailVo caseDetailVo = new CaseDetailVo();
        BeanUtils.copyProperties(caseEntity, caseDetailVo);
        ArrayList<String> temp = new ArrayList<>();

        String feature = caseEntity.getFeature();
        String[] split = feature.split(",");
        List<String> ids = Arrays.asList(split);
        featureEntities.forEach(featureEntity -> {
            if (ids.contains(String.valueOf(featureEntity.getId()))) {
                temp.add(featureEntity.getName());
            }
        });
        caseDetailVo.setFeature(String.join(",", temp));
        temp.clear();


        Integer menu = caseEntity.getMenu();
        menuEntities.forEach(menuEntity -> {
            if (menuEntity.getId().equals(menu)) {
                caseDetailVo.setMenu(menuEntity.getName());
            }
        });

        Integer area = caseEntity.getArea();
        areaEntities.forEach(areaEntity -> {
            if (area.equals(areaEntity.getId())) {
                caseDetailVo.setArea(areaEntity.getName());
            }
        });


        Integer japanese = caseEntity.getJapanese();
        japaneseEntities.forEach(japaneseEntity -> {
            if (japanese.equals(japaneseEntity.getId())) {
                caseDetailVo.setJapanese(japaneseEntity.getName());
            }
        });

        Integer experience = caseEntity.getExperience();
        experienceEntities.forEach(experienceEntity -> {
            if (experience.equals(experienceEntity.getId())) {
                caseDetailVo.setExperience(experienceEntity.getName());
            }
        });
        return caseDetailVo;
    }

}