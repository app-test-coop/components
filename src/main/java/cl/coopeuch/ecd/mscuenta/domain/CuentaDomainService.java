package cl.coopeuch.ecd.mscuenta.domain;

import org.springframework.stereotype.Service;

@Service
public class CuentaDomainService implements ICuentaDomainService {

	@Override
	public void realizarCargoDinero(Cuenta cuenta, Double monto) {
		cuenta.realizarCargoDinero(monto);		
	}

	@Override
	public void realizarAbonoDinero(Cuenta cuenta, Double monto) {
		cuenta.realizarAbonoDinero(monto);		
	}

	@Override
	public void validarCreacion(Cuenta cuenta) {
		cuenta.validarCreacion();		
	}

}
