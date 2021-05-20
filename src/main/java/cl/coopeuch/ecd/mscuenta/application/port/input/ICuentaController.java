package cl.coopeuch.ecd.mscuenta.application.port.input;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import cl.coopeuch.ecd.application.dto.cross.GeneralRequest;
import cl.coopeuch.ecd.application.dto.cross.GeneralResponse;
import cl.coopeuch.ecd.mscuenta.application.dto.in.CuentaCreacion;
import cl.coopeuch.ecd.mscuenta.application.dto.in.CuentaTransaccion;
import cl.coopeuch.ecd.mscuenta.application.dto.out.CuentaResumen;

public interface ICuentaController {
	ResponseEntity<GeneralResponse<CuentaResumen>> obtener(@RequestHeader HttpHeaders headers,
			@PathVariable(required = true) String numero, HttpServletRequest request);

	ResponseEntity<GeneralResponse<CuentaResumen>> crear(@RequestHeader HttpHeaders headers,
			@Valid @RequestBody GeneralRequest<CuentaCreacion> request);

	ResponseEntity<GeneralResponse<CuentaResumen>> realizarAbono(@RequestHeader HttpHeaders headers,
			@Valid @RequestBody GeneralRequest<CuentaTransaccion> request);

	ResponseEntity<GeneralResponse<CuentaResumen>> realizarCargo(@RequestHeader HttpHeaders headers,
			@Valid @RequestBody GeneralRequest<CuentaTransaccion> request);
	
	
	ResponseEntity<GeneralResponse<String>> obtenerIp(HttpServletRequest request);
	
	ResponseEntity<GeneralResponse<String>> obtenerHoraMs();
	
	ResponseEntity<GeneralResponse<String>> obtenerHoraDb();
	
}
