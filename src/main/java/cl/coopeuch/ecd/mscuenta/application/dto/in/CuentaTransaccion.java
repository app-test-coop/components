package cl.coopeuch.ecd.mscuenta.application.dto.in;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaTransaccion {
    
    @NotBlank
    @NotNull
    @Size(min=5, max=50)
	private String numero;
    
    @NotNull
    private Double monto;	
}