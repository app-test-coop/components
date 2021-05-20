package cl.coopeuch.ecd.mscuenta.infrastructure.controller;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/todo")
public class ToDoController implements IToDoController {

	@Autowired
	private IToDoService toDoService;
	

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
	@PutMapping(value = "v1/putTodo", headers = {"Authorization"})
	@Override
	public ResponseEntity<GeneralResponse<Hacer>> put(@RequestHeader HttpHeaders headers,
														  @PathVariable(required = true) Integer id, HttpServletRequest request) {
		GeneralResponse<Hacer> response = new GeneralResponse<>();
		Hacer a = new Hacer();
		a.setId(1);
		a.setTitle("Put Exitoso");
		response.setData(new Hacer());
		return ResponseEntity.ok(response);
	}

	
}
