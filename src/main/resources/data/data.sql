-- PostGreSQL - Initial Datasetup
-- Create table

CREATE SEQUENCE SC_SQ START 1;

CREATE SEQUENCE PROP_SQ START 1;

CREATE SEQUENCE RESIDENT_SQ START 1;

CREATE SEQUENCE CHILD_SQ START 1;

create table SERVICE_COORDINATOR (
	SC_ID				INT PRIMARY KEY NOT NULL,
	USER_NAME       	VARCHAR(36) NOT NULL UNIQUE,
	ENCRYPTED_PASSWORD 	VARCHAR(128) NOT NULL,
	ACTIVE          	BOOLEAN DEFAULT FALSE,
	EMAIL		   	 	VARCHAR (128) UNIQUE,
	CREATED_ON	    	TIMESTAMP DEFAULT NOW(),
	LAST_LOGIN	    	TIMESTAMP 
);

create table PROPERTY (
	PROP_ID			INT PRIMARY KEY NOT NULL,
	PROP_NAME		VARCHAR(128) UNIQUE NOT NULL,
	UNIT			INT,
	UNIT_FEE		INT,
	ACTIVE			BOOLEAN DEFAULT TRUE
);


create table RESIDENT (
	RESIDENT_ID		BIGINT PRIMARY KEY NOT NULL,
	ACTIVE			BOOLEAN DEFAULT FALSE,
	FIRST_NAME		VARCHAR(20),
	MIDDLE			VARCHAR(20),
	LAST_NAME		VARCHAR(20),
	PROP_ID			INT REFERENCES PROPERTY(PROP_ID),
	VOICEMAIL_NO	VARCHAR(20),
	TEXT_NO			VARCHAR(20),
	EMAIL			VARCHAR(128),
	ADDRESS			VARCHAR(128),
	ACK_PR			BOOLEAN DEFAULT TRUE,
	ALLOW_CONTACT	BOOLEAN DEFAULT FALSE,
	WANTS_SURVEY	BOOLEAN DEFAULT FALSE,
	PHOTO_RELEASE	BOOLEAN DEFAULT FALSE,
	DATE_ADDED		TIMESTAMP DEFAULT NOW(),
	DATE_MODIFIED	TIMESTAMP,
	MODIFIED_BY		VARCHAR(36),
	SERVICE_COORD	VARCHAR(36)
);

create table CHILD (
	CHILD_ID		BIGINT PRIMARY KEY NOT NULL,
	FULL_NAME		VARCHAR(50),	
	PARENT_ID		BIGINT REFERENCES RESIDENT(RESIDENT_ID)
);
 
-- Create table
create table APP_ROLE
(
  ROLE_ID   INT not null,
  ROLE_NAME VARCHAR(30) not null
) ;
--  
alter table APP_ROLE
  add constraint APP_ROLE_PK primary key (ROLE_ID);
 
alter table APP_ROLE
  add constraint APP_ROLE_UK unique (ROLE_NAME);
 
 
-- Create table
create table USER_ROLE
(
  ID      INT NOT NULL,
  USER_ID INT NOT NULL,
  ROLE_ID INT NOT NULL
);

--  
alter table USER_ROLE
  add constraint USER_ROLE_PK primary key (ID);
 
alter table USER_ROLE
  add constraint USER_ROLE_UK unique (USER_ID, ROLE_ID);
  
alter table USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (USER_ID)
  references  SERVICE_COORDINATOR(SC_ID);
 
alter table USER_ROLE
  add constraint USER_ROLE_FK2 foreign key (ROLE_ID)
  references APP_ROLE (ROLE_ID);  
  
-- Used by Spring Remember Me API.  
CREATE TABLE Persistent_Logins (
 
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    PRIMARY KEY (series)     
);
  

commit;

--------------------------------------

 
insert into SERVICE_COORDINATOR (SC_ID, USER_NAME, ENCRYPTED_PASSWORD, ACTIVE, EMAIL)
values (nextval('SC_SQ'), 'dbuser1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu',TRUE, 'dbuser1@email.com');
 
insert into SERVICE_COORDINATOR (SC_ID, USER_NAME, ENCRYPTED_PASSWORD, ACTIVE, EMAIL)
values (nextval('SC_SQ'), 'dbadmin1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu',TRUE,'dbadmin1@email.com');
-------------------------------------------
 
insert into app_role (ROLE_ID, ROLE_NAME)
values (1, 'ROLE_ADMIN');
 
insert into app_role (ROLE_ID, ROLE_NAME)
values (2, 'ROLE_USER');
 
--- 
insert into user_role (ID, USER_ID, ROLE_ID)
values (1, 1, 1);
 
insert into user_role (ID, USER_ID, ROLE_ID)
values (2, 1, 2);
 
insert into user_role (ID, USER_ID, ROLE_ID)
values (3, 2, 2);

---


INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Cutter Apts', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Eastmoor Square', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Fair Park', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Faith Village', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Fostoria Townhomes', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Glenview States', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Indian Meadows', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Kenmore Square', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Lawrence Village', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Ohio Townhomes', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Post Oaks', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Rosewind', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'The Meadows (CMHA)', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'The Meadows (Marrysville)', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Thornwood', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Villages at Roll Hill', 50, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Washington Court Apts', 50, 1000, TRUE);


Commit;