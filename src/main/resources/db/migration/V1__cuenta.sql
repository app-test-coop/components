CREATE TABLE IF NOT exists cuenta (
	id BIGSERIAL NOT NULL,
	numero VARCHAR(50) UNIQUE NOT NULL,
	saldo DECIMAL NOT NULL,
	rut VARCHAR(13) NOT NULL,
	bloqueado BOOL NOT NULL,
	fechacreacion TIMESTAMP NOT NULL,	
	fechaactualizacion TIMESTAMP NULL,
	vigente BOOL NOT NULL,
	PRIMARY KEY (id)
);

insert into cuenta (numero, saldo, rut, bloqueado, fechacreacion, fechaactualizacion, vigente)
values('000001', 0, '107574042',false, now(), null, true);
insert into cuenta (numero, saldo, rut, bloqueado, fechacreacion, fechaactualizacion, vigente)
values('000002', 0, '20000123',true, now(), null, true);

