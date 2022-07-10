package io.renren.modules.front.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.front.entity.ExperienceEntity;
import io.renren.modules.front.service.ExperienceService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 工作经验
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
@RestController
@RequestMapping("front/experience")
public class ExperienceController {
    @Autowired
    private ExperienceService experienceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("front:experience:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = experienceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("front:experience:info")
    public R info(@PathVariable("id") Integer id){
		ExperienceEntity experience = experienceService.getById(id);

        return R.ok().put("experience", experience);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:experience:save")
    public R save(@RequestBody ExperienceEntity experience){
		experienceService.save(experience);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:experience:update")
    public R update(@RequestBody ExperienceEntity experience){
		experienceService.updateById(experience);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:experience:delete")
    public R delete(@RequestBody Integer[] ids){
		experienceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
