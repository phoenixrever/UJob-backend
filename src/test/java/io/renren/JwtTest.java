package io.renren;

import io.renren.modules.app.utils.JwtUtils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 新spring boot 不用junit4 了 用junit5（jupiter）了 不需要这个注解
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTest {
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void test() {
        String token = jwtUtils.generateToken(1);
        //int a=10/0;
        System.out.println("--------------JWTTEST----------");
        System.out.println(token);
    }

}
