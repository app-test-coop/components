package cl.coopeuch.ecd.mscuenta.infrastructurecross.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import cl.coopeuch.ecd.infrastructurecross.util.logging.ILogging;



@Repository
public class CacheRepository implements ICacheRepository {

	private final StringRedisTemplate template;
	
	@Autowired
	private ILogging log;

	public CacheRepository(StringRedisTemplate template) {
		this.template = template;
	}

	@Override
	public void guardar(String key, String value, int expiration) {
		try {
			template.opsForValue().set(key, value);
			template.expire(key, expiration, TimeUnit.MINUTES);
		} catch (Exception e) {
			log.errorMesg("Redis server error al guardar key " + key + ". Message " + e.getMessage());
		}

	}

	@Override
	public String obtener(String key) {		
		try {
			return template.opsForValue().get(key);
		} catch (Exception e) {
			log.errorMesg("Redis server error al obtener key " + key + ". Message " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<String> obtenerKeys(String patternKey, int cantidadBuscar) {
        try {
        	        	
            RedisConnection redisConnection = template.getConnectionFactory().getConnection();
            ScanOptions options = ScanOptions.scanOptions().match(patternKey).count(cantidadBuscar).build();
            Cursor<byte[]> cursor = redisConnection.scan(options);
            List<String> listaKeys = new ArrayList<>();
           
            while (cursor.hasNext()) {            	
                String nombreKey = Arrays.toString(cursor.next());
                listaKeys.add(nombreKey);
            }
           
            return listaKeys;   
           
        } catch (Exception e) {
            log.errorMesg("Redis server error al obtenerKeys " + patternKey + ". Message " + e.getMessage());
        }
        return Collections.emptyList();
    }
		
	@Override
	public List<String> obtenerValores(String patternKey, int cantidadBuscar) {
	
		try {
			List<String> keys = obtenerKeys(patternKey, cantidadBuscar);
			return template.opsForValue().multiGet(keys);
		} catch (Exception e) {
			log.errorMesg("Redis server error al obtenerValores " + patternKey + ". Message " + e.getMessage());
		}
		return Collections.emptyList();
	}

	@Override
	public void borrar(String key) {
		
		try {
			template.opsForValue().getOperations().delete(key);
		} catch (Exception e) {
			log.errorMesg("Redis server error al borrar " + key + ". Message " + e.getMessage());
		}
	}

	@Override
	public Boolean validarConexion() {
		try {
			return template.getConnectionFactory().getConnection().ping() != null;
		} catch (Exception e) {
			log.errorMesg("Redis server no esta disponible al validarConexion. Message " + e.getMessage());
		}
		return false;
	}

}
