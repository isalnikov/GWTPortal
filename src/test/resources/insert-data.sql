INSERT INTO Users (enabled, login, password) 
	VALUES (true, 'admin', 'admin');
INSERT INTO Users (enabled, login, password) 
	VALUES (true, 'user', 'user');

INSERT INTO Roles (rolename) 
	VALUES ('ROLE_ADMIN');
INSERT INTO Roles (rolename) 
	VALUES ('ROLE_USER');

