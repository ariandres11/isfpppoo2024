
-- Cantidades y tipos de puerto deben ser arrays igual que lista de ips
- Tabla de Equipos

CREATE TABLE ari_equipos (
    codigo VARCHAR(10) NOT NULL PRIMARY KEY,
    descripcion VARCHAR(50),
    marca VARCHAR(50),
    modelo VARCHAR(50),
    ubicacion VARCHAR(50) REFERENCES ari_ubicaciones(codigo),
    tipo_equipo VARCHAR(10) REFERENCES ari_tipos_equipo(codigo), -- Clave foránea a tipo_equipo
    estado BOOLEAN NOT NULL
);
