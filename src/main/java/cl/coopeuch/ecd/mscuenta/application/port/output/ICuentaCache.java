package cl.coopeuch.ecd.mscuenta.application.port.output;

import cl.coopeuch.ecd.mscuenta.domain.Cuenta;

public interface ICuentaCache {	
	void guardar(String key, Cuenta value);
	Cuenta obtener(String key);
	void borrar(String key);
}