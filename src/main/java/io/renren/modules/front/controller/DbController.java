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

import io.renren.modules.front.entity.DbEntity;
import io.renren.modules.front.service.DbService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 使用数据库
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
@RestController
@RequestMapping("front/db")
public class DbController {
    @Autowired
    private DbService dbService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("front:db:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dbService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("front:db:info")
    public R info(@PathVariable("id") Integer id){
		DbEntity db = dbService.getById(id);

        return R.ok().put("db", db);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:db:save")
    public R save(@RequestBody DbEntity db){
		dbService.save(db);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:db:update")
    public R update(@RequestBody DbEntity db){
		dbService.updateById(db);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:db:delete")
    public R delete(@RequestBody Integer[] ids){
		dbService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
