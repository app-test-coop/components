package cl.coopeuch.ecd.mscuenta.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.coopeuch.ecd.infrastructurecross.util.mapper.MapperUtil;
import cl.coopeuch.ecd.mscuenta.application.dto.out.Hacer;
import cl.coopeuch.ecd.mscuenta.application.port.interactor.IToDoService;
import cl.coopeuch.ecd.mscuenta.application.port.output.IToDoAgent;
import reactor.core.publisher.Mono;

@Service
public class ToDoService implements IToDoService {
	
	@Autowired
	private IToDoAgent todoAgent;
	
	@Override
	public Mono<Hacer> obtener(int id) {		
		return todoAgent.obtener(id).map(todo-> MapperUtil.getInstance().map(todo, Hacer.class));
	}


}
