package cl.coopeuch.ecd.mscuenta.infrastructure.agent;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AgentException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -7405432357544406448L;
    
    private final HttpStatus httpStatus;    
    private final String codigoError;

    public AgentException(String errorMessage,String codigoError, HttpStatus httpStatus){
        super(errorMessage);
        this.codigoError = codigoError;
        this.httpStatus = httpStatus;
    }    
    
}