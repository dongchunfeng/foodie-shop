package com.itzixue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/12/3
 */
@Component
@EnableSwagger2
public class Swagger2 {

    /**
     * 配置Swagger2
     * swagger-ui.html
     *  doc.html
     * @return
     */
    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2) //制定api类型为swagger2
                .apiInfo(apiInfo())//用于定义api文档汇总
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.itzixue.controller"))
                .paths(PathSelectors.any()).build()
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("天天吃货 电商平台接口api")
                .contact(new Contact("itzixue", "http://www.itzixue.com", "dcf_free@126.com"))
                .description("专门为天天吃货提供的api文档")
                .version("1.0.1")
                .termsOfServiceUrl("http://www.itzixue.com")
                .build();
    }

}
