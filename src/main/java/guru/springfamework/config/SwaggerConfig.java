package guru.springfamework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    public static final String CUSTOMER_TAG="Great class, this customer";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        final ApiInfoBuilder builder = new ApiInfoBuilder();

        return builder.title("My Application API through Swagger UI")
                .version("1.0")
                .license("Some copyright test")
                .contact(new Contact("Mirko Leschikar",
                        "https://blog.carls-rest.de",
                        "mirko@leschikar.de"))
                .build();
    }
}

