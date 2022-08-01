package io.renren.modules.front.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.modules.front.vo.SelectionsVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.front.entity.CityEntity;
import io.renren.modules.front.service.CityService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 工作地点
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
@RestController
@RequestMapping("front/city")
public class CityController {
    @Autowired
    private CityService cityService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("front:area:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cityService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取所有selections 数据 citys japanese jobTypes
     */
    @GetMapping("/selections")
    public R getSelections(){
        SelectionsVo selectionsVo = cityService.getCacheSelections();
        return R.ok().put("selections", selectionsVo);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("front:area:info")
    public R info(@PathVariable("id") Integer id){
		CityEntity area = cityService.getById(id);

        return R.ok().put("area", area);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:area:save")
    public R save(@RequestBody CityEntity area){
		cityService.save(area);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:area:update")
    public R update(@RequestBody CityEntity area){
		cityService.updateById(area);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:area:delete")
    public R delete(@RequestBody Integer[] ids){
		cityService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
