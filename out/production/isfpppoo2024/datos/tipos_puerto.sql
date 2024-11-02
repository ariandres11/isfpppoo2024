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

-- Tipos de Puertos
CREATE TABLE ari_tipos_puerto (
    codigo VARCHAR(10) PRIMARY KEY,
    descripcion VARCHAR(50),
    velocidad numeric(20)
);

ALTER TABLE ONLY ari_tipos_puerto
    ADD CONSTRAINT ari_tipos_puerto_pkey PRIMARY KEY (codigo);
