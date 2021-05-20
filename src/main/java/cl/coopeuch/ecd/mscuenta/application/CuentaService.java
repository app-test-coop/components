package cl.coopeuch.ecd.mscuenta.application;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import cl.coopeuch.ecd.infrastructurecross.util.mapper.MapperUtil;
import cl.coopeuch.ecd.mscuenta.application.dto.in.CuentaCreacion;
import cl.coopeuch.ecd.mscuenta.application.dto.in.CuentaTransaccion;
import cl.coopeuch.ecd.mscuenta.application.dto.out.CuentaResumen;
import cl.coopeuch.ecd.mscuenta.application.port.interactor.ICuentaService;
import cl.coopeuch.ecd.mscuenta.application.port.output.ICuentaCache;
import cl.coopeuch.ecd.mscuenta.application.port.output.ICuentaDb;
import cl.coopeuch.ecd.mscuenta.domain.Cuenta;
import cl.coopeuch.ecd.mscuenta.domain.ICuentaDomainService;

@Service
public class CuentaService implements ICuentaService {

	@Autowired
	private ICuentaDb cuentaDb;

	@Autowired
	private ICuentaDomainService cuentaDomain;

	@Autowired
	private ICuentaCache cuentaCache;

	@Override
	public CuentaResumen obtener(String rut, String numero) {

		Cuenta cuentaMemory = cuentaCache.obtener(rut + "_" + numero);

		if (cuentaMemory == null) {
			Cuenta cuenta = cuentaDb.obtener(rut, numero);

			if (cuenta == null) {
				throw new ApplicationException("No se logro obtener la cuenta", "ARQ-07", HttpStatus.NOT_FOUND);
			}

			cuentaCache.guardar(rut + "_" + numero, cuenta);

			return MapperUtil.getInstance().map(cuenta, CuentaResumen.class);
		} else {
			return MapperUtil.getInstance().map(cuentaMemory, CuentaResumen.class);
		}
	}

	@Override
	public CuentaResumen crear(String rut, CuentaCreacion cuentaCreacion) {

		if (Boolean.FALSE.equals(cuentaCreacion.getVigente())) {
			throw new ApplicationException("Los registros de cuenta se deben crear vigentes", "ARQ-08", HttpStatus.NOT_FOUND);
		}

		Cuenta cuenta = MapperUtil.getInstance().map(cuentaCreacion, Cuenta.class);
		cuenta.setRut(rut);
		cuenta.setFechaCreacion(LocalDateTime.now());

		cuentaDomain.validarCreacion(cuenta);

		return MapperUtil.getInstance().map(cuentaDb.crear(cuenta), CuentaResumen.class);
	}

	@Override
	public CuentaResumen realizarAbono(String rut, CuentaTransaccion cuentaTransaccion) {

		Cuenta cuenta = cuentaDb.obtener(rut, cuentaTransaccion.getNumero());

		if (cuenta == null) {
			throw new ApplicationException("No es posible realizar el abono, la cuenta no existe", "ARQ-09", HttpStatus.NOT_FOUND);
		}

		if (Boolean.FALSE.equals(cuenta.getVigente())) {
			throw new ApplicationException("No es posible realizar un abono sobre una cuenta no vigente", "ARQ-10", HttpStatus.NOT_FOUND);
		}

		cuentaDomain.realizarAbonoDinero(cuenta, cuentaTransaccion.getMonto());

		cuenta.setFechaActualizacion(LocalDateTime.now());

		cuenta = cuentaDb.actualizar(cuenta);

		cuentaCache.borrar(rut + "_" + cuentaTransaccion.getNumero());

		return MapperUtil.getInstance().map(cuenta, CuentaResumen.class);
	}

	@Override
	public CuentaResumen realizarCargo(String rut, CuentaTransaccion cuentaTransaccion) {

		Cuenta cuenta = cuentaDb.obtener(rut, cuentaTransaccion.getNumero());

		if (cuenta == null) {
			throw new ApplicationException("No es posible realizar el cargo, la cuenta no existe" ,"ARQ-11", HttpStatus.NOT_FOUND);
		}

		if (Boolean.FALSE.equals(cuenta.getVigente())) {
			throw new ApplicationException("No es posible realizar un cargo sobre una cuenta no vigente", "ARQ-12", HttpStatus.NOT_FOUND);
		}

		cuentaDomain.realizarCargoDinero(cuenta, cuentaTransaccion.getMonto());

		cuenta.setFechaActualizacion(LocalDateTime.now());

		cuenta = cuentaDb.actualizar(cuenta);

		cuentaCache.borrar(rut + "_" + cuentaTransaccion.getNumero());

		return MapperUtil.getInstance().map(cuenta, CuentaResumen.class);
	}

	
	@Override
	public String obtenerHora() {
		return cuentaDb.obtenerHora();
	}

	


	
}
