package cl.coopeuch.ecd.mscuenta.application.dto.in;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;


@Getter
@Setter
public class ClienteNuevo {

    @NotBlank
    @NotNull
    @Size(min=5, max=30)
    private String rut;

    @JsonProperty
    @NotNull
    private String email;

    @NotNull
    private String telefono;

}
