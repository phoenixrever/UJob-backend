package io.renren.modules.front.controller; /**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */


import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.common.utils.ShiroUtils;
import io.renren.modules.front.entity.GeneralUserEntity;
import io.renren.modules.front.service.FrontLoginService;
import io.renren.modules.front.service.GeneralUserService;
import io.renren.modules.sys.form.SysLoginForm;
import io.renren.modules.sys.service.SysCaptchaService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 登录相关
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/front/auth")
public class FrontLoginController {
    @Autowired
    private GeneralUserService generalUserService;
    @Autowired
    private FrontLoginService frontLoginService;
    @Autowired
    private SysCaptchaService sysCaptchaService;

    /**
     * 前端验证码 暂时不需要
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //获取图片验证码 getCaptcha方法 会在captcha表新建一个5分钟过期的一条验证码数据
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);

        //图片数据流写入response 返回
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 一般用户登录
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody SysLoginForm form)throws IOException {
        //boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
        //if(!captcha){
        //    return R.error("验证码不正确");
        //}

        //用户信息
        GeneralUserEntity user = generalUserService.query().eq("username", form.getUsername()).one();

        //账号不存在、密码错误
        if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return R.error("账号或密码不正确");
        }

        //账号锁定
        if(user.getStatus() == 0){
            return R.error("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到redis
        // 此时前端已经从后端拿到生成的token了 用次token 加到请求头中 跳转到主页 完成登录
        // 每次访问带着此token到后端校验
        String token = frontLoginService.createJWtToken(user.getUsername(), Constant.GENERAL_USER);
        return R.ok().put("token", token);
    }



    /**
     * 退出
     */
    @PostMapping("/logout")
    public R logout() {

        return R.ok();
    }

}
