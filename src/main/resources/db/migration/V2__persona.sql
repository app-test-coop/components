CREATE TABLE IF NOT exists persona (
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

insert into persona (numero, saldo, rut, bloqueado, fechacreacion, fechaactualizacion, vigente)
values('000001', 0, '107574042',false, now(), null, true);



