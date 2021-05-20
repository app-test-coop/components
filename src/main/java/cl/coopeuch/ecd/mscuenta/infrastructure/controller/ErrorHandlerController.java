package cl.coopeuch.ecd.mscuenta.infrastructure.controller;

import cl.coopeuch.ecd.application.dto.cross.ErrorInfo;
import cl.coopeuch.ecd.application.dto.cross.ErrorResponse;
import cl.coopeuch.ecd.infrastructurecross.util.logging.ILogging;
import cl.coopeuch.ecd.infrastructurecross.util.metrica.IMetrica;
import cl.coopeuch.ecd.infrastructurecross.util.metrica.MetricaParam;
import cl.coopeuch.ecd.mscuenta.application.ApplicationException;
import cl.coopeuch.ecd.mscuenta.application.port.input.IErrorHandlerController;
import cl.coopeuch.ecd.mscuenta.domain.DomainException;
import cl.coopeuch.ecd.mscuenta.infrastructure.agent.AgentException;
import cl.coopeuch.ecd.mscuenta.infrastructurecross.application.Setting;


import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorHandlerController implements IErrorHandlerController {

	@Autowired
	private ILogging log;

	@Autowired
	private IMetrica metricaRepository;

	@Autowired
	private Setting setting;

	private ErrorResponse obtenerError(HttpStatus httpStatus, String errorMessage, String errorCode, 
			String requestUri) {
		ErrorResponse error = new ErrorResponse();
		ErrorInfo errorInfo = new ErrorInfo(httpStatus.value(), errorMessage, errorCode, requestUri);
		error.setError(errorInfo);
		return error;
	}

	@Override
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodArgumentNotValidException(HttpServletRequest request,
			MethodArgumentNotValidException e) {

		BindingResult result = e.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		StringBuilder errorMessage = new StringBuilder();
		fieldErrors.forEach(f -> errorMessage.append(f.getField() + " " + f.getDefaultMessage() + " | "));

		ErrorResponse error = obtenerError(HttpStatus.BAD_REQUEST, errorMessage.toString(),
				HttpStatus.BAD_REQUEST.toString(), request.getRequestURI());

		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"ERROR_MethodArgumentNotValidException");
		metricaRepository.putErrorAsync(metricaParam);

		log.error(e);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@Override
	@ExceptionHandler(PSQLException.class)
	public ResponseEntity<ErrorResponse> exceptionPSQLHandler(HttpServletRequest request, PSQLException e) {
		ErrorResponse error = obtenerError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.toString(), request.getRequestURI());

		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"ERROR_PSQLException");
		metricaRepository.putErrorAsync(metricaParam);

		log.error(e);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ErrorResponse> sqlExceptionHandler(HttpServletRequest request, SQLException e) {
		ErrorResponse error = obtenerError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.toString(), request.getRequestURI());

		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"ERROR_SQLException");
		metricaRepository.putErrorAsync(metricaParam);

		log.error(e);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
		
	@Override
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> exceptionApplicationHandler(HttpServletRequest request,
			MissingServletRequestParameterException e) {
		ErrorResponse error = obtenerError(HttpStatus.BAD_REQUEST, e.getMessage(), HttpStatus.BAD_REQUEST.toString(),
				request.getRequestURI());

		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"ERROR_MissingServletRequestParameterException");
		metricaRepository.putErrorAsync(metricaParam);

		log.error(e);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@Override
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponse> nullPointerException(HttpServletRequest request, NullPointerException e) {
		ErrorResponse error = obtenerError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.toString(), request.getRequestURI());

		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"ERROR_NullPointerException");
		metricaRepository.putErrorAsync(metricaParam);

		log.error(e);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	@ExceptionHandler(RedisConnectionFailureException.class)
	public ResponseEntity<ErrorResponse> redisConnectionException(HttpServletRequest request,
			RedisConnectionFailureException e) {
		ErrorResponse error = obtenerError(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage(),
				HttpStatus.SERVICE_UNAVAILABLE.toString(), request.getRequestURI());

		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"ERROR_RedisConnectionFailureException");
		metricaRepository.putErrorAsync(metricaParam);

		log.error(e);
		return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(HttpServletRequest request, Exception e) {
		ErrorResponse error = obtenerError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.toString(), request.getRequestURI());

		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(), "ERROR_Exception");
		metricaRepository.putErrorAsync(metricaParam);

		log.error(e);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<ErrorResponse> domainException(HttpServletRequest request, DomainException e) {
		ErrorResponse error = obtenerError(e.getHttpStatus(), e.getMessage(), e.getCodigoError(),
				request.getRequestURI());

		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"ERROR_DomainException");
		metricaRepository.putErrorAsync(metricaParam);

		log.error(e);
		return new ResponseEntity<>(error, e.getHttpStatus());
	}

	@Override
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ErrorResponse> applicationException(HttpServletRequest request, ApplicationException e) {
		ErrorResponse error = obtenerError(e.getHttpStatus(), e.getMessage(), e.getCodigoError(),
				request.getRequestURI());

		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"ERROR_ApplicationException");
		metricaRepository.putErrorAsync(metricaParam);

		log.error(e);
		return new ResponseEntity<>(error, e.getHttpStatus());
	}

	
	@Override
	@ExceptionHandler(AgentException.class)
	public ResponseEntity<ErrorResponse> agentException(HttpServletRequest request, AgentException e) {
		ErrorResponse error = obtenerError(e.getHttpStatus(), e.getMessage(), e.getCodigoError(),
				request.getRequestURI());

		MetricaParam metricaParam = new MetricaParam(setting.getProjectName(), setting.getAppName(),
				"ERROR_AgentException");
		metricaRepository.putErrorAsync(metricaParam);

		log.error(e);
		return new ResponseEntity<>(error, e.getHttpStatus());
	}	
	
	
	
}
