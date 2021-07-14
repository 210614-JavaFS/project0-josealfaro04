DROP TABLE IF EXISTS account CASCADE;

DROP TABLE IF EXISTS profile CASCADE;

CREATE TABLE account(
	id SERIAL PRIMARY KEY, 
	username VARCHAR(20) UNIQUE NOT NULL,
	pass_word VARCHAR(20) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	balance NUMERIC(19,2) CHECK(balance > 0),
	acc_level INTEGER NOT NULL,
	does_exist BOOLEAN
);
INSERT INTO account (username, pass_word, first_name , last_name, acc_level, does_exist)
VALUES('employee','employee', 'john','smith','2',TRUE),
	('admin10','admin10', 'bob','will','3',TRUE);


CREATE TABLE profile(
	username VARCHAR(20) REFERENCES account(username),
	pass_word VARCHAR(20) NOT NULL,
	first_name VARCHAR(20)NOT NULL,
	last_name VARCHAR(20)NOT NULL ,
	email VARCHAR(30)NOT NULL ,
	address VARCHAR(50)NOT NULL ,
	city VARCHAR(20) NOT NULL,
	state VARCHAR(20)NOT NULL ,
	zipcode VARCHAR(10) 
);

INSERT INTO profile (username,pass_word,first_name,last_name,email,address,city,state,zipcode)
VALUES ('employee','employee','john','smith','js','lol','kc','ca','1234'),
		('admin10','employee','bob','will','sa','qw','we','ca','1234');

