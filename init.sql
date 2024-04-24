CREATE SEQUENCE employees_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE Employees (
                           key BIGINT DEFAULT nextval('employees_key_seq') PRIMARY KEY,
                           name VARCHAR(255),
                           lastname VARCHAR(255),
                           address VARCHAR(255),
                           phone VARCHAR(20)
);