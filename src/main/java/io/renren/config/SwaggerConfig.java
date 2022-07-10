/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * 简单使用说明
 * @Api注解用在类上，说明该类的作用 @Api(tags="用户管理")
 * @ApiOperation注解用在方法上，说明该方法的作用 @ApiOperation(value = "列表",notes="保存用户")
 * @ApiParam注解用在方法参数  @ApiParam(value= "用户名", required = true)
 * @ApiIgnore注解，可用于类、方法或参数上，表示生成Swagger接口文档时，忽略类、方法或参数。
 *
 * Swagger路径：http://localhost:8080/renren-fast/swagger/index.html
 * Swagger注解路径：http://localhost:8080/renren-fast/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi(Environment environment) {
        //添加head参数配置start
        ParameterBuilder lang = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        // Authorization，可以自定义名称-->token
        lang.name("accept-language").description("国际化").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(lang.build());

        //根据环境是否启动swagger
        Profiles profiles = Profiles.of("dev");
        boolean b = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(b) //生产环境 dev 启动
                .select()
                //加了ApiOperation注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                //.apis(RequestHandlerSelectors.basePackage("io.renren.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .securitySchemes(security());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("xFuture")
                .description("开发文档")
                .termsOfServiceUrl("http://xfuture.cc/")
                .version("1.0.0")
                .build();
    }

    private List<ApiKey> security() {
        return newArrayList(
                new ApiKey("token", "token", "header")
        );
    }

}