package io.renren.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.util.Locale;

//一定不要忘记替换spring本身的 LocaleResolver 可以直接给组件取别名替换容器中重名的组件
//也可以@bean 的方式注入 这里用bean
//@Component("localeResolver")

public class LocaleConfig implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String language = request.getHeader("accept-language");
        //注意如果不写 language是 en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7,ja;q=0.6
        Locale local = Locale.getDefault();
        if (StringUtils.hasText(language)){
            switch (language){
                case "zh_CN":
                    local = new Locale(Locale.CHINA.getLanguage(),"CN");
                    break;
                case "ja_JP":
                    local = new Locale(Locale.JAPAN.getLanguage(),"JP");
                    break;
            }
        }
        return local;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
