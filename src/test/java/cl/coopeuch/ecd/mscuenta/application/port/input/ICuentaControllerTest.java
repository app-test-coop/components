package cl.coopeuch.ecd.mscuenta.application.port.input;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.any;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.coopeuch.ecd.application.dto.cross.GeneralRequest;
import cl.coopeuch.ecd.application.dto.cross.GeneralResponse;
import cl.coopeuch.ecd.infrastructurecross.util.logging.ILogging;
import cl.coopeuch.ecd.infrastructurecross.util.metrica.IMetrica;
import cl.coopeuch.ecd.mscuenta.application.dto.in.CuentaCreacion;
import cl.coopeuch.ecd.mscuenta.application.dto.in.CuentaTransaccion;
import cl.coopeuch.ecd.mscuenta.application.dto.out.CuentaResumen;
import cl.coopeuch.ecd.mscuenta.application.port.interactor.ICuentaService;
import cl.coopeuch.ecd.mscuenta.infrastructure.controller.CuentaController;
import cl.coopeuch.ecd.mscuenta.infrastructurecross.application.Setting;

@SpringBootTest(classes = CuentaController.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ICuentaControllerTest {

	@Autowired
	private ICuentaController cuentaController;

	
	@MockBean
	private ICuentaService cuentaService;
	
	@MockBean
	private IMetrica metricaRepository;
	
	@MockBean
	private Setting setting;
	
	@MockBean
	private ILogging log;
	
	@Before
	public void prepare() {

		CuentaResumen cuentaResumen = new CuentaResumen();
		cuentaResumen.setBloqueado(false);
		cuentaResumen.setNumero("000001");
		cuentaResumen.setRut("107574042");
		cuentaResumen.setSaldo(500000D);
		
		when(this.cuentaService.obtener(any(), any())).thenReturn(cuentaResumen);

	}

	@Test
	public void dadoTokenYnumeroCuentaObtenerCuenta() {
		
		String numero = "000001";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer eyJraWQiOiJDb29wZXVjaCBDaGlsZSIsImFsZyI6IkVTMjU2In0.eyJzdWIiOiIxMDc1NzQwNDIiLCJpc3MiOiJDb29wZXVjaCIsImNhbmFsIjoiV2ViIiwiZXhwIjoiMjAyMC0wOS0wMlQyMTo1NToxMS4zNTQiLCJpYXQiOiIyMDIwLTA5LTAyVDIxOjM1OjExLjM1NCIsInJvbCI6IlNvY2lvIn0.tFSjA4dQKkhCI9I6vYMAI-GygytZjfYYnIWe3G8ZDMGKKyYD5lultoTL2WHwra0vRHjebqxn8PS4xvrjQN_T3g");	
		HttpServletRequest request = mock(HttpServletRequest.class);	
		
		ResponseEntity<GeneralResponse<CuentaResumen>> response = cuentaController.obtener(headers, numero, request);
		
		assertNotNull(response);
	}

	@Test
	public void dadoTokenYdatosCuentaCrearCuenta() {
		
		GeneralRequest<CuentaCreacion> request = new GeneralRequest<CuentaCreacion>();
		
		CuentaCreacion cuentaCreacion = new CuentaCreacion();
		cuentaCreacion.setBloqueado(false);
		cuentaCreacion.setNumero("000001");
		cuentaCreacion.setSaldo(500000D);
		cuentaCreacion.setVigente(true);
		
		request.setParams(cuentaCreacion);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer eyJraWQiOiJDb29wZXVjaCBDaGlsZSIsImFsZyI6IkVTMjU2In0.eyJzdWIiOiIxMDc1NzQwNDIiLCJpc3MiOiJDb29wZXVjaCIsImNhbmFsIjoiV2ViIiwiZXhwIjoiMjAyMC0wOS0wMlQyMTo1NToxMS4zNTQiLCJpYXQiOiIyMDIwLTA5LTAyVDIxOjM1OjExLjM1NCIsInJvbCI6IlNvY2lvIn0.tFSjA4dQKkhCI9I6vYMAI-GygytZjfYYnIWe3G8ZDMGKKyYD5lultoTL2WHwra0vRHjebqxn8PS4xvrjQN_T3g");	
		
		ResponseEntity<GeneralResponse<CuentaResumen>> response = cuentaController.crear(headers, request);
		
		assertNotNull(response);
	}
	
	@Test
	public void dadoTokenNumeroMontoRealizaAbono() {
		
		GeneralRequest<CuentaTransaccion> request = new GeneralRequest<CuentaTransaccion>();
		
		CuentaTransaccion CuentaTransaccion= new CuentaTransaccion();
		CuentaTransaccion.setMonto(500000D);
		CuentaTransaccion.setNumero("000001");	
		
		request.setParams(CuentaTransaccion);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer eyJraWQiOiJDb29wZXVjaCBDaGlsZSIsImFsZyI6IkVTMjU2In0.eyJzdWIiOiIxMDc1NzQwNDIiLCJpc3MiOiJDb29wZXVjaCIsImNhbmFsIjoiV2ViIiwiZXhwIjoiMjAyMC0wOS0wMlQyMTo1NToxMS4zNTQiLCJpYXQiOiIyMDIwLTA5LTAyVDIxOjM1OjExLjM1NCIsInJvbCI6IlNvY2lvIn0.tFSjA4dQKkhCI9I6vYMAI-GygytZjfYYnIWe3G8ZDMGKKyYD5lultoTL2WHwra0vRHjebqxn8PS4xvrjQN_T3g");	
		
		ResponseEntity<GeneralResponse<CuentaResumen>> response = cuentaController.realizarAbono(headers, request);
		
		assertNotNull(response);
	}	
	
	@Test
	public void dadoTokenNumeroMontoRealizaCargo() {
		
		GeneralRequest<CuentaTransaccion> request = new GeneralRequest<CuentaTransaccion>();
		
		CuentaTransaccion CuentaTransaccion= new CuentaTransaccion();
		CuentaTransaccion.setMonto(500000D);
		CuentaTransaccion.setNumero("000001");	
		
		request.setParams(CuentaTransaccion);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer eyJraWQiOiJDb29wZXVjaCBDaGlsZSIsImFsZyI6IkVTMjU2In0.eyJzdWIiOiIxMDc1NzQwNDIiLCJpc3MiOiJDb29wZXVjaCIsImNhbmFsIjoiV2ViIiwiZXhwIjoiMjAyMC0wOS0wMlQyMTo1NToxMS4zNTQiLCJpYXQiOiIyMDIwLTA5LTAyVDIxOjM1OjExLjM1NCIsInJvbCI6IlNvY2lvIn0.tFSjA4dQKkhCI9I6vYMAI-GygytZjfYYnIWe3G8ZDMGKKyYD5lultoTL2WHwra0vRHjebqxn8PS4xvrjQN_T3g");	
		
		ResponseEntity<GeneralResponse<CuentaResumen>> response = cuentaController.realizarCargo(headers, request);
		
		assertNotNull(response);
	}	
	
	
}
