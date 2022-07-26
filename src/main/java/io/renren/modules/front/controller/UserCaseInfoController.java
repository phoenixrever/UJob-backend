package io.renren.modules.front.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.modules.front.entity.GeneralUserEntity;
import io.renren.modules.front.service.CaseService;
import io.renren.modules.front.vo.CaseDetailVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private CaseService caseService;


    /**
     * 查询案件信息
     * todo 问题来了 不登录是没用user的 所以不能记录历史记录
     * 暂时的解决方法 我写2个接口 前端看登录情况请求不同的接口
     */
    @RequestMapping("/caseInfo/{id}")
    //@RequiresPermissions("front:case:info")
    public R infoLogin(@PathVariable("id") Long id){
        CaseDetailVo caseDetailVo = caseService.getDetailById(id);

        //如果已经登录 记录到历史记录里面 每次查询都会记录一次
        //todo 如果未登录用 需不需要保存记录 登陆后合并 待决定
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(principal != null){
            //先查看对应关系是否存在
            UserCaseInfoEntity caseInfoEntity = userCaseInfoService.query().eq("case_id", id).one();
            if (caseInfoEntity == null) {
                GeneralUserEntity generalUser = (GeneralUserEntity) principal;
                Long userId = generalUser.getUserId();
                UserCaseInfoEntity userCaseInfoEntity = new UserCaseInfoEntity();
                userCaseInfoEntity.setCaseId(id);
                userCaseInfoEntity.setUserId(userId);
                userCaseInfoEntity.setVisited(1);
                userCaseInfoService.save(userCaseInfoEntity);
            }else {
                caseInfoEntity.setVisited(caseInfoEntity.getVisited() + 1);
            }
        }

        return R.ok().put("case", caseDetailVo);
    }

    /**
     * 历史记录
     */
    @RequestMapping("/detailList")
    //@RequiresPermissions("front:usercaseinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = caseService.queryHistoryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 用户浏览历史 写这里 shiro path 号设置些
     */
    @RequestMapping("/case/history")
    @RequiresPermissions("front:usercaseinfo:list")
    public R listHistory(@RequestParam Map<String, Object> params){
        PageUtils page = userCaseInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("front:usercaseinfo:info")
    public R info(@PathVariable("id") Long id){
		UserCaseInfoEntity userCaseInfo = userCaseInfoService.getById(id);

        return R.ok().put("userCaseInfo", userCaseInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:usercaseinfo:save")
    public R save(@RequestBody UserCaseInfoEntity userCaseInfo){
		userCaseInfoService.save(userCaseInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:usercaseinfo:update")
    public R update(@RequestBody UserCaseInfoEntity userCaseInfo){
		userCaseInfoService.updateById(userCaseInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:usercaseinfo:delete")
    public R delete(@RequestBody Long[] ids){
		userCaseInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
