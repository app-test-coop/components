package cl.coopeuch.ecd.mscuenta.infrastructure.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cl.coopeuch.ecd.application.dto.cross.GeneralRequest;
import cl.coopeuch.ecd.mscuenta.application.dto.in.ClienteNuevo;
import cl.coopeuch.ecd.mscuenta.application.dto.in.CuentaCreacion;
import cl.coopeuch.ecd.mscuenta.application.dto.out.CuentaResumen;
import cl.coopeuch.ecd.mscuenta.application.port.interactor.ICuentaService;
import cl.coopeuch.ecd.mscuenta.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.coopeuch.ecd.application.dto.cross.GeneralResponse;
import cl.coopeuch.ecd.mscuenta.application.dto.out.Hacer;
import cl.coopeuch.ecd.mscuenta.application.port.input.IToDoController;
import cl.coopeuch.ecd.mscuenta.application.port.interactor.IToDoService;
import io.swagger.annotations.ApiOperation;



@RestController
@RequestMapping("/cliente")
public class ToDoController implements IToDoController {

	@Autowired
	private IToDoService toDoService;

	@Autowired
	private ICuentaService cuentaService;
	

	@ApiOperation(value = "Obtener tarea por id")
	@GetMapping(value = "v1/obtener/{id}", headers = {"Authorization"})
	@Override
	public ResponseEntity<GeneralResponse<Hacer>> obtener(@RequestHeader HttpHeaders headers,
			@PathVariable(required = true) Integer id, HttpServletRequest request) {		
		GeneralResponse<Hacer> response = new GeneralResponse<>();		
		response.setData(toDoService.obtener(id).block());
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Put Something")
	@PutMapping(value = "v1/ingresar")
	@Override
	public ResponseEntity<GeneralResponse<Cliente>> put(HttpHeaders headers, @Valid @RequestBody ClienteNuevo request) {
		GeneralResponse<Cliente> response = new GeneralResponse<>();

		Cliente cli = new Cliente();
		cli.setRut(request.getRut());
		cli.setTelefono(request.getTelefono());
		cli.setEmail(request.getEmail());
		Cliente data = cuentaService.crearCliente(cli);
		response.setData(data);
		return ResponseEntity.ok(response);

	}




	
}
