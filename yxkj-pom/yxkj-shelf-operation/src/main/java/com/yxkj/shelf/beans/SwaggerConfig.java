package com.yxkj.shelf.beans;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket petApi() {
    return new Docket(DocumentationType.SWAGGER_2).globalOperationParameters(setHeaderToken())
        .apiInfo(apiInfo()).select()
        .apis(RequestHandlerSelectors.basePackage("com.yxkj.shelf.controller")).build();

  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("API Document").description("无人货架接口文档")
        .termsOfServiceUrl("http://localhost:8080").version("1.0").build();
  }

  private List<Parameter> setHeaderToken() {
    ParameterBuilder tokenPar = new ParameterBuilder();
    List<Parameter> pars = new ArrayList<>();
    tokenPar.name("X-Auth-Token").description("token").modelRef(new ModelRef("string"))
        .parameterType("header").required(false).build();
    pars.add(tokenPar.build());
    return pars;
  }
}
