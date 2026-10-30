package pe.edu.upeu.lp2_back_gp1.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("APi ecommerce LP2- UPeU")
                        .version("1.0")
                        .description("Documentacion de servicios para el proyecto de LP II")
                );
    }
}
