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

import io.renren.modules.front.entity.FeatureEntity;
import io.renren.modules.front.service.FeatureService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-16 23:27:38
 */
@RestController
@RequestMapping("front/feature")
public class FeatureController {
    @Autowired
    private FeatureService featureService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("front:feature:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = featureService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("front:feature:info")
    public R info(@PathVariable("id") Integer id){
		FeatureEntity feature = featureService.getById(id);

        return R.ok().put("feature", feature);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:feature:save")
    public R save(@RequestBody FeatureEntity feature){
		featureService.save(feature);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:feature:update")
    public R update(@RequestBody FeatureEntity feature){
		featureService.updateById(feature);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:feature:delete")
    public R delete(@RequestBody Integer[] ids){
		featureService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
