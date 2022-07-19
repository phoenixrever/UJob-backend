package io.renren.modules.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.front.dao.CaseDao;
import io.renren.modules.front.entity.*;
import io.renren.modules.front.service.*;
import io.renren.modules.front.vo.CaseDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


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
    public CaseDetailVo getDetailById(Integer id) {
        CaseEntity caseEntity = this.getById(id);
        CaseDetailVo caseDetailVo = changeIdToName(caseEntity);
        return caseDetailVo;
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