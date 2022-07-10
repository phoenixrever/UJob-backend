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

import io.renren.modules.front.entity.AreaEntity;
import io.renren.modules.front.service.AreaService;
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
@RequestMapping("front/area")
public class AreaController {
    @Autowired
    private AreaService areaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("front:area:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = areaService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("front:area:info")
    public R info(@PathVariable("id") Integer id){
		AreaEntity area = areaService.getById(id);

        return R.ok().put("area", area);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:area:save")
    public R save(@RequestBody AreaEntity area){
		areaService.save(area);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:area:update")
    public R update(@RequestBody AreaEntity area){
		areaService.updateById(area);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:area:delete")
    public R delete(@RequestBody Integer[] ids){
		areaService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
