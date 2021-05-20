package cl.coopeuch.ecd.mscuenta.application.dto.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaResumen {	
	
	private String rut;     
	private String numero;    
    private Double saldo;
    private Boolean bloqueado;    
}