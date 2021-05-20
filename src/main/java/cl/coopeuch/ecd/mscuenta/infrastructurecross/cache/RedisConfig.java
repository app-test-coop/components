package cl.coopeuch.ecd.mscuenta.infrastructurecross.cache;


import java.time.Duration;
import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import cl.coopeuch.ecd.mscuenta.infrastructurecross.application.Setting;
import io.lettuce.core.ReadFrom;


@Configuration
public class RedisConfig {
	
	@Autowired
	private Setting setting;
		
	@Bean
	LettuceConnectionFactory connectionFactory() {	
		
		if(setting.getActiveProfile().equalsIgnoreCase("local")) {			
			RedisStandaloneConfiguration configuracion = new RedisStandaloneConfiguration();
			configuracion.setHostName(setting.getRedisHost().split(":")[0]);
			configuracion.setPort(Integer.parseInt(setting.getRedisHost().split(":")[1]));
			configuracion.setPassword(setting.getRedisPassword());
									
		    return new LettuceConnectionFactory(configuracion, getClientConfiguration());
		}else {						
			List<String> nodos = Arrays.asList(setting.getRedisHost().split(","));
			RedisClusterConfiguration configuracion = new RedisClusterConfiguration(nodos);
			configuracion.setPassword(setting.getRedisPassword());
			
			return new LettuceConnectionFactory(configuracion, getClientConfiguration());			
		}		
	}
		
	private LettuceClientConfiguration getClientConfiguration() {
		return LettuceClientConfiguration.builder()			
				.readFrom(ReadFrom.REPLICA_PREFERRED)					
				.commandTimeout(Duration.ofMillis(setting.getRedisTimeout()))
			    .shutdownTimeout(Duration.ZERO)
			    .build();		
	}
	
	@Bean
	StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}
		
	
}
