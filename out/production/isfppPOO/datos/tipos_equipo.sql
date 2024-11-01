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
CREATE TABLE ari_tipos_equipo(
    codigo VARCHAR(10) PRIMARY KEY,
    descripcion VARCHAR(50)
);

ALTER TABLE ONLY ari_tipos_equipo
    ADD CONSTRAINT ari_tipos_equipo_pkey PRIMARY KEY (codigo);