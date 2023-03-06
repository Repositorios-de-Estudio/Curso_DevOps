package com.paymentchain.billing;




import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;





@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class InvoiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvoiceApplication.class, args);
        
    }
    
    @Bean
  public GroupedOpenApi publicApi() {
      return GroupedOpenApi.builder()
              .group("digitalthinking-spis")
              .packagesToScan("com.paymentchain")             
              .pathsToMatch("/**")
              .build();
  }
  
//  @Bean
//  public OpenAPI customOpenAPI() {
//    final String securitySchemeName = "bearerAuth";
//    final String apiTitle = String.format("%s API", StringUtils.capitalize("Billing App"));
//    return new OpenAPI()
//        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
//        .components(
//            new Components()
//                .addSecuritySchemes(securitySchemeName,
//                    new SecurityScheme()
//                        .name(securitySchemeName)
//                        .type(SecurityScheme.Type.HTTP)
//                        .scheme("bearer")
//                        .bearerFormat("JWT")
//                )
//        )
//        .info(new io.swagger.v3.oas.models.info.Info().title(apiTitle).version("V1"));
//  }

}
