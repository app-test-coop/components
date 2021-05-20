package cl.coopeuch.ecd.mscuenta.application.dto.in;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;


@Getter
@Setter
public class CuentaCreacion {

    @NotBlank
    @NotNull
    @Size(min=5, max=30)    
    private String numero;
        
	@JsonProperty
    @NotNull
    private Double saldo;
   
    @NotNull
    private Boolean bloqueado;    
    
    @NotNull
    private Boolean vigente;
}
