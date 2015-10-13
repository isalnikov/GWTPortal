INSERT INTO Users (enabled, login, password) 
	VALUES (true, 'admin', 'admin');
INSERT INTO Users (enabled, login, password) 
	VALUES (true, 'user', 'user');

INSERT INTO Roles (rolename) 
	VALUES ('ROLE_ADMIN');
INSERT INTO Roles (rolename) 
	VALUES ('ROLE_USER');


INSERT INTO user_roles (user_id, role_id) 
	VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) 
	VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) 
	VALUES (2, 2);

