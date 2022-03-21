package com.atren.server.config.swager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Swagger2配置类
 * @author rxyLucky
 * @create 2022-02-10 16:00
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.atren.server.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(SecuritySchemes());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("咖啡在线销售网站接口文档")
                .description("咖啡在线销售网站接口文档")
                .contact(new Contact("任向阳","http://localhost:8081/doc.html","14301830@qq.com"))
                .version("1.0")
                .build();
    }

    private List<SecurityContext> securityContexts(){
        return Arrays.asList(SecurityContext.builder().securityReferences(defaultAuth()).build());
    }

    private List<SecurityReference> defaultAuth() {
        ArrayList<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization",authorizationScopes));
        return result;
    }

    private List<ApiKey> SecuritySchemes(){
        //设置请求头信息
        ArrayList<ApiKey> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey("token", "token", "header");
        result.add(apiKey);
        return result;

    }
}
