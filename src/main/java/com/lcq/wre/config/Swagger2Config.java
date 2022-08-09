package com.lcq.wre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2API文档的配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    public static final String Article_Controller = "ArticleController";
    public static final String Category_Controller = "CategoryController";
    public static final String Chat_Controller = "ChatController";
    public static final String Comment_Controller = "CommentController";
    public static final String Login_Controller = "LoginController";
    public static final String Logout_Controller = "LogoutController";
    public static final String Register_Controller = "RegisterController";
    public static final String SendCode_Controller = "SendCodeController";
    public static final String Tags_Controller = "TagsController";
    public static final String Upload_Controller = "UploadController";
    public static final String User_Controller = "UserController";

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage("com.lcq.wre.controller"))
                //为有@Api注解的Controller生成API文档
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //为有@ApiOperation注解的方法生成API文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(Article_Controller, "文章管理"))
                .tags(new Tag(Category_Controller, "文章分类管理"))
                .tags(new Tag(Chat_Controller, "私信聊天管理"))
                .tags(new Tag(Login_Controller, "登录管理"))
                .tags(new Tag(Logout_Controller, "登出管理"))
                .tags(new Tag(Comment_Controller, "评论管理"))
                .tags(new Tag(Register_Controller, "注册管理"))
                .tags(new Tag(Upload_Controller, "上传oss管理"))
                .tags(new Tag(User_Controller, "用户管理"))
                .tags(new Tag(SendCode_Controller, "验证码管理"))
                .tags(new Tag(Tags_Controller, "文章标签管理"))
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SwaggerUI演示")
                .description("WeWrite")
                .contact("lcq")
                .version("1.0")
                .build();
    }
}
