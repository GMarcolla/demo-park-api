insert into USUARIOS (id, username, password, role) values (100, 'ana@gmail.com', '$2a$12$pnVT6i1IxZRqUQ2EwPuI4eG3v2kMm94uQYPyL30UOfLIxWiF1c3sW', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role) values (101, 'bia@gmail.com', '$2a$12$pnVT6i1IxZRqUQ2EwPuI4eG3v2kMm94uQYPyL30UOfLIxWiF1c3sW', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (102, 'bob@gmail.com', '$2a$12$pnVT6i1IxZRqUQ2EwPuI4eG3v2kMm94uQYPyL30UOfLIxWiF1c3sW', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (103, 'toby@gmail.com', '$2a$12$pnVT6i1IxZRqUQ2EwPuI4eG3v2kMm94uQYPyL30UOfLIxWiF1c3sW', 'ROLE_CLIENTE');


insert into CLIENTES (id, nome, cpf, id_usuario) values (10, 'Bianca Silva', '67527423080', 101);
insert into CLIENTES (id, nome, cpf, id_usuario) values (20, 'Roberto Gomes', '91305613023', 102);