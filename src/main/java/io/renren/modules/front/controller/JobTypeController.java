package io.renren.modules.front.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.front.entity.JobTypeEntity;
import io.renren.modules.front.service.JobTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 所属分类
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
@RestController
@RequestMapping("front/jobType")
public class JobTypeController {
    @Autowired
    private JobTypeService jobTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("front:menu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = jobTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("front:menu:info")
    public R info(@PathVariable("id") Integer id){
		JobTypeEntity menu = jobTypeService.getById(id);

        return R.ok().put("jobTypes", menu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:menu:save")
    public R save(@RequestBody JobTypeEntity menu){
		jobTypeService.save(menu);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:menu:update")
    public R update(@RequestBody JobTypeEntity menu){
		jobTypeService.updateById(menu);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:menu:delete")
    public R delete(@RequestBody Integer[] ids){
		jobTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
