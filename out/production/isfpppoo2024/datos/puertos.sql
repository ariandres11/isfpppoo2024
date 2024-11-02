

CREATE TABLE ari_equipos_puertos (
    equipo_codigo VARCHAR(10) REFERENCES ari_equipos(codigo),
    tipo_puerto_codigo VARCHAR(10) REFERENCES ari_tipos_puerto(codigo),
    cantidad_total INTEGER,
    PRIMARY KEY (equipo_codigo, tipo_puerto_codigo)
);