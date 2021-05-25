package cl.coopeuch.ecd.mscuenta.application.port.input;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cl.coopeuch.ecd.application.dto.cross.GeneralRequest;
import cl.coopeuch.ecd.mscuenta.application.dto.in.ClienteNuevo;
import cl.coopeuch.ecd.mscuenta.application.dto.in.CuentaTransaccion;
import cl.coopeuch.ecd.mscuenta.domain.Cliente;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import cl.coopeuch.ecd.application.dto.cross.GeneralResponse;
import cl.coopeuch.ecd.mscuenta.application.dto.out.Hacer;

public interface IToDoController {
	ResponseEntity<GeneralResponse<Hacer>> obtener(@RequestHeader HttpHeaders headers,
			@PathVariable(required = true) Integer id, HttpServletRequest request);

	ResponseEntity<GeneralResponse<Cliente>> put(@RequestHeader HttpHeaders headers,
												 @Valid @RequestBody ClienteNuevo request);

	
}
