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

import io.renren.modules.front.entity.LanguageEntity;
import io.renren.modules.front.service.LanguageService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 开发语言
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
@RestController
@RequestMapping("front/language")
public class LanguageController {
    @Autowired
    private LanguageService languageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("front:language:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = languageService.queryPage(params);

        return R.ok().put("page", page);
    }




    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:language:save")
    public R save(@RequestBody LanguageEntity language){
		languageService.save(language);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:language:update")
    public R update(@RequestBody LanguageEntity language){
		languageService.updateById(language);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:language:delete")
    public R delete(@RequestBody Integer[] ids){
		languageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
