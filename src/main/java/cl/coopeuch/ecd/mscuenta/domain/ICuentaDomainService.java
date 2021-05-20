package cl.coopeuch.ecd.mscuenta.domain;

public interface ICuentaDomainService {
	void realizarCargoDinero(Cuenta cuenta, Double monto);
	void realizarAbonoDinero(Cuenta cuenta, Double monto);
	void validarCreacion(Cuenta cuenta);
}
