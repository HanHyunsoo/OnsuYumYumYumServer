package com.onsuyum.config;

import com.fasterxml.classmate.TypeResolver;
import com.onsuyum.common.response.FailureResponseBody;
import com.onsuyum.common.response.SuccessResponseBody;
import com.onsuyum.restaurant.dto.request.MenuRequestForm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("Onsuyum API")
                .description("온수냠냠냠 API Docs")
                .license("MIT License")
                .version("0.5")
                .build();
    }

    @Bean
    public Docket swaggerApi(TypeResolver typeResolver) {
        return new Docket(DocumentationType.OAS_30)
                .additionalModels(
                        typeResolver.resolve(SuccessResponseBody.class),
                        typeResolver.resolve(FailureResponseBody.class),
                        typeResolver.resolve(MenuRequestForm.class)
                )
                .consumes(getConsumeContentTypes())
                .produces(getProduceContentTypes())
                .apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.onsuyum"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .tags(
                        new Tag("Category API", "카테고리에 관련한 기능들을 처리"),
                        new Tag("Menu API", "메뉴에 관련한 기능"),
                        new Tag("Restaurant Category API", "음식점과 카테고리의 관계에 대한 기능들"),
                        new Tag("Restaurant API", "음식점 관련 기능"),
                        new Tag("Image API", "이미지 관련 기능")
                );
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("multipart/form-data");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        produces.add("image/png");
        produces.add("image/jpeg");
        return produces;
    }
}