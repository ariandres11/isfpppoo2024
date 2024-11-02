SET schema 'poo2024';


SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

-- Tabla de tipos de equipo
CREATE TABLE ari_conexiones(
    codigo_equipo1 VARCHAR(10) REFERENCES ari_equipos(codigo),
    codigo_equipo2 VARCHAR(10) REFERENCES ari_equipos(codigo),
    tipo_cable VARCHAR(10) REFERENCES ari_tipos_cable(codigo),
    tipo_puerto_equipo1 VARCHAR(10) REFERENCES ari_tipos_puerto(codigo),
    tipo_puerto_equipo2 VARCHAR(10) REFERENCES ari_tipos_puerto(codigo),
    estado boolean
);

ALTER TABLE ONLY ari_conexiones
    ADD CONSTRAINT ari_conexiones_pkey PRIMARY KEY (codigo_equipo1, codigo_equipo2, tipo_cable, tipo_puerto_equipo1, tipo_puerto_equipo2);

ALTER TABLE ari_conexiones
    ADD CONSTRAINT ari_conexiones_codigo_equipo1_fkey FOREIGN KEY (codigo_equipo1) REFERENCES ari_equipos(codigo);

ALTER TABLE ari_conexiones
    ADD CONSTRAINT ari_conexiones_codigo_equipo2_fkey FOREIGN KEY (codigo_equipo2) REFERENCES ari_equipos(codigo);

ALTER TABLE ari_conexiones
    ADD CONSTRAINT ari_conexiones_tipo_cable_fkey FOREIGN KEY (tipo_cable) REFERENCES ari_tipos_cable(codigo);

ALTER TABLE ari_conexiones
    ADD CONSTRAINT ari_conexiones_tipo_puerto_equipo1_fkey FOREIGN KEY (tipo_puerto_equipo1) REFERENCES ari_tipos_puerto(codigo);

ALTER TABLE ari_conexiones
    ADD CONSTRAINT ari_conexiones_tipo_puerto_equipo2_fkey FOREIGN KEY (tipo_puerto_equipo2) REFERENCES ari_tipos_puerto(codigo);