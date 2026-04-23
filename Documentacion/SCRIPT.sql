CREATE DATABASE connectWork;
use connectWork;


CREATE TABLE tabla_configuracion(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	porcentaje_comision INTEGER NOT NULL
);

create table rol(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(20) NOT NULL
);

CREATE TABLE usuario_sistema(
	nickname VARCHAR(30) PRIMARY KEY NOT NULL,
	activo BOOLEAN NOT NULL DEFAULT TRUE,
	contraseña VARCHAR(700) NOT NULL,
	id_rol INTEGER NOT NULL,
	CONSTRAINT fk_rol FOREIGN KEY (id_rol) REFERENCES rol(id) ON DELETE CASCADE
);

CREATE TABLE usuario_plataforma(
	nickname VARCHAR(30) NOT NULL,
	nombre VARCHAR(80) NOT NULL,
	cui    VARCHAR(50) PRIMARY KEY NOT NULL,
	correo VARCHAR(100) UNIQUE NOT NULL,
	telefono VARCHAR(20) UNIQUE NOT NULL,
	perfil_completado BOOLEAN NOT NULL DEFAULT FALSE,
	fecha_nacimiento DATE NOT NULL,
	direccion VARCHAR(300) NOT NULL,
	CONSTRAINT fk_nickname FOREIGN KEY (nickname) REFERENCES usuario_sistema(nickname) ON DELETE CASCADE
);

CREATE TABLE perfil_cliente(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	cui_usuario VARCHAR(50) NOT NULL,
	descripcion VARCHAR(400) NOT NULL,
	sitio_web VARCHAR(500),
	industria VARCHAR(100) NOT NULL,
	CONSTRAINT fk_cui_cliente FOREIGN KEY (cui_usuario) REFERENCES usuario_plataforma(cui) ON DELETE CASCADE
);

CREATE TABLE nivel_experiencia(
	 id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	 nombre VARCHAR(20) NOT NULL
);

create table perfil_freelancer(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	cui_freelancer VARCHAR(50) NOT NULL,
	biografia VARCHAR(300) NOT NULL,
	tarifa_hora DECIMAL(10,2) NOT NULL,
	id_nivel_experiencia INTEGER NOT NULL,
	CONSTRAINT fk_cui_freelancer FOREIGN KEY (cui_freelancer) REFERENCES usuario_plataforma(cui) ON DELETE CASCADE,
	CONSTRAINT fk_nivel_freelancer FOREIGN KEY (id_nivel_experiencia) REFERENCES nivel_experiencia(id) ON DELETE CASCADE
);

CREATE TABLE calificacion_freelancer(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	cui_freelancer VARCHAR(50) NOT NULL,
	cantidad_estrellas INTEGER NOT NULL,
	fecha_calificacion DATE NOT NULL,
	comentario VARCHAR(50) NOT NULL,
	CONSTRAINT fk_cui_calificaicon FOREIGN KEY (cui_freelancer) REFERENCES usuario_plataforma(cui) ON DELETE CASCADE
);


CREATE TABLE cartera_virtual(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	cui_cliente VARCHAR(50) NOT NULL,
	saldo DECIMAL(10,2) NOT NULL DEFAULT 0,
	CONSTRAINT fk_cui_tarjeta FOREIGN KEY (cui_cliente) REFERENCES usuario_plataforma(cui) ON DELETE CASCADE
);

CREATE TABLE recargo_tarjeta(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	cui_cliente VARCHAR(50) NOT NULL,
	monto DECIMAL(10,2) NOT NULL,
	fecha DATE NOT NULL,
	CONSTRAINT fk_cui_recargo FOREIGN KEY (cui_cliente) REFERENCES usuario_plataforma(cui) ON DELETE CASCADE
);

CREATE TABLE notificacion_freelancer(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	descripcion VARCHAR(200) NOT NULL,
	cui_freelancer VARCHAR(50) NOT NULL,
	fecha DATE NOT NULL,
	CONSTRAINT fk_cui_freelancer_notifcacion FOREIGN KEY (cui_freelancer) REFERENCES usuario_plataforma(cui) ON DELETE CASCADE
);


CREATE TABLE habilidad(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	activa BOOLEAN NOT NULL DEFAULT TRUE,
	nombre VARCHAR(40) NOT NULL
);

CREATE TABLE categoria(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	activa BOOLEAN NOT NULL DEFAULT TRUE,
	nombre VARCHAR(40) NOT NULL
);

CREATE TABLE habilidad_freelancer(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_habilidad INTEGER NOT NULL,
	cui_freelancer VARCHAR(50) NOT NULL,
	CONSTRAINT fk_cui_habilidad FOREIGN KEY (cui_freelancer) REFERENCES usuario_plataforma(cui) ON DELETE CASCADE,
	CONSTRAINT fk_habilidad_freelancer FOREIGN KEY (id_habilidad) REFERENCES habilidad(id) ON DELETE CASCADE
);

CREATE TABLE habilidad_categoria(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_habilidad INTEGER NOT NULL,
	CONSTRAINT fk_habilidad_categoria FOREIGN KEY (id_habilidad) REFERENCES  habilidad(id) ON DELETE CASCADE
);

CREATE TABLE estado_solicitud(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(15) NOT NULL
);

CREATE TABLE solicitud_habilidad(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	descripcion VARCHAR(50) NOT NULL,
	id_estado INTEGER NOT NULL,
	fecha DATE NOT NULL,
	cui_freelancer VARCHAR(50) NOT NULL,
	CONSTRAINT fk_cui_s_habilidad FOREIGN KEY (cui_freelancer) REFERENCES usuario_plataforma(cui) ON DELETE CASCADE,
	CONSTRAINT fk_estado_s_habilidad FOREIGN KEY (id_estado) REFERENCES estado_solicitud (id) ON DELETE CASCADE
);

CREATE TABLE solicitud_categoria(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	descripcion VARCHAR(50) NOT NULL,
	id_estado INTEGER NOT NULL,
	fecha DATE NOT NULL,
	cui_freelancer VARCHAR(50) NOT NULL,
	CONSTRAINT fk_cui_s_categoria FOREIGN KEY (cui_freelancer) REFERENCES usuario_plataforma(cui) ON DELETE CASCADE,
	CONSTRAINT fk_estado_s_categoria FOREIGN KEY (id_estado) REFERENCES estado_solicitud (id) ON DELETE CASCADE
);

CREATE TABLE estado_proyecto(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(15)  NOT NULL
);

CREATE TABLE proyecto(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	titulo VARCHAR(100) NOT NULL,
	descripcion VARCHAR(300) NOT NULL,
	presupuesto_maximo DECIMAL(10,2) NOT NULL,
	fecha_publicacion DATE NOT NULL,
	fecha_entrega_deseada DATE NOT NULL,
	cui_cliente VARCHAR(50) NOT NULL,
	id_estado INTEGER NOT NULL,
	id_categoria INTEGER NOT NULL,
	CONSTRAINT fk_cui_proyecto FOREIGN KEY (cui_cliente) REFERENCES usuario_plataforma(cui) ON DELETE CASCADE,
	CONSTRAINT fk_id_categoria_proyecto FOREIGN KEY (id_categoria) REFERENCES categoria(id) ON DELETE CASCADE,
	CONSTRAINT fk_id_estado_proyecto FOREIGN KEY (id_estado) REFERENCES estado_proyecto(id) ON DELETE CASCADE
);

CREATE TABLE cancelacion_proyecto(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	motivo VARCHAR(200)  NOT NULL,
	id_proyecto INTEGER NOT NULL,
	CONSTRAINT fk_id_proyecto_cancelacion FOREIGN KEY (id_proyecto) REFERENCES proyecto(id) ON DELETE CASCADE
);


CREATE TABLE estado_propuesta(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(15) NOT NULL
);

CREATE TABLE propuesta_proyecto(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	plazo_entrega INTEGER NOT NULL,
	presupuesto_ofertado DECIMAL(10,2) NOT NULL,
	carta_presentacion VARCHAR(500) NOT NULL,
	fecha_creacion DATE NOT NULL,
	id_proyecto INTEGER NOT NULL,
	id_estado INTEGER NOT NULL,
	cui_freelancer VARCHAR(50) NOT NULL,
	CONSTRAINT fk_id_proyecto_propuesta FOREIGN KEY (id_proyecto) REFERENCES proyecto(id) ON DELETE CASCADE,
	CONSTRAINT fk_cui_propuesta FOREIGN KEY (cui_freelancer) REFERENCES usuario_plataforma(cui) ON DELETE CASCADE,
	CONSTRAINT fk_id_estado_propuesta FOREIGN KEY (id_estado) REFERENCES estado_propuesta(id) ON DELETE CASCADE
);

CREATE TABLE contrato(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	fecha_entrega DATE NOT NULL,
	porcentaje_comision INTEGER NOT NULL,
	fecha_generacion DATE NOT NULL,
	id_proyecto INTEGER NOT NULL,
	cui_freelancer VARCHAR(50) NOT NULL,
	CONSTRAINT fk_id_proyecto_contrato FOREIGN KEY (id_proyecto) REFERENCES proyecto(id) ON DELETE CASCADE,
	CONSTRAINT fk_cui_contrato FOREIGN KEY (cui_freelancer) REFERENCES usuario_plataforma(cui) ON DELETE CASCADE
);

CREATE TABLE pago_proyecto(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	fecha_pago DATE NOT NULL,
	monto DECIMAL(10,2) NOT NULL,
	comision DECIMAL(10,2) NOT NULL,
	id_contrato INTEGER NOT NULL,
	cui_freelancer VARCHAR(50) NOT NULL,
	CONSTRAINT fk_id_contrato_pago FOREIGN KEY (id_contrato) REFERENCES contrato(id) ON DELETE CASCADE,
	CONSTRAINT fk_cui_freelancer_pago FOREIGN KEY (cui_freelancer) REFERENCES usuario_plataforma(cui) ON DELETE CASCADE
);

CREATE TABLE estado_entrega(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(15) NOT NULL
);

CREATE TABLE entrega(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	descripcion VARCHAR(300) NOT NULL,
	fecha DATE NOT NULL,
	id_proyecto INTEGER NOT NULL,
	id_estado INTEGER NOT NULL,
	CONSTRAINT fk_id_proyecto_entrega FOREIGN KEY (id_proyecto) REFERENCES proyecto(id) ON DELETE CASCADE,
	CONSTRAINT fk_id_estado_entrega FOREIGN KEY (id_estado) REFERENCES estado_entrega(id) ON DELETE CASCADE
);

CREATE TABLE rechazo_entrega(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	motivo VARCHAR(300) NOT NULL,
	fecha DATE NOT NULL,
	id_entrega INTEGER NOT NULL,
	CONSTRAINT fk_id_entrega_rechazo FOREIGN KEY (id_entrega) REFERENCES entrega(id) ON DELETE CASCADE
);

CREATE TABLE archivo_entrega(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_entrega INTEGER NOT NULL,
	archivo BLOB NOT NULL,
	CONSTRAINT fk_id_entrega_archivo FOREIGN KEY (id_entrega) REFERENCES entrega(id) ON DELETE CASCADE
);
