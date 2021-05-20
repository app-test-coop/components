package cl.coopeuch.ecd.mscuenta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication(scanBasePackages={"cl.coopeuch.ecd"})
public class MsCuentaApplication {	
	public static void main(String[] args) {
		SpringApplication.run(MsCuentaApplication.class, args);
	}

	
}
