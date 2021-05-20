package cl.coopeuch.ecd.mscuenta.domain;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DomainException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = -7405432357544406448L;
    
    private final HttpStatus httpStatus;  
    private final String codigoError;

    public DomainException(String errorMessage,String codigoError, HttpStatus httpStatus){
        super(errorMessage);
        this.codigoError = codigoError;
        this.httpStatus = httpStatus;
    }    
        
    
    
    
}