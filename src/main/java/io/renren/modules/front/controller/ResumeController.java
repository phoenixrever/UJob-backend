package io.renren.modules.front.controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.util.PoitlIOUtils;
import io.renren.modules.front.entity.CertificateEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.front.entity.ResumeEntity;
import io.renren.modules.front.service.ResumeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


/**
 * 简历表，备注简历信息可能和个人信息填的不一样。
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-23 17:11:00
 */
@RestController
@RequestMapping("front/resume")
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("front:resume:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = resumeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("front:resume:info")
    public R info(@PathVariable("userId") Long userId){
		ResumeEntity resume = resumeService.getWithCertificateById(userId);

        return R.ok().put("resume", resume);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("front:resume:save")
    public R save(@RequestBody ResumeEntity resume){
		resumeService.saveWithCertificate(resume);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:resume:update")
    public R update(@RequestBody ResumeEntity resume){
		resumeService.updateById(resume);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:resume:delete")
    public R delete(@RequestBody Long[] ids){
		resumeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * word 文档
     * 这里直接返回文件流 注意 返回类型void
     */
    @GetMapping("/word/{userId}")
    //@RequiresPermissions("front:resume:word")
    public void word(@PathVariable("userId") Long userId, HttpServletResponse response) throws IOException {
        ResumeEntity resume = resumeService.getWithCertificateById(userId);
        XWPFTemplate template = makeDoc(resume);
        response.setContentType("application/octet-stream");
        //原因:跨域后系统为了安全去掉自定义头
        // 解决自定义头被拦截的问题

        // 解决自定义头被拦截的问题
        //response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipFileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers","Content-Disposition");

        //自定义name没用的必须要Content-disposition 好像
        response.setHeader("Content-disposition","attachment;filename="+resume.getName()+".docx");

        // HttpServletResponse response
        OutputStream out = response.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(out);
        template.write(bos);
        bos.flush();
        out.flush();
        PoitlIOUtils.closeQuietlyMulti(template, bos, out);
    }

    private XWPFTemplate makeDoc(ResumeEntity resume) throws FileNotFoundException {
        //get resource path
        File file = ResourceUtils.getFile("classpath:templates/resume.docx");

        System.out.println(resume);
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", resume.getName());
        map.put("image", Pictures.ofUrl("https://wx2.sinaimg.cn/mw690/006W2MEEly1gzairj23j8j30qn12h45o.jpg")
                .size(180, 240).create());

        map.put("y", LocalDate.now().getYear());
        map.put("m", LocalDate.now().getMonth().getValue());
        map.put("d", LocalDate.now().getDayOfMonth());
        map.put("hiragana", resume.getHiraganaName());
        map.put("year", resume.getBirthday().split("-")[0]);
        map.put("month", resume.getBirthday().split("-")[1]);
        map.put("day", resume.getBirthday().split("-")[2]);
        map.put("age", LocalDate.now().getYear() - Integer.parseInt(resume.getBirthday().split("-")[0]));
        map.put("hiraganaAddress", resume.getCity()+resume.getArea()+resume.getAreaDetail());
        map.put("address", resume.getCity()+resume.getArea()+resume.getAreaDetail());
        map.put("p1", resume.getPhone().substring(0, 3));//080 3302 8999
        map.put("p2", resume.getPhone().substring(3, 7));//3302 8999
        map.put("p3", resume.getPhone().substring(7));//8999
        map.put("email", resume.getEmail());
        map.put("sYear", resume.getAdmissionDate().split("-")[0]);
        map.put("sMonth", resume.getAdmissionDate().split("-")[1]);
        map.put("schoolName1", resume.getSchoolName()+resume.getMajor()+" "+"入学");
        map.put("eYear", resume.getGraduationDate().split("-")[0]);
        map.put("eMonth", resume.getGraduationDate().split("-")[1]);
        map.put("schoolName2", resume.getSchoolName()+resume.getMajor()+" "+"卒業");

        map.put("cYear", resume.getHiredDate().split("-")[0]);//2010-02
        map.put("cMonth", resume.getHiredDate().split("-")[1]);
        map.put("companyName1", resume.getCompanyName()+" "+"入職");

        map.put("cYear1", resume.getLeaveDate().split("-")[0]);//2010-02
        map.put("cMonth1", resume.getLeaveDate().split("-")[1]);
        map.put("companyName2", resume.getCompanyName()+" "+"退職");

        //資　格・免　許
        List<CertificateEntity> certificateEntities = resume.getCertificateEntities();
        for (int i = 0; i <certificateEntities.size(); i++) {
            map.put("cy"+(i+1), certificateEntities.get(i).getDate().split("-")[0]);
            map.put("cm"+(i+1), certificateEntities.get(i).getDate().split("-")[1]);
            map.put("c"+(i+1), certificateEntities.get(i).getName());
        }

        map.put("hobby", resume.getHobby());
        map.put("skill", resume.getSkill());
        map.put("selfIntro", resume.getSelfIntro());

        XWPFTemplate template = XWPFTemplate.compile(file).render(map);
        return template;
    }
}
