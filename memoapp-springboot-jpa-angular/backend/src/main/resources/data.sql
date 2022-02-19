
insert into ROLES (id, name) values (0, 'ADMIN');
insert into ROLES (id, name) values (1, 'USER');

-- Password is 'admin1234'
insert into USERS (loginId, encodedPassword, firstName, lastName) values ('admin', '$2a$10$xCyhExONZTBmi.Hsgj0bVuXHMjvWYuSITxlmtars1QHxxQ2idbxQi', null, null);

-- Password is 'john1234'
insert into USERS (loginId, encodedPassword, firstName, lastName) values ('john', '$2a$10$5Zr90uI6Vq42Qe.2o.eYA.RIg9ebIcCB58/X7wZKHBExfUfICJ/dy', 'John', 'Smith');

-- Password is 'fred1234'
insert into USERS (loginId, encodedPassword, firstName, lastName) values ('fred', '$2a$10$S1fL/y9B/uQII793x/VKtuAiSPEp8APRHoJ8M.kmI.0uyRHdzBMDe', 'Fred', 'Brown');


insert into USER_ROLE(USER_ID, ROLE_ID) values ('admin', 0);
insert into USER_ROLE(USER_ID, ROLE_ID) values ('admin', 1);


insert into USER_ROLE(USER_ID, ROLE_ID) values ('john', 1);
insert into USER_ROLE(USER_ID, ROLE_ID) values ('fred', 1);
