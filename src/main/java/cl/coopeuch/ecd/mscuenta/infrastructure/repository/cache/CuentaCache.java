package cl.coopeuch.ecd.mscuenta.infrastructure.repository.cache;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.coopeuch.ecd.infrastructurecross.util.json.JsonUtil;
import cl.coopeuch.ecd.infrastructurecross.util.metrica.IMetrica;
import cl.coopeuch.ecd.infrastructurecross.util.metrica.MetricaParam;
import cl.coopeuch.ecd.mscuenta.application.port.output.ICuentaCache;
import cl.coopeuch.ecd.mscuenta.domain.Cuenta;
import cl.coopeuch.ecd.mscuenta.infrastructurecross.application.Setting;
import cl.coopeuch.ecd.mscuenta.infrastructurecross.cache.ICacheRepository;



@Service
public class CuentaCache implements ICuentaCache {

	private String coleccion = "cuenta_";
	
	@Autowired
	private ICacheRepository cacheRepository;
	
	@Autowired
	private IMetrica metricaRepository;
	
	@Autowired
	private Setting setting;
	
	
	@Override
	public void guardar(String key, Cuenta cuenta) {
		LocalDateTime inicioProceso = LocalDateTime.now();		
		cacheRepository.guardar(coleccion + key, JsonUtil.getInstance().obtenerJson(cuenta), setting.getRedisExpiracionCuenta());	
		
		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"CACHE_CUENTA_GUARDAR", (double) ChronoUnit.MILLIS.between(inicioProceso, LocalDateTime.now()));
		metricaRepository.putTiempoRespuestaAsync(metricaParam);			
	}

	@Override
	public Cuenta obtener(String key) {
		LocalDateTime inicioProceso = LocalDateTime.now();
		Cuenta cuenta = JsonUtil.getInstance().obtenerInstancia(Cuenta.class, cacheRepository.obtener(coleccion + key));		
				
		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"CACHE_CUENTA_OBTENER", (double) ChronoUnit.MILLIS.between(inicioProceso, LocalDateTime.now()));
		metricaRepository.putTiempoRespuestaAsync(metricaParam);	
		
		return cuenta;
	}

	@Override
	public void borrar(String key) {
		LocalDateTime inicioProceso = LocalDateTime.now();
		cacheRepository.borrar(coleccion + key);
		
		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"CACHE_CUENTA_BORRAR", (double) ChronoUnit.MILLIS.between(inicioProceso, LocalDateTime.now()));
		metricaRepository.putTiempoRespuestaAsync(metricaParam);			
	}

}

