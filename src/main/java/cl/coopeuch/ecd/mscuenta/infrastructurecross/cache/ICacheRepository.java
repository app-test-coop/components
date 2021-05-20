package cl.coopeuch.ecd.mscuenta.infrastructurecross.cache;

import java.util.List;

public interface ICacheRepository {
	void guardar(String key, String value, int expiration);

	String obtener(String key);

	List<String> obtenerKeys(String patternKey, int cantidadBuscar);	
	
	List<String> obtenerValores(String patternKey, int cantidadBuscar);

	void borrar(String key);

	Boolean validarConexion();
}
