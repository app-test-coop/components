package cl.coopeuch.ecd.mscuenta;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.coopeuch.ecd.mscuenta.infrastructurecross.application.Setting;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Autowired
	private Setting setting;
	
    @Bean
    public Docket swagger() {
        String packageName = getClass().getPackage().getName();
        packageName = packageName.substring(0, packageName.lastIndexOf('.'));
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(packageName))
                .build()
                .apiInfo(getApiInformation());
    }

    private ApiInfo getApiInformation(){

        Contact contact = new Contact("Coopeuch", "https://www.coopeuch.cl", "developers@coopeuch.cl");
        return new ApiInfoBuilder()
        .title("[REST API] ["+setting.getProjectName() +"] Microservicio ["+setting.getAppName()+"].")
        .description("Este microservicio muestra como ejemplo la estructura del proyecto hexagonal, junto con exponer APIs que permiten realizar operaciones sobre cuentas.")
        .version(setting.getVersion())
        .license("Coopeuch ® " + DateTime.now().getYear() + " - Todos los derechos reservados.")
        .licenseUrl("https://www.coopeuch.cl") 
        .contact(contact)             
        .build();        
        
        
    }
}
