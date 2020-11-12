insert into cargo (cargo_name) values ('Manager');
insert into cargo (cargo_name) values ('Developer');
insert into cargo (cargo_name) values ('Tester');
insert into cargo (cargo_name) values ('Estagiário');
insert into cargo (cargo_name) values ('Analista Adm');
insert into cargo (cargo_name) values ('Supervisor');
insert into cargo (cargo_name) values ('Operador de Máquina');
insert into cargo (cargo_name) values ('Analista de Recrutamento');
insert into cargo (cargo_name) values ('Analista Financeiro');
insert into cargo (cargo_name) values ('Analista de Logística');

insert into funcionario (funcionario_name, funcionario_age, funcionario_birthday, funcionario_document, cargo_id) values ('João', 23, '1997-10-10', '98767623', 2);
insert into funcionario (funcionario_name, funcionario_age, funcionario_birthday, funcionario_document, cargo_id) values ('Carlos', 28, '1990-03-18', '92342623', 3);
insert into funcionario (funcionario_name, funcionario_age, funcionario_birthday, funcionario_document, cargo_id) values ('Lucas', 19, '2001-01-28', '98983423', 4);
insert into funcionario (funcionario_name, funcionario_age, funcionario_birthday, funcionario_document, cargo_id) values ('Pedro', 42, '1978-05-10', '9123323', 1);
insert into funcionario (funcionario_name, funcionario_age, funcionario_birthday, funcionario_document, cargo_id) values ('Eduardo', 38, '1982-01-17', '91762323', 5);
insert into funcionario (funcionario_name, funcionario_age, funcionario_birthday, funcionario_document, cargo_id) values ('Francisco', 36, '1984-05-20', '786323', 6);
insert into funcionario (funcionario_name, funcionario_age, funcionario_birthday, funcionario_document, cargo_id) values ('Junior', 31, '1989-08-13', '23786323', 7);
insert into funcionario (funcionario_name, funcionario_age, funcionario_birthday, funcionario_document, cargo_id) values ('Camila', 25, '1995-07-19', '5829632', 8);
insert into funcionario (funcionario_name, funcionario_age, funcionario_birthday, funcionario_document, cargo_id) values ('Magalhães', 47, '1973-12-11', '1623233', 9);
insert into funcionario (funcionario_name, funcionario_age, funcionario_birthday, funcionario_document, cargo_id) values ('Renato', 41, '1979-01-09', '9181212', 10);


insert into departamento (departamento_name, funcionario_id) values ('Projetos', 4);
insert into departamento (departamento_name, funcionario_id) values ('Logística', 5);
insert into departamento (departamento_name, funcionario_id) values ('Recursos Humanos', 8);
insert into departamento (departamento_name, funcionario_id) values ('Administração', 9);

insert into funcionario_departamento (departamento_id, funcionario_id) values (1, 1);
insert into funcionario_departamento (departamento_id, funcionario_id) values (1, 2);
insert into funcionario_departamento (departamento_id, funcionario_id) values (1, 3);
insert into funcionario_departamento (departamento_id, funcionario_id) values (1, 4);
insert into funcionario_departamento (departamento_id, funcionario_id) values (2, 5);
insert into funcionario_departamento (departamento_id, funcionario_id) values (2, 6);
insert into funcionario_departamento (departamento_id, funcionario_id) values (2, 7);
insert into funcionario_departamento (departamento_id, funcionario_id) values (3, 8);
insert into funcionario_departamento (departamento_id, funcionario_id) values (4, 9);
insert into funcionario_departamento (departamento_id, funcionario_id) values (2, 10);

insert into historico_funcionario (departamento_id, funcionario_id, data_inicio) values (1, 1, CURRENT_TIMESTAMP);
insert into historico_funcionario (departamento_id, funcionario_id, data_inicio) values (1, 2, CURRENT_TIMESTAMP);
insert into historico_funcionario (departamento_id, funcionario_id, data_inicio) values (1, 3, CURRENT_TIMESTAMP);
insert into historico_funcionario (departamento_id, funcionario_id, data_inicio) values (1, 4, CURRENT_TIMESTAMP);
insert into historico_funcionario (departamento_id, funcionario_id, data_inicio) values (2, 5, CURRENT_TIMESTAMP);
insert into historico_funcionario (departamento_id, funcionario_id, data_inicio) values (2, 6, CURRENT_TIMESTAMP);
insert into historico_funcionario (departamento_id, funcionario_id, data_inicio) values (2, 7, CURRENT_TIMESTAMP);
insert into historico_funcionario (departamento_id, funcionario_id, data_inicio) values (3, 8, CURRENT_TIMESTAMP);
insert into historico_funcionario (departamento_id, funcionario_id, data_inicio) values (4, 9, CURRENT_TIMESTAMP);
insert into historico_funcionario (departamento_id, funcionario_id, data_inicio) values (2, 10, CURRENT_TIMESTAMP);


