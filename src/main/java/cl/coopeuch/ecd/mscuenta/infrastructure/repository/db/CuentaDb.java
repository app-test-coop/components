package cl.coopeuch.ecd.mscuenta.infrastructure.repository.db;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import cl.coopeuch.ecd.infrastructurecross.util.metrica.IMetrica;
import cl.coopeuch.ecd.infrastructurecross.util.metrica.MetricaParam;
import cl.coopeuch.ecd.mscuenta.application.port.output.ICuentaDb;
import cl.coopeuch.ecd.mscuenta.domain.Cuenta;
import cl.coopeuch.ecd.mscuenta.domain.Tiempo;
import cl.coopeuch.ecd.mscuenta.infrastructurecross.application.Setting;

@Repository
public class CuentaDb implements ICuentaDb {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private IMetrica metricaRepository;
	
	@Autowired
	private Setting setting;	

	@Override
	public Cuenta crear(Cuenta cuenta) {

		LocalDateTime inicioProceso = LocalDateTime.now();

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO cuenta ");
		query.append("(numero, saldo, rut, bloqueado, fechacreacion, fechaactualizacion, vigente) ");
		query.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(query.toString(), new String[] { "id" });
			ps.setObject(1, cuenta.getNumero());
			ps.setObject(2, cuenta.getSaldo());
			ps.setObject(3, cuenta.getRut());
			ps.setObject(4, cuenta.getBloqueado());
			ps.setObject(5, cuenta.getFechaCreacion());
			ps.setObject(6, cuenta.getFechaActualizacion());
			ps.setObject(7, cuenta.getVigente());
			return ps;
		}, keyHolder);
		cuenta.setId(keyHolder.getKey().longValue());
		
		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"DB_CUENTA_CREAR", (double) ChronoUnit.MILLIS.between(inicioProceso, LocalDateTime.now()));
		metricaRepository.putTiempoRespuestaAsync(metricaParam);

		return cuenta;
	}

	@Override
	public Cuenta actualizar(Cuenta cuenta) {

		LocalDateTime inicioProceso = LocalDateTime.now();

		StringBuilder query = new StringBuilder();
		query.append("UPDATE cuenta ");
		query.append("SET saldo = ?, fechaactualizacion = ? ");
		query.append("WHERE numero = ? AND rut = ?");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(query.toString(), new String[] { "id" });
			ps.setObject(1, cuenta.getSaldo());
			ps.setObject(2, cuenta.getFechaActualizacion());
			ps.setObject(3, cuenta.getNumero());
			ps.setObject(4, cuenta.getRut());
			return ps;
		}, keyHolder);
		cuenta.setId(keyHolder.getKey().longValue());

		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"DB_CUENTA_ACTUALIZAR", (double) ChronoUnit.MILLIS.between(inicioProceso, LocalDateTime.now()));
		metricaRepository.putTiempoRespuestaAsync(metricaParam);		

		return cuenta;
	}

	@Override
	public Cuenta obtener(String rut, String numero) {

		LocalDateTime inicioProceso = LocalDateTime.now();

		StringBuilder query = new StringBuilder();
		query.append("SELECT id, numero, saldo, rut, bloqueado, fechaCreacion, fechaActualizacion, vigente ");
		query.append("FROM cuenta ");
		query.append("WHERE numero = ? AND rut = ?");

		Cuenta cuenta = DataAccessUtils.singleResult(
				jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<>(Cuenta.class), numero, rut));

		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"DB_CUENTA_OBTENER", (double) ChronoUnit.MILLIS.between(inicioProceso, LocalDateTime.now()));
		metricaRepository.putTiempoRespuestaAsync(metricaParam);			

		return cuenta;
	}

	@Override
	public String obtenerHora() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT now() as hora");
		
		Tiempo tiempo = DataAccessUtils.singleResult(
				jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<>(Tiempo.class)));		
		
		return tiempo.getHora();
	}
	
	

}
