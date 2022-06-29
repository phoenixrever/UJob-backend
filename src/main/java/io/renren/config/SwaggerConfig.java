/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
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
 * @ApiOperation注解用在方法上，说明该方法的作用 @ApiOperation("列表")
 * @ApiParam注解用在方法参数  @ApiParam(value= "用户名", required = true)
 * @ApiIgnore注解，可用于类、方法或参数上，表示生成Swagger接口文档时，忽略类、方法或参数。
 *
 * Swagger路径：http://localhost:8080/renren-fast/swagger/index.html
 * Swagger注解路径：http://localhost:8080/renren-fast/swagger-ui.html
 */
@Configuration
//new Docket(DocumentationType.SWAGGER_2).enable(false) 也可以关闭swagger havingValue为期望值
@ConditionalOnProperty(prefix = "swagger",value = {"enable"},havingValue = "true")
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {
        //添加head参数配置start
            ParameterBuilder lang = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        // Authorization，可以自定义名称-->token
        lang.name("accept-language").description("国际化").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(lang.build());
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
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
            .title("人人开源")
            .description("renren-fast文档")
            .termsOfServiceUrl("https://www.renren.io")
            .version("3.0.0")
            .build();
    }

    private List<ApiKey> security() {
        return newArrayList(
            new ApiKey("token", "token", "header")
        );
    }

}