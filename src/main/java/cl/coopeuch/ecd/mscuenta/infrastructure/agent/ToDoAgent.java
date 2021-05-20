package cl.coopeuch.ecd.mscuenta.infrastructure.agent;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.coopeuch.ecd.mscuenta.application.port.output.IToDoAgent;
import cl.coopeuch.ecd.mscuenta.infrastructure.agent.dto.ToDo;
import cl.coopeuch.ecd.mscuenta.infrastructurecross.application.Setting;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import reactor.core.publisher.Mono;

@Service
public class ToDoAgent implements IToDoAgent {

	@Autowired
	private Setting setting;	
		
	@Override
	@TimeLimiter(name = "agentTodoObtener", fallbackMethod = "defaultFallback")
	public Mono<ToDo> obtener(int id) {
		return WebClient.create(this.setting.getApiRestMockend())
				.get().uri(uriBuilder -> uriBuilder.path("/"+id).build())
				.retrieve()
				.bodyToMono(ToDo.class)
				.delayElement(Duration.ofSeconds(10));
	}
	   	   
    private Mono<ToDo> defaultFallback(int id, Exception ex) throws Exception{
    	throw new AgentException(ex.getMessage(), "ARQ-33", HttpStatus.REQUEST_TIMEOUT); 
    }
	
    
    
    
}
