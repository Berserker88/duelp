create database feedback;
use feedback;
CREATE USER sqluser IDENTIFIED BY 'sqluserpw'; 
grant usage on *.* to sqluser@localhost identified by 'sqluserpw'; 
grant all privileges on feedback.* to sqluser@localhost;

CREATE TABLE COMMENTS (id INT NOT NULL AUTO_INCREMENT, 
    MYUSER VARCHAR(30) NOT NULL,
    EMAIL VARCHAR(30), 
    WEBPAGE VARCHAR(100) NOT NULL, 
    DATUM DATE NOT NULL, 
    SUMMERY VARCHAR(40) NOT NULL,
    COMMENTS VARCHAR(400) NOT NULL,
    PRIMARY KEY (ID));

INSERT INTO COMMENTS values (default, 'lars', 'myemail@gmail.com','http://www.vogella.com', '2009-09-14 10:33:11', 'Summery','My first comment');