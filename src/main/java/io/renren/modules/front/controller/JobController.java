package io.renren.modules.front.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.modules.front.vo.JobDetailVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.front.entity.JobEntity;
import io.renren.modules.front.service.JobService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 案件列表
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
@RestController
@RequestMapping("front/job")
public class JobController {
    @Autowired
    private JobService jobService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("front:job:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = jobService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 把所有多个 id 表示的 select 换成 name 列表
     */
    @RequestMapping("/detailList")
    //@RequiresPermissions("front:language:list")
    public R detailList(@RequestParam Map<String, Object> params){
        PageUtils page = jobService.queryDetailPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("front:job:info")
    public R info(@PathVariable("id") Integer id){
        JobDetailVo jobDetailVo = jobService.getDetailById(id);

        return R.ok().put("jobDetail", jobDetailVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("front:job:save")
    public R save(@RequestBody JobEntity job){
        System.out.println(job);
        jobService.save(job);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:job:update")
    public R update(@RequestBody JobEntity job){
		jobService.updateById(job);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:job:delete")
    public R delete(@RequestBody Integer[] ids){
		jobService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
