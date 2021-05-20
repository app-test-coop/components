package cl.coopeuch.ecd.mscuenta.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

@Getter
@Setter
public class Cuenta {	
    private Long id;
    private String numero;
    private Double saldo;
    private String rut;
    private Boolean bloqueado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private Boolean vigente;
        
    protected void realizarCargoDinero(Double monto) {
    	if(Boolean.TRUE.equals(bloqueado)) {    		    		
    		throw new DomainException("No es posible cargar dinero, porque esta bloqueada la cuenta N " + numero, "ARQ-01", HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
    	if(monto == 0) {
    		throw new DomainException("No es posible realizar un cargo por un monto de 0 pesos en la cuenta N " + numero, "ARQ-02", HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
    	if(saldo < monto) {
    		throw new DomainException("No es posible realizar un cargo, porque no hay saldo suficiente en la cuenta N " + numero, "ARQ-03", HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
    	saldo = saldo - monto;
    }
        
    protected void realizarAbonoDinero(Double monto) {
    	if(Boolean.TRUE.equals(bloqueado)) {
    		throw new DomainException("No es posible abonar dinero, porque esta bloqueada la cuenta N " + numero, "ARQ-04", HttpStatus.INTERNAL_SERVER_ERROR);
    	} 
    	
    	if(monto <= 0) {
    		throw new DomainException("El monto que se abona debe ser mayor a 0 en la cuenta N " + numero, "ARQ-05", HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
    	saldo = saldo + monto;    	    	
    }
    
    protected void validarCreacion() {
    	if(saldo != 0) {
    		throw new DomainException("La cuenta debe nacer con saldo 0 ", "ARQ-06", HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
    
}
