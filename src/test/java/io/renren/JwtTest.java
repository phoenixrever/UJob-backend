package io.renren;

import io.renren.common.utils.Constant;
import io.renren.config.MyElasticSearchConfig;
import io.renren.modules.app.utils.JwtUtils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Conditional;

/**
 * 新spring boot 不用junit4 了 用junit5（jupiter）了 不需要这个注解
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
@ConditionalOnProperty(name = "renren.elasticsearch.open", havingValue = "true")
public class JwtTest {
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void test() {
        String token = jwtUtils.generateToken("shadow",Constant.GENERAL_USER);
        //int a=10/0;
        System.out.println("--------------JWTTEST----------");
        System.out.println(token);
    }

}
