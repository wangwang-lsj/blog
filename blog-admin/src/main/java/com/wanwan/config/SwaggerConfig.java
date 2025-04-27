package com.wanwan.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 1. swagger配置类
 http://localhost:8080/swagger-ui/index.html
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //是否开启 (true 开启  false隐藏。生产环境建议隐藏)
                //.enable(false)
                .select()
                //扫描的路径包,设置basePackage会将包下的所有被@Api标记类的所有方法作为api
                .apis(RequestHandlerSelectors.basePackage("com.wanwan.controller"))
                //指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("后台项目接口文档") // 文档标题
                .description("基本的一些接口说明") // 文档基本描述
                .contact(new Contact("xxxx", "https://blog.csdn.net", "3491454639@qq.com")) // 联系人信息
                .termsOfServiceUrl("http://terms.service.url/组织链接") // 组织链接
                .version("1.0") // 版本
                .license("Apache 2.0 许可") // 许可
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0") // 许可链接
                .extensions(new ArrayList<>()) // 拓展
                .build();
    }

}
