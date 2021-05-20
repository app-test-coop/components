package cl.coopeuch.ecd.mscuenta.application.port.interactor;

import java.util.concurrent.CompletionStage;

import cl.coopeuch.ecd.mscuenta.application.dto.out.Hacer;
import reactor.core.publisher.Mono;

public interface IToDoService {
	Mono<Hacer> obtener(int id);	
}
