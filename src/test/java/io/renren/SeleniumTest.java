package io.renren;

import io.github.bonigarcia.wdm.WebDriverManager;

import io.renren.modules.front.entity.JobEntity;
import io.renren.modules.front.service.JobService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@SpringBootTest
public class SeleniumTest {
    @Autowired
    private JobService jobService;
    private WebDriver driver;

    @BeforeAll
    public static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver(new ChromeOptions().setHeadless(true));
        driver.manage().window().maximize();//最大化窗口


        //driver = new ChromeDriver();

    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    public void test() {

        for (int n = 1; n <= 4; n++) {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            /**
             * jobtype=0 不限
             * jobtype=5 一般事务
             * jobtype=4 销售
             * jobtype=28 餐饮
             * jobtype=10 贩卖
             * jobtype=30 服务接待
             * jobtype=7 IT相关
             * jobtype=37 建筑装修
             * jobtype=22 丽人养生
             * jobtype=38 工厂物流
             * jobtype=26 司机
             *
             *
             * 1,不限
             * 2,一般事务
             * 3,销售
             * 4,餐饮
             * 5,贩卖
             * 6,服务接待
             * 7,IT相关
             * 8,建筑装修
             * 9,丽人养生
             * 10,工厂物流
             * 11,司机
             */

            //别忘记改  jobEntity.setJobType(2); 对应的id
            int jobType =26;


            driver.get("http://ijob.jp/index.php?pN=" + n + "&ctl=Index&act=joblist&type=3&c_hangye=&jobtype="+jobType+"&city_id=&rlang=&kword=");
            List<WebElement> elements = driver.findElements(By.className("bdhuisebottom2"));
            List<JobEntity> jobEntities = new ArrayList<>();
            //用来存详情页的url 不然for循环会丢失元素 暂时没找到解决方法 先这么做
            List<String> hrefs = new ArrayList<>();
            System.out.println("=================第" + n + "页====================size:" + elements.size());

            for (WebElement element : elements) {
                JobEntity jobEntity = new JobEntity();
                jobEntity.setJobType(11);

                String name = element.findElement(By.xpath("./a[2]/div[1]/div[1]")).getText();
                jobEntity.setJobName(name);
                String salary = element.findElement(By.xpath("./a[2]/div[1]/div[2]")).getText();
                jobEntity.setSalary(salary);
                List<WebElement> japanneses = element.findElements(By.xpath("./a[2]/div[2]/div"));
                for (int i = 0; i < japanneses.size(); i++) {
                    if (i == japanneses.size() - 1) {
                        jobEntity.setWorkType(japanneses.get(i).getText());//正社员
                    } else {
                        String japanese = japanneses.get(i).getText();
                        switch (japanese) {
                            case "挨拶程度":
                                jobEntity.setJapanese(2);
                                break;
                            case "日常会話":
                                jobEntity.setJapanese(3);
                                break;
                            case "ビジネス":
                                jobEntity.setJapanese(4);
                                break;
                            case "无经验可":
                                jobEntity.setExperience(1);
                                break;
                            case "中国语对应":
                                jobEntity.setChinese(1);
                                break;
                            default:
                                jobEntity.setJapanese(1);
                                break;
                        }
                    }
                }

                String city = element.findElement(By.xpath("./a[2]/div[3]/div[1]/div")).getText();
                switch (city) {
                    case "不限":
                        jobEntity.setCity(1);
                        break;
                    case "東京都":
                        jobEntity.setCity(2);
                        break;
                    case "神奈川県":
                        jobEntity.setCity(3);
                        break;
                    case "埼玉県":
                        jobEntity.setCity(4);
                        break;
                    case "千葉県":
                        jobEntity.setCity(5);
                        break;
                    case "大阪府":
                        jobEntity.setCity(6);
                        break;
                    case "在宅":
                        jobEntity.setCity(7);
                        break;
                    case "其他地区":
                        jobEntity.setCity(8);
                        break;
                }
                String distance = "";
                List<WebElement> elements1 = element.findElements(By.xpath("./a[2]/div[3]/div[2]/div"));

                if (elements1.size()>0) {
                    distance = elements1.get(0).getText();
                }
                jobEntity.setDistance(distance);
                String time = "";
                List<WebElement> timeElements = element.findElements(By.xpath("./a[2]/div[3]/div[3]"));
                if (timeElements.size()>0) {
                    time = timeElements.get(0).getText();
                    String replace = time.replace("/", "-");
                    //字符串转成日期格式
                    Date date = StrToDate(replace + " 00:01:00");
                    jobEntity.setCreatedTime(date);
                }
                List<WebElement> futures = element.findElements(By.xpath("./a[2]/div[4]/div[1]/div"));
                ArrayList<String> strings = new ArrayList<>();
                for (WebElement future : futures) {
                    String text = future.getText();
                    switch (text) {
                        case "有奖金":
                            strings.add("1");
                            break;
                        case "交通费支给":
                            strings.add("2");
                            break;
                        case "提供员工餐":
                            strings.add("3");
                            break;
                        case "提供住宿":
                            strings.add("4");
                            break;
                        case "提供就职签证":
                            strings.add("5");
                            break;
                        case "社保年金":
                            strings.add("6");
                            break;
                        case "有薪假期":
                            strings.add("7");
                            break;
                    }
                }
                jobEntity.setFeature(String.join(",", strings));
                String companyName = element.findElement(By.xpath("./a[2]/div[4]/div[2]")).getText();
                jobEntity.setCompanyName(companyName);

                String url = element.findElement(By.xpath("./a[2]")).getAttribute("href");
                hrefs.add(url);
                jobEntity.setBusinessUserId(2L);
                jobEntities.add(jobEntity);
                //System.out.println(jobEntity);
                //System.out.println("----------------------");
            }

            //进入详情页收集数据
            for (int i = 0; i < hrefs.size(); i++) {
                driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                driver.get(hrefs.get(i));
                List<WebElement> detailElements =driver.findElements(By.xpath("/html/body/div[3]/div[1]/div/div[3]/div[5]"));
                if (detailElements.size()>0) {
                   String detail = detailElements.get(0).getText();
                    jobEntities.get(i).setWorkDetail(detail);
                }
                List<WebElement> addressElements =driver.findElements(By.xpath("/html/body/div[3]/div[1]/div/div[3]/div[7]/div[1]/span"));
                if (addressElements.size()>0) {
                    String address = addressElements.get(0).getText();
                    jobEntities.get(i).setAddress(address);
                }
            }
            //System.out.println(jobEntities);
            //jobService.saveBatch(jobEntities); //最好还是按顺序执行
            jobEntities.forEach(jobService::save);
        }
    }

    public Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
