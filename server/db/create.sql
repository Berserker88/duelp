/*DUELP Datenbank*/
/*C:\Users\forall\Desktop\duelp\duelp\server\db\create.sql*/
BEGIN;


/*CLEAN UP*/
DROP TABLE users;
DROP TABLE users_faecher;
DROP TABLE faecher;
DROP TABLE users_termine;
DROP TABLE klausuren;
DROP TABLE termine;
DROP TABLE termine_orte;
DROP TABLE orte;


/*CREATE*/

/*user*/
CREATE TABLE users(id INT NOT NULL AUTO_INCREMENT,name VARCHAR(20),password VARCHAR(20),PRIMARY KEY(id));

/*user_faecher*/
CREATE TABLE users_faecher(id INT NOT NULL AUTO_INCREMENT,user_id INT,fach_id INT, gewichtung INT,PRIMARY KEY(id));

/*faecher*/
CREATE TABLE faecher (id INT NOT NULL AUTO_INCREMENT,name VARCHAR(20),PRIMARY KEY(id));

/*user_termine*/
CREATE TABLE users_termine (id INT NOT NULL AUTO_INCREMENT,user_id INT,termin_id INT,PRIMARY KEY(id));

/*klausuren*/
CREATE TABLE klausuren (id INT NOT NULL AUTO_INCREMENT,termin_id INT,fach_id INT,PRIMARY KEY(id));

/*termine*/
CREATE TABLE termine (id INT NOT NULL AUTO_INCREMENT,datum DATE,PRIMARY KEY(id));

/*termine_orte*/
CREATE TABLE termine_orte (id INT NOT NULL AUTO_INCREMENT,termin_id INT,ort_id INT,PRIMARY KEY(id));

/*orte*/
CREATE TABLE orte (id INT NOT NULL AUTO_INCREMENT,user_id INT,plz CHAR(5),ort VARCHAR(20),strasse_hnr VARCHAR(20),latitude DOUBLE,longitude DOUBLE,PRIMARY KEY(id));


/*TEST DATA*/

INSERT INTO users (name ,password) VALUES('peter','dermeter');


COMMIT;

