-- Crear tabla persona
CREATE TABLE persona (
    id_persona BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    identificacion VARCHAR(50) UNIQUE NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    edad INT,
    genero VARCHAR(10)
);

-- Crear tabla cliente
CREATE TABLE cliente (
    id_cliente BIGSERIAL PRIMARY KEY,
    id_persona BIGINT NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_cliente_persona FOREIGN KEY (id_persona) REFERENCES persona(id_persona)
);

-- Crear tabla cuenta
CREATE TABLE cuenta (
    id_cuenta BIGSERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(50) UNIQUE NOT NULL,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo_inicial NUMERIC(15,2) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    id_cliente BIGINT NOT NULL,
    CONSTRAINT fk_cuenta_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

-- Crear tabla movimiento
CREATE TABLE movimiento (
    id_movimiento BIGSERIAL PRIMARY KEY,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor NUMERIC(15,2) NOT NULL,
    saldo NUMERIC(15,2) NOT NULL,
    id_cuenta BIGINT NOT NULL,
    CONSTRAINT fk_movimiento_cuenta FOREIGN KEY (id_cuenta) REFERENCES cuenta(id_cuenta)
);




-- Personas
INSERT INTO persona (nombre, genero, edad, identificacion, direccion, telefono)
VALUES
    ('Jose Lema', 'M', 32, '1728392839', 'Otavalo s/n y principal', '0985245785'),
    ('Marianela Montalvo', 'F', 28, '1827364534', 'Amazonas y NNUU', '097548905'),
    ('Juan Osorio', 'M', 40, '1928374655', '13 junio y Equinoccial', '098874987');

-- Clientes
INSERT INTO cliente (id_persona, contrasena, estado) VALUES
                                                         (1, '1234', TRUE),
                                                         (2, '5678', TRUE),
                                                         (3, '1245', TRUE);

-- Cuentas
INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado, id_cliente) VALUES
                                                                                       ('478758', 'Ahorros', 2000, TRUE, 1),
                                                                                       ('223487', 'Corriente', 100, TRUE, 2),
                                                                                       ('493878', 'Ahorros', 0, TRUE, 3),
                                                                                       ('496825', 'Ahorros', 540, TRUE, 2);


ALTER TABLE cliente ALTER COLUMN id_cliente TYPE BIGINT;
ALTER SEQUENCE cliente_id_cliente_seq AS BIGINT;

-- ya hiciste cliente.id_cliente y cliente.id_persona a BIGINT

-- CUENTA
ALTER TABLE cuenta ALTER COLUMN id_cuenta TYPE BIGINT;
ALTER TABLE cuenta ALTER COLUMN id_cliente TYPE BIGINT;

-- MOVIMIENTO
ALTER TABLE movimiento ALTER COLUMN id_movimiento TYPE BIGINT;
ALTER TABLE movimiento ALTER COLUMN id_cuenta TYPE BIGINT;

-- (opcional) asegurar ownership de secuencias
ALTER SEQUENCE IF EXISTS cuenta_id_cuenta_seq OWNED BY cuenta.id_cuenta;
ALTER SEQUENCE IF EXISTS movimiento_id_movimiento_seq OWNED BY movimiento.id_movimiento;
