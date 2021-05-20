package cl.coopeuch.ecd.mscuenta.application.port.output;

import cl.coopeuch.ecd.mscuenta.infrastructure.agent.dto.ToDo;
import reactor.core.publisher.Mono;

public interface IToDoAgent {
	Mono<ToDo> obtener(int id);
}
