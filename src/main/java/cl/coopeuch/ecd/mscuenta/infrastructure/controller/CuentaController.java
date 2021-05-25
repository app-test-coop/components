package cl.coopeuch.ecd.mscuenta.infrastructure.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import cl.coopeuch.ecd.application.dto.cross.GeneralRequest;
import cl.coopeuch.ecd.application.dto.cross.GeneralResponse;
import cl.coopeuch.ecd.infrastructurecross.util.http.HeaderUtil;
import cl.coopeuch.ecd.infrastructurecross.util.ip.IpUtil;
import cl.coopeuch.ecd.infrastructurecross.util.logging.ILogging;
import cl.coopeuch.ecd.infrastructurecross.util.metrica.IMetrica;
import cl.coopeuch.ecd.infrastructurecross.util.metrica.MetricaParam;
import cl.coopeuch.ecd.infrastructurecross.util.token.TokenUtil;
import cl.coopeuch.ecd.mscuenta.application.dto.in.CuentaCreacion;
import cl.coopeuch.ecd.mscuenta.application.dto.in.CuentaTransaccion;
import cl.coopeuch.ecd.mscuenta.application.dto.out.CuentaResumen;
import cl.coopeuch.ecd.mscuenta.application.dto.out.Hacer;
import cl.coopeuch.ecd.mscuenta.application.port.input.ICuentaController;
import cl.coopeuch.ecd.mscuenta.application.port.interactor.ICuentaService;
import cl.coopeuch.ecd.mscuenta.infrastructurecross.application.Setting;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cuenta")
public class CuentaController implements ICuentaController {

	@Autowired
	private ICuentaService cuentaService;

	@Autowired
	private IMetrica metricaRepository;

	@Autowired
	private Setting setting;
	
	@Autowired
	private ILogging log;

	@ApiOperation(value = "Obtener cuenta de socio mediante token y numero de cuenta")
	@GetMapping(value = "v1/obtener/{numero}", headers = {"Authorization"})
	@Override
	public ResponseEntity<GeneralResponse<CuentaResumen>> obtener(@RequestHeader HttpHeaders headers,
			@PathVariable(required = true) String numero, HttpServletRequest request) {
		
		log.info("IP cliente: " + IpUtil.getInstance().getClientIp(request));
		
		LocalDateTime inicioProceso = LocalDateTime.now();

		GeneralResponse<CuentaResumen> response = new GeneralResponse<>();
		String rut = TokenUtil.getInstance().obtenerClaim(HeaderUtil.getInstance().getToken(headers), "sub");

		CuentaResumen data = cuentaService.obtener(rut, numero);
		response.setData(data);
	
		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"API_CUENTA_OBTENER", (double) ChronoUnit.MILLIS.between(inicioProceso, LocalDateTime.now()));
		metricaRepository.putTiempoRespuestaAsync(metricaParam);

		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Crear cuenta de socio")
	@PostMapping(value = "v1/crear", headers = {"Authorization"})
	@Override
	public ResponseEntity<GeneralResponse<CuentaResumen>> crear(@RequestHeader HttpHeaders headers,
			@Valid @RequestBody GeneralRequest<CuentaCreacion> request) {

		LocalDateTime inicioProceso = LocalDateTime.now();

		GeneralResponse<CuentaResumen> response = new GeneralResponse<>();
		String rut = TokenUtil.getInstance().obtenerClaim(HeaderUtil.getInstance().getToken(headers), "sub");

		CuentaResumen data = cuentaService.crear(rut, request.getParams());
		response.setData(data);

	
		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"API_CUENTA_CREAR", (double) ChronoUnit.MILLIS.between(inicioProceso, LocalDateTime.now()));
		metricaRepository.putTiempoRespuestaAsync(metricaParam);		

		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Realizar abono en dinero a una cuenta")
	@PutMapping(value = "v1/realizarabono")
	@Override
	public ResponseEntity<GeneralResponse<CuentaResumen>> realizarAbono(@RequestHeader HttpHeaders headers,
			@Valid GeneralRequest<CuentaTransaccion> request) {


		GeneralResponse<CuentaResumen> response = new GeneralResponse<>();
		//String rut = TokenUtil.getInstance().obtenerClaim(HeaderUtil.getInstance().getToken(headers), "sub");

		CuentaResumen data = new CuentaResumen();//  cuentaService.realizarAbono(rut, request.getParams());
		data.setNumero("777");
		
		response.setData(data);
		
		/*MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"API_CUENTA_REALIZARABONO", (double) ChronoUnit.MILLIS.between(inicioProceso, LocalDateTime.now()));
		metricaRepository.putTiempoRespuestaAsync(metricaParam);
*/
		return ResponseEntity.ok(response);
		
	}

	@ApiOperation(value = "Realizar cargo en dinero a una cuenta")
	@PutMapping(value = "v1/realizarcargo", headers = {"Authorization"})
	@Override
	public ResponseEntity<GeneralResponse<CuentaResumen>> realizarCargo(@RequestHeader HttpHeaders headers,
			@Valid GeneralRequest<CuentaTransaccion> request) {

		LocalDateTime inicioProceso = LocalDateTime.now();

		GeneralResponse<CuentaResumen> response = new GeneralResponse<>();
		String rut = TokenUtil.getInstance().obtenerClaim(HeaderUtil.getInstance().getToken(headers), "sub");

		CuentaResumen data = cuentaService.realizarCargo(rut, request.getParams());
		response.setData(data);

		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"API_CUENTA_REALIZARCARGO", (double) ChronoUnit.MILLIS.between(inicioProceso, LocalDateTime.now()));
		metricaRepository.putTiempoRespuestaAsync(metricaParam);			
		
		return ResponseEntity.ok(response);
	}

	
	
	@ApiOperation(value = "Obtener Ip request")
	@GetMapping("v1/ip/obtener")
	@Override
	public ResponseEntity<GeneralResponse<String>> obtenerIp(HttpServletRequest request) {
		
		GeneralResponse<String> response	 = new GeneralResponse<>();
		response.setData(IpUtil.getInstance().getClientIp(request));		

		return ResponseEntity.ok(response);
	}
	
		
	@ApiOperation(value = "Obtener hora ms")
	@GetMapping("v1/horams/obtener")
	@Override
	public ResponseEntity<GeneralResponse<String>> obtenerHoraMs() {
		
		GeneralResponse<String> response = new GeneralResponse<>();
		response.setData(LocalDateTime.now().toString());		

		return ResponseEntity.ok(response);
	}
	
	
	@ApiOperation(value = "Obtener hora ms")
	@GetMapping("v1/horadb/obtener")
	@Override
	public ResponseEntity<GeneralResponse<String>> obtenerHoraDb() {
		
		GeneralResponse<String> response = new GeneralResponse<>();
		response.setData(cuentaService.obtenerHora());		
	
		return ResponseEntity.ok(response);
	}
		
	
}
