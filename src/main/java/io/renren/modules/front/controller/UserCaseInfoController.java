package io.renren.modules.front.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.renren.common.utils.ShiroUtils;
import io.renren.modules.front.entity.GeneralUserEntity;
import io.renren.modules.front.service.JobService;
import io.renren.modules.front.service.ItCaseService;
import io.renren.modules.front.vo.DeliveryVo;
import io.renren.modules.front.vo.JobDetailVo;
import io.renren.modules.front.vo.ItCaseDetailVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.front.entity.UserCaseInfoEntity;
import io.renren.modules.front.service.UserCaseInfoService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;


/**
 * 用户案件信息表必须要登录
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-25 20:44:20
 */
@RestController
@RequestMapping("front/usercaseinfo")
public class UserCaseInfoController {
    @Autowired
    private UserCaseInfoService userCaseInfoService;



    /**
     * 查询案件信息
     * todo 问题来了 不登录是没用user的 所以不能记录历史记录
     * 暂时的解决方法 我写2个接口 前端看登录情况请求不同的接口
     * <p>
     * 这边不用事务 历史记录没记录到也没大关系
     */
    @RequestMapping("/caseInfo/{id}")
    //@RequiresPermissions("front:case:info")
    public R infoLogin(@PathVariable("id") Long id) {
        JobDetailVo jobDetailVo = userCaseInfoService.getJobDetailVo(id);
        return R.ok().put("case", jobDetailVo);
    }


    /**
     * 查询iT案件信息
     */
    @RequestMapping("/itCaseInfo/{id}")
    //@RequiresPermissions("front:case:info")
    public R itInfoLogin(@PathVariable("id") Long id) {
        ItCaseDetailVo itCaseDetailVo = userCaseInfoService.getItCaseDetailVo(id);

        return R.ok().put("itCase", itCaseDetailVo);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("front:usercaseinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = userCaseInfoService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 用户浏览历史
     */
    @RequestMapping("/case/{search}")
    //@RequiresPermissions("front:usercaseinfo:list")
    public R listHistory(@RequestParam Map<String, Object> params, @PathVariable("search") String search) {
        //int caseType = 0;
        //PageUtils page =null;
        //if( params.get("caseType")  != null){
        //    caseType = Integer.parseInt((String) params.get("caseType"));
        //}
        //switch (caseType){
        //    case 0:
        //        page = jobService.queryHistoryPage(params);
        //        break;
        //    case 1:
        //        page = itCaseService.queryHistoryPage(params);
        //        break;
        //}

        PageUtils page = userCaseInfoService.querySearchPage(params, search);
        return R.ok().put("page", page);
    }

    /**
     * favorite  case_type 和 case_id 联合确定唯一
     */
    @PostMapping("/setFavorite")
    public R setFavorite(@RequestBody UserCaseInfoEntity userCaseInfo) {
        UserCaseInfoEntity userCaseInfoEntity = userCaseInfoService.query().eq("case_id", userCaseInfo.getCaseId()).eq("case_type", userCaseInfo.getCaseType()).one();
        if (userCaseInfoEntity != null) {
            //不要用前段传来的其他数据 保证安全性能
            userCaseInfoEntity.setFavorite(userCaseInfo.getFavorite());
            userCaseInfoEntity.setFavoriteTime(new Date());
            userCaseInfoService.updateById(userCaseInfoEntity);
        }
        return R.ok();
    }

    /**
     * setDelivery
     * 记得校验caseType的时候只允许传0或1
     */
    @PostMapping("/setDelivery")
    public R setDelivery(@RequestBody DeliveryVo deliveryVo) {
        userCaseInfoService.setDeliveryWithIds(deliveryVo);
        return R.ok();
    }


    /**
     * favorite
     */
    @GetMapping("/favorite/{caseType}/{caseId}")
    public R favorite(@PathVariable Integer caseType, @PathVariable Long caseId) {
        Integer favorite = 0;
        UserCaseInfoEntity userCaseInfoEntity = userCaseInfoService.query().eq("case_id", caseId).eq("case_type", caseType).one();
        if (userCaseInfoEntity != null) {
            favorite = userCaseInfoEntity.getFavorite();
        }
        return R.ok().put("favorite", favorite);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("front:usercaseinfo:info")
    public R info(@PathVariable("id") Long id) {
        UserCaseInfoEntity userCaseInfo = userCaseInfoService.getById(id);

        return R.ok().put("userCaseInfo", userCaseInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:usercaseinfo:save")
    public R save(@RequestBody UserCaseInfoEntity userCaseInfo) {
        userCaseInfoService.save(userCaseInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:usercaseinfo:update")
    public R update(@RequestBody UserCaseInfoEntity userCaseInfo) {
        userCaseInfoService.updateById(userCaseInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:usercaseinfo:delete")
    public R delete(@RequestBody Long[] ids) {
        userCaseInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
