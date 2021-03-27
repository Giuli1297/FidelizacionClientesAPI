
INSERT INTO cliente
(cliente_id, fecha_de_nacimiento, numero_documento, tipo_documento, email, apellido, nombre, nacionalidad, telefono)
VALUES (nextval('hibernate_sequence'), '1997-02-17', 5662275, 'CID', 'giuli1297@gmail.com', 'gonzalez', 'giuli', 'Paraguaya', '+595971117026');

INSERT INTO cliente
(cliente_id, fecha_de_nacimiento, numero_documento, tipo_documento, email, apellido, nombre, nacionalidad, telefono)
VALUES (nextval('hibernate_sequence'), '1997-03-11', 5896245, 'CID', 'tato7@gmail.com', 'berni', 'tato', 'Paraguaya', '+59581248569');

INSERT INTO cliente
(cliente_id, fecha_de_nacimiento, numero_documento, tipo_documento, email, apellido, nombre, nacionalidad, telefono)
VALUES (nextval('hibernate_sequence'), '1964-05-12', 794232, 'CID', 'gonza@gmail.com', 'gonzalez', 'cesar', 'Paraguaya', '+595981243941');


INSERT INTO concepto_uso
(concepto_uso_id, descripcion, puntos_requeridos)
VALUES (nextval('hibernate_sequence'), 'Vale de compra por 100.000gs', 20);

INSERT INTO concepto_uso
(concepto_uso_id, descripcion, puntos_requeridos)
VALUES (nextval('hibernate_sequence'), 'Descuento del 7% en esta compra', 30);

INSERT INTO concepto_uso
(concepto_uso_id, descripcion, puntos_requeridos)
VALUES (nextval('hibernate_sequence'), 'Item A', 15);