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

import io.renren.modules.front.entity.CertificateEntity;
import io.renren.modules.front.service.CertificateService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 资格证书表
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-23 17:11:00
 */
@RestController
@RequestMapping("front/certificate")
public class CertificateController {
    @Autowired
    private CertificateService certificateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("front:certificate:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = certificateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("front:certificate:info")
    public R info(@PathVariable("id") String id){
		CertificateEntity certificate = certificateService.getById(id);

        return R.ok().put("certificate", certificate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:certificate:save")
    public R save(@RequestBody CertificateEntity certificate){
		certificateService.save(certificate);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:certificate:update")
    public R update(@RequestBody CertificateEntity certificate){
		certificateService.updateById(certificate);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:certificate:delete")
    public R delete(@RequestBody String[] ids){
		certificateService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
