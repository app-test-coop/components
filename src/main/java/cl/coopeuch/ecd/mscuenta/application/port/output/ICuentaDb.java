package cl.coopeuch.ecd.mscuenta.application.port.output;

import cl.coopeuch.ecd.mscuenta.domain.Cliente;
import cl.coopeuch.ecd.mscuenta.domain.Cuenta;

public interface ICuentaDb {
	Cuenta obtener(String rut, String numero);
	Cuenta crear(Cuenta cuenta);
	Cuenta actualizar(Cuenta cuenta);
	String obtenerHora();
	Cliente crearCliente(Cliente cliente);

	}
