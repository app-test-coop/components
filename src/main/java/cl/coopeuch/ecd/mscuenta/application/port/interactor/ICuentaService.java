package cl.coopeuch.ecd.mscuenta.application.port.interactor;

import cl.coopeuch.ecd.mscuenta.application.dto.in.CuentaCreacion;
import cl.coopeuch.ecd.mscuenta.application.dto.in.CuentaTransaccion;
import cl.coopeuch.ecd.mscuenta.application.dto.out.CuentaResumen;
import cl.coopeuch.ecd.mscuenta.domain.Cliente;

public interface ICuentaService {
	CuentaResumen obtener(String rut, String numero);
	CuentaResumen crear(String rut, CuentaCreacion cuentaCreacion);	
	CuentaResumen realizarAbono(String rut, CuentaTransaccion cuentaTransaccion);
	CuentaResumen realizarCargo(String rut, CuentaTransaccion cuentaTransaccion);
	String obtenerHora();
	Cliente crearCliente(Cliente cliente);

	}
