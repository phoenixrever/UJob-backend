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

import io.renren.modules.front.entity.OsEntity;
import io.renren.modules.front.service.OsService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 操作系统
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
@RestController
@RequestMapping("front/os")
public class OsController {
    @Autowired
    private OsService osService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("front:os:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = osService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("front:os:info")
    public R info(@PathVariable("id") Integer id){
		OsEntity os = osService.getById(id);

        return R.ok().put("os", os);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:os:save")
    public R save(@RequestBody OsEntity os){
		osService.save(os);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:os:update")
    public R update(@RequestBody OsEntity os){
		osService.updateById(os);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:os:delete")
    public R delete(@RequestBody Integer[] ids){
		osService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
