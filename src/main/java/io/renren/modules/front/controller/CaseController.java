package io.renren.modules.front.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.modules.front.entity.GeneralUserEntity;
import io.renren.modules.front.entity.UserCaseInfoEntity;
import io.renren.modules.front.service.UserCaseInfoService;
import io.renren.modules.front.vo.CaseDetailVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.front.entity.CaseEntity;
import io.renren.modules.front.service.CaseService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 案件列表
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-16 23:27:38
 */
@RestController
@RequestMapping("front/case")
public class CaseController {
    @Autowired
    private CaseService caseService;

    @Autowired
    private UserCaseInfoService userCaseInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("front:case:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = caseService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 把所有多个 id 表示的 select 换成 name 列表
     */
    @RequestMapping("/detailList")
    //@RequiresPermissions("front:language:list")
    public R detailList(@RequestParam Map<String, Object> params){
        PageUtils page = caseService.queryDetailPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     * 未登录用这个接口查询案件详情
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("front:case:info")
    public R info(@PathVariable("id") Long id){
        CaseDetailVo caseDetailVo = caseService.getDetailById(id);
        return R.ok().put("case", caseDetailVo);
    }



    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:case:save")
    public R save(@RequestBody CaseEntity caseEntity){
		caseService.save(caseEntity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:case:update")
    public R update(@RequestBody CaseEntity caseEntity){
		caseService.updateById(caseEntity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:case:delete")
    public R delete(@RequestBody Integer[] ids){
		caseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
