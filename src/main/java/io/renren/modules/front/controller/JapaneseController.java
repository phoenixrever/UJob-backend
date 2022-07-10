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

import io.renren.modules.front.entity.JapaneseEntity;
import io.renren.modules.front.service.JapaneseService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 日语程度
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
@RestController
@RequestMapping("front/japanese")
public class JapaneseController {
    @Autowired
    private JapaneseService japaneseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("front:japanese:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = japaneseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("front:japanese:info")
    public R info(@PathVariable("id") Integer id){
		JapaneseEntity japanese = japaneseService.getById(id);

        return R.ok().put("japanese", japanese);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:japanese:save")
    public R save(@RequestBody JapaneseEntity japanese){
		japaneseService.save(japanese);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:japanese:update")
    public R update(@RequestBody JapaneseEntity japanese){
		japaneseService.updateById(japanese);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:japanese:delete")
    public R delete(@RequestBody Integer[] ids){
		japaneseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
