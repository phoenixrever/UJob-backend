package io.renren.modules.front.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.modules.front.service.UserCaseInfoService;
import io.renren.modules.front.vo.JobDetailVo;
import io.renren.modules.front.vo.SelectionsVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.front.entity.JobEntity;
import io.renren.modules.front.service.JobService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 案件列表
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-16 23:27:38
 */
@RestController
@RequestMapping("front/job")
public class JobController {
    @Autowired
    private JobService jobService;

    @Autowired
    private UserCaseInfoService userCaseInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("front:case:list")
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
     * 未登录用这个接口查询案件详情
     *
     * 登录接口放到/front/usercaseinfo/info/{id}
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("front:case:info")
    public R info(@PathVariable("id") Long id){
        JobDetailVo jobDetailVo = jobService.getDetailById(id);
        return R.ok().put("case", jobDetailVo);
    }



    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:case:save")
    public R save(@RequestBody JobEntity jobEntity){
		jobService.save(jobEntity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:case:update")
    public R update(@RequestBody JobEntity jobEntity){
		jobService.updateById(jobEntity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:case:delete")
    public R delete(@RequestBody Integer[] ids){
		jobService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
