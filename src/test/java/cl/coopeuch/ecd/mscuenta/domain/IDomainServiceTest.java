package cl.coopeuch.ecd.mscuenta.domain;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.coopeuch.ecd.mscuenta.domain.Cuenta;
import cl.coopeuch.ecd.mscuenta.domain.ICuentaDomainService;

@SpringBootTest(classes = CuentaDomainService.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class IDomainServiceTest {

	@Autowired
	private ICuentaDomainService cuentaDomainService;

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

	}

	@Test
	public void dadoCuentaRealizarCargo() {
		Double montoCargo = 500D;
		Double saldoFinal = cuenta.getSaldo() - montoCargo;

		cuentaDomainService.realizarCargoDinero(cuenta, montoCargo);

		assertEquals(cuenta.getSaldo(), saldoFinal);
	}

	@Test
	public void dadoCuentaBloqueadaIntentarRealizarCargo() {

		cuenta.setBloqueado(true);
		Double montoCargo = 500D;

		try {
			cuentaDomainService.realizarCargoDinero(cuenta, montoCargo);
			Assert.fail();
		} catch (DomainException ex) {
			Assert.assertEquals("No es posible cargar dinero, porque esta bloqueada la cuenta N " + cuenta.getNumero(),
					ex.getMessage());
		}
	}

	@Test
	public void dadoCuentaIntentarRealizarCargoMontoCero() {

		Double montoCargo = 0D;

		try {
			cuentaDomainService.realizarCargoDinero(cuenta, montoCargo);
			Assert.fail();
		} catch (DomainException ex) {
			Assert.assertEquals("No es posible realizar un cargo por un monto de 0 pesos en la cuenta N " 
					+ cuenta.getNumero(), ex.getMessage());
		}
	}

	@Test
	public void dadoCuentaIntentarRealizarCargoMayorAlSaldo() {

		Double montoCargo = cuenta.getSaldo() + 1D;

		try {
			cuentaDomainService.realizarCargoDinero(cuenta, montoCargo);
			Assert.fail();
		} catch (DomainException ex) {
			Assert.assertEquals("No es posible realizar un cargo, porque no hay saldo suficiente en la cuenta N "
					+ cuenta.getNumero(), ex.getMessage());
		}
	}

	@Test
	public void dadoCuentaRealizarAbono() {
		Double montoAbono = 500D;
		Double saldoFinal = cuenta.getSaldo() + montoAbono;

		cuentaDomainService.realizarAbonoDinero(cuenta, montoAbono);

		assertEquals(cuenta.getSaldo(), saldoFinal);
	}

	@Test
	public void dadoCuentaBloqueadaIntentarRealizarAbono() {

		cuenta.setBloqueado(true);
		Double montoAbono = 500D;

		try {
			cuentaDomainService.realizarAbonoDinero(cuenta, montoAbono);
			Assert.fail();
		} catch (DomainException ex) {
			Assert.assertEquals("No es posible abonar dinero, porque esta bloqueada la cuenta N " + cuenta.getNumero(),
					ex.getMessage());
		}
	}

	@Test
	public void dadoCuentaIntentarRealizarAbonoMenorCero() {

		Double montoAbono = -1D;

		try {
			cuentaDomainService.realizarAbonoDinero(cuenta, montoAbono);
			Assert.fail();
		} catch (DomainException ex) {
			Assert.assertEquals("El monto que se abona debe ser mayor a 0 en la cuenta N " + cuenta.getNumero(),
					ex.getMessage());
		}
	}

	@Test
	public void dadoCuentaIntentarCrear() {
		Double saldo = 0D;
		cuenta.setSaldo(saldo);
		cuentaDomainService.validarCreacion(cuenta);
		assertEquals(cuenta.getSaldo(), saldo);
	}

	@Test
	public void dadoCuentaIntentarCrearlaConSaldoMayorCero() {

		try {
			cuentaDomainService.validarCreacion(cuenta);
			Assert.fail();
		} catch (DomainException ex) {
			Assert.assertEquals("La cuenta debe nacer con saldo 0 ", ex.getMessage());
		}
	}

}
