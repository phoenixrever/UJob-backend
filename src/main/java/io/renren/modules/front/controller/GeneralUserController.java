package io.renren.modules.front.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.front.entity.GeneralUserEntity;
import io.renren.modules.front.service.GeneralUserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 一般用户
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-18 14:22:59
 */
@RestController
@RequestMapping("/generaluser")
public class GeneralUserController {
    @Autowired
    private GeneralUserService generalUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("front:generaluser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = generalUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("front:generaluser:info")
    public R info(@PathVariable("userId") Long userId){
		GeneralUserEntity generalUser = generalUserService.getById(userId);

        return R.ok().put("generalUser", generalUser);
    }

    /**
     * 获取当前登录user
     */
    @GetMapping("/user")
    public R info() {
        GeneralUserEntity generalUserEntity = ShiroUtils.getGeneralUserEntity();
        generalUserEntity.setPassword(null);
        return R.ok().put("user", generalUserEntity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("front:generaluser:save")
    public R save(@RequestBody GeneralUserEntity generalUser){
		generalUserService.save(generalUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:generaluser:update")
    public R update(@RequestBody GeneralUserEntity generalUser){
		generalUserService.updateById(generalUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:generaluser:delete")
    public R delete(@RequestBody Long[] userIds){
		generalUserService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
