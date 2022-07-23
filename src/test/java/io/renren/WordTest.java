package io.renren;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Pictures;
import io.renren.modules.front.entity.CertificateEntity;
import io.renren.modules.front.entity.ResumeEntity;
import io.renren.modules.front.service.ResumeService;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class WordTest {
    @Autowired
    private ResumeService resumeService;

    @Test
    public void test() throws IOException {
        //get resource path
        File file = ResourceUtils.getFile("classpath:templates/resume.docx");

        ResumeEntity resume = resumeService.getWithCertificateById(1L);
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

        //template.writeAndClose(Files.newOutputStream(Paths.get(output.getPath()+"/output.docx")));
        template.writeAndClose(Files.newOutputStream(Paths.get("D:\\code\\renren-fast\\src\\main\\resources\\templates\\output.docx")));
    }
}
