package cl.coopeuch.ecd.mscuenta.application.port.input;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.postgresql.util.PSQLException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import cl.coopeuch.ecd.application.dto.cross.ErrorResponse;
import cl.coopeuch.ecd.mscuenta.application.ApplicationException;

import cl.coopeuch.ecd.mscuenta.domain.DomainException;
import cl.coopeuch.ecd.mscuenta.infrastructure.agent.AgentException;

public interface IErrorHandlerController {

	ResponseEntity<ErrorResponse> methodArgumentNotValidException(HttpServletRequest request,
			MethodArgumentNotValidException e);

	ResponseEntity<ErrorResponse> exceptionPSQLHandler(HttpServletRequest request, PSQLException e);

	ResponseEntity<ErrorResponse> sqlExceptionHandler(HttpServletRequest request, SQLException e);
	
	ResponseEntity<ErrorResponse> exceptionApplicationHandler(HttpServletRequest request,
			MissingServletRequestParameterException e);

	ResponseEntity<ErrorResponse> nullPointerException(HttpServletRequest request, NullPointerException e);

	ResponseEntity<ErrorResponse> redisConnectionException(HttpServletRequest request,
			RedisConnectionFailureException e);

	ResponseEntity<ErrorResponse> exceptionHandler(HttpServletRequest request, Exception e);

	ResponseEntity<ErrorResponse> domainException(HttpServletRequest request, DomainException e);

	ResponseEntity<ErrorResponse> applicationException(HttpServletRequest request, ApplicationException e);
	
	ResponseEntity<ErrorResponse> agentException(HttpServletRequest request, AgentException e);
}
