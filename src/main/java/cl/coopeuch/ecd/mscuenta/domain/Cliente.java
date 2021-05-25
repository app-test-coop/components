package cl.coopeuch.ecd.mscuenta.domain;


import lombok.Getter;
        import lombok.Setter;

        import java.time.LocalDateTime;

        import org.springframework.http.HttpStatus;

@Getter
@Setter
public class Cliente {
    private String rut;
    private String email;
    private String telefono;
}