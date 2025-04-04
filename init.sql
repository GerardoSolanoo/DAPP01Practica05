CREATE USER jinwoo;
ALTER USER jinwoo WITH PASSWORD 'arise';
CREATE DATABASE ahjin;
GRANT ALL PRIVILEGES ON DATABASE ahjin TO jinwoo;
\connect ahjin;

CREATE SEQUENCE clientes_clave_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE clientes
(
    clave integer NOT NULL DEFAULT nextval('clientes_clave_seq'::regclass),
    nombre character varying(255),
    CONSTRAINT clientes_pkey PRIMARY KEY (clave)
);

CREATE SEQUENCE productos_clave_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE productos
(
    clave integer NOT NULL DEFAULT nextval('productos_clave_seq'::regclass),
    nombre character varying(255),
    precio double precision,
    CONSTRAINT productos_pkey PRIMARY KEY (clave)
);

CREATE SEQUENCE ventas_clave_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE ventas
(
    clave integer NOT NULL DEFAULT nextval('ventas_clave_seq'::regclass),
    fecha date,
    monto numeric(10,2),
    cliente integer,
    CONSTRAINT ventas_pkey PRIMARY KEY (clave),
    CONSTRAINT fk_ventas_cliente FOREIGN KEY (cliente)
        REFERENCES clientes (clave) MATCH SIMPLE
);

CREATE SEQUENCE detalle_venta_idrow_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE detalle_venta
(
    idrow integer NOT NULL DEFAULT nextval('detalle_venta_idrow_seq'::regclass),
    idventa integer,
    producto integer,
    precio double precision NOT NULL,
    cantidad double precision NOT NULL,
    descripcion character varying(255),
    CONSTRAINT detalle_venta_pkey PRIMARY KEY (idrow),
    CONSTRAINT fk_detalle_venta_venta FOREIGN KEY (idventa)
        REFERENCES ventas (clave) MATCH SIMPLE,
    CONSTRAINT fk_detalle_venta_producto FOREIGN KEY (producto)
        REFERENCES productos (clave) MATCH SIMPLE
);

INSERT INTO clientes (nombre) VALUES
                                  ('Juan Pérez'),
                                  ('María González'),
                                  ('Carlos Rodríguez');

INSERT INTO productos (nombre, precio) VALUES
                                           ('Laptop HP', 12999.99),
                                           ('Smartphone Samsung', 9999.99),
                                           ('Monitor LG', 3499.99),
                                           ('Teclado Logitech', 899.99),
                                           ('Mouse inalámbrico', 349.99);

INSERT INTO ventas (fecha, monto, cliente) VALUES
                                               (CURRENT_DATE, 13349.98, 1),
                                               (CURRENT_DATE, 9999.99, 2),
                                               (CURRENT_DATE, 4399.98, 3);

INSERT INTO detalle_venta (idventa, producto, precio, cantidad, descripcion) VALUES
                                                                                 (1, 1, 12999.99, 1, 'Laptop HP Pavilion 15"'),
                                                                                 (1, 4, 899.99, 1, 'Teclado mecánico gaming'),
                                                                                 (2, 2, 9999.99, 1, 'Samsung Galaxy S21'),
                                                                                 (3, 3, 3499.99, 1, 'Monitor LG 24" Full HD'),
                                                                                 (3, 5, 349.99, 2, 'Mouse inalámbrico ergonómico');

CREATE INDEX idx_ventas_cliente ON ventas(cliente);
CREATE INDEX idx_detalle_producto ON detalle_venta(producto);
CREATE INDEX idx_detalle_venta ON detalle_venta(idventa);