package cl.coopeuch.ecd.mscuenta.application.port.interactor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.coopeuch.ecd.mscuenta.application.ApplicationException;
import cl.coopeuch.ecd.mscuenta.application.CuentaService;
import cl.coopeuch.ecd.mscuenta.application.dto.in.CuentaCreacion;
import cl.coopeuch.ecd.mscuenta.application.dto.in.CuentaTransaccion;
import cl.coopeuch.ecd.mscuenta.application.dto.out.CuentaResumen;
import cl.coopeuch.ecd.mscuenta.application.port.output.ICuentaCache;
import cl.coopeuch.ecd.mscuenta.application.port.output.ICuentaDb;
import cl.coopeuch.ecd.mscuenta.domain.Cuenta;
import cl.coopeuch.ecd.mscuenta.domain.ICuentaDomainService;

@SpringBootTest(classes = CuentaService.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ICuentaServiceTest {

	@Autowired
	private ICuentaService cuentaService;

	@MockBean
	private ICuentaDb cuentaDb;

	@MockBean
	private ICuentaDomainService cuentaDomain;

	@MockBean
	private ICuentaCache cuentaCache;

	private Cuenta cuenta;

	@Before
	public void prepare() {
		cuenta = new Cuenta();
		cuenta.setBloqueado(false);
		cuenta.setFechaActualizacion(null);
		cuenta.setFechaCreacion(LocalDateTime.now());
		cuenta.setId(1L);
		cuenta.setNumero("000001");
		cuenta.setRut("107574042");
		cuenta.setSaldo(500000D);
		cuenta.setVigente(true);

		when(this.cuentaDb.crear(any())).thenReturn(cuenta);
		when(this.cuentaDb.actualizar(any())).thenReturn(cuenta);
	}

	@Test
	public void dadoRutMasNumeroDeClienteEntoncesObtieneCuentaCache() {
		when(this.cuentaCache.obtener(anyString())).thenReturn(cuenta);

		String rut = "107574042";
		String numero = "000001";

		CuentaResumen cuentaResumen = cuentaService.obtener(rut, numero);

		assertNotNull(cuentaResumen);
	}

	@Test
	public void dadoRutMasNumeroDeClienteEntoncesObtieneCuentaDb() {
		when(this.cuentaDb.obtener(anyString(), anyString())).thenReturn(cuenta);

		String rut = "107574042";
		String numero = "000001";

		CuentaResumen cuentaResumen = cuentaService.obtener(rut, numero);

		assertNotNull(cuentaResumen);
	}

	@Test
	public void dadoRutMasNumeroDeClienteEntoncesNoObtieneCuenta() {

		String rut = "107574042";
		String numero = "000001";

		try {
			cuentaService.obtener(rut, numero);
			Assert.fail();
		} catch (ApplicationException ex) {
			Assert.assertEquals("No se logro obtener la cuenta", ex.getMessage());
		}
	}

	@Test
	public void dadoRutYDatosCuentaEntoncesCrearCuenta() {
		String rut = "107574042";
		CuentaCreacion cuentaCreacion = new CuentaCreacion();
		cuentaCreacion.setBloqueado(false);
		cuentaCreacion.setNumero("000001");
		cuentaCreacion.setSaldo(500000D);
		cuentaCreacion.setVigente(true);

		CuentaResumen cuentaResumen = cuentaService.crear(rut, cuentaCreacion);

		assertNotNull(cuentaResumen);
	}

	@Test
	public void dadoRutYDatosCuentaEntoncesIntentaCrearCuentaNoVigente() {
		String rut = "107574042";
		CuentaCreacion cuentaCreacion = new CuentaCreacion();
		cuentaCreacion.setBloqueado(false);
		cuentaCreacion.setNumero("000001");
		cuentaCreacion.setSaldo(500000D);
		cuentaCreacion.setVigente(false);

		try {
			cuentaService.crear(rut, cuentaCreacion);
			Assert.fail();
		} catch (ApplicationException ex) {
			Assert.assertEquals("Los registros de cuenta se deben crear vigentes", ex.getMessage());
		}
	}

	@Test
	public void dadoRutYDatosTransaccionEntoncesRealizarAbono() {

		when(this.cuentaDb.obtener(anyString(), anyString())).thenReturn(cuenta);
		when(this.cuentaCache.obtener(anyString())).thenReturn(cuenta);

		String rut = "107574042";

		CuentaTransaccion cuentaTransaccion = new CuentaTransaccion();
		cuentaTransaccion.setMonto(100000D);
		cuentaTransaccion.setNumero("000001");

		CuentaResumen cuentaResumen = cuentaService.realizarAbono(rut, cuentaTransaccion);

		assertNotNull(cuentaResumen);
	}

	@Test
	public void dadoRutYDatosTransaccionEntoncesIntentarRealizarAbonoSinCuenta() {

		String rut = "107574042";
		CuentaTransaccion cuentaTransaccion = new CuentaTransaccion();
		cuentaTransaccion.setMonto(100000D);
		cuentaTransaccion.setNumero("000001");

		try {
			cuentaService.realizarAbono(rut, cuentaTransaccion);
			Assert.fail();
		} catch (ApplicationException ex) {
			Assert.assertEquals("No es posible realizar el abono, la cuenta no existe", ex.getMessage());
		}

	}

	@Test
	public void dadoRutYDatosTransaccionEntoncesIntentarRealizarAbonoCuentaNoVigente() {

		cuenta.setVigente(false);
		when(this.cuentaDb.obtener(anyString(), anyString())).thenReturn(cuenta);

		String rut = "107574042";
		CuentaTransaccion cuentaTransaccion = new CuentaTransaccion();
		cuentaTransaccion.setMonto(100000D);
		cuentaTransaccion.setNumero("000001");

		try {
			cuentaService.realizarAbono(rut, cuentaTransaccion);
			Assert.fail();
		} catch (ApplicationException ex) {
			Assert.assertEquals("No es posible realizar un abono sobre una cuenta no vigente", ex.getMessage());
		}

	}

	@Test
	public void dadoRutYDatosTransaccionEntoncesRealizarCargo() {

		when(this.cuentaDb.obtener(anyString(), anyString())).thenReturn(cuenta);
		when(this.cuentaCache.obtener(anyString())).thenReturn(cuenta);

		String rut = "107574042";

		CuentaTransaccion cuentaTransaccion = new CuentaTransaccion();
		cuentaTransaccion.setMonto(100000D);
		cuentaTransaccion.setNumero("000001");

		CuentaResumen cuentaResumen = cuentaService.realizarCargo(rut, cuentaTransaccion);

		assertNotNull(cuentaResumen);
	}

	@Test
	public void dadoRutYDatosTransaccionEntoncesIntentarRealizarCargoSinCuenta() {

		String rut = "107574042";

		CuentaTransaccion cuentaTransaccion = new CuentaTransaccion();
		cuentaTransaccion.setMonto(100000D);
		cuentaTransaccion.setNumero("000001");

		try {
			cuentaService.realizarCargo(rut, cuentaTransaccion);
			Assert.fail();
		} catch (ApplicationException ex) {
			Assert.assertEquals("No es posible realizar el cargo, la cuenta no existe", ex.getMessage());
		}

	}

	@Test
	public void dadoRutYDatosTransaccionEntoncesIntentarRealizarCargoCuentaNoVigente() {

		cuenta.setVigente(false);
		when(this.cuentaDb.obtener(anyString(), anyString())).thenReturn(cuenta);

		String rut = "107574042";

		CuentaTransaccion cuentaTransaccion = new CuentaTransaccion();
		cuentaTransaccion.setMonto(100000D);
		cuentaTransaccion.setNumero("000001");

		try {
			cuentaService.realizarCargo(rut, cuentaTransaccion);
			Assert.fail();
		} catch (ApplicationException ex) {
			Assert.assertEquals("No es posible realizar un cargo sobre una cuenta no vigente", ex.getMessage());
		}

	}

}
