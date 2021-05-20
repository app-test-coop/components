package cl.coopeuch.ecd.mscuenta.infrastructurecross.application;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Getter
public class Setting {
	

	@Value("${spring.app.version}") 
	private String version; 
	
	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	@Value("${spring.project.name}")
	private String projectName;		
	
	@Value("${spring.application.name}")
	private String appName;	
	
    @Value("${spring.redis.host}")
    private String redisHost;
        
    @Value("${spring.redis.password}")
    private String redisPassword;
    
    @Value("${spring.redis.timeout}")
    private Integer redisTimeout;
        
    @Value("${redis.tiempoexpiracion.cuenta}")
    private Integer redisExpiracionCuenta;    
    
    @Value("${api.rest.mockend}")
    private String apiRestMockend;        
    
}
