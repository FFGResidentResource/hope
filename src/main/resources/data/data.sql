-- PostGreSQL - Initial Datasetup
-- Create table

create table RESIDENT (
	RESIDENT_ID		BIGINT PRIMARY KEY NOT NULL,
	ACTIVE			BOOLEAN DEFAULT FALSE,
	FIRST_NAME		VARCHAR(20),
	MIDDLE			VARCHAR(20),
	LAST_NAME		VARCHAR(20),
	UNIT_ID			BIGINT REFERENCES UNIT(UNIT_ID),
	VOICEMAIL_NO	VARCHAR(20),
	TEXT_NO			VARCHAR(20),
	EMAIL			VARCHAR(128),
	ALLOW_CONTACT	BOOLEAN DEFAULT FALSE,
	WANTS_SURVEY	BOOLEAN DEFAULT FALSE,
	PHOTO_RELEASE	BOOLEAN DEFAULT FALSE,
	DATE_ADDED		TIMESTAMP DEFAULT NOW(),
	SERVICE_COORD	BIGINT REFERENCES SERVICE_COORDINATOR(SC_ID)
);

create table SERVICE_COORDINATOR (
	SC_ID			INT PRIMARY KEY NOT NULL,
	USER_NAME       VARCHAR(36) NOT NULL UNIQUE,
	ENCRYPTED_PASSWORD VARCHAR(128) NOT NULL,
	ACTIVE          BOOLEAN DEFAULT FALSE,
	EMAIL		    VARCHAR (128) UNIQUE,
	CREATED_ON	    TIMESTAMP DEFAULT NOW(),
	LAST_LOGIN	    TIMESTAMP 
);

create table UNIT (
	UNIT_ID			BIGINT PRIMARY KEY NOT NULL,
	UNIT_NAME		VARCHAR(20),
	PROPERTY_ID		BIGINT REFERENCES PROPERTY(PROP_ID)
);

create table PROPERTY (
	PROP_ID			BIGINT PRIMARY KEY NOT NULL,
	PROP_NAME		VARCHAR(50) NOT NULL
);

create table CHILD (
	CHILD_ID		BIGINT PRIMARY KEY NOT NULL,
	FIRST_NAME		VARCHAR(20),
	MIDDLE			VARCHAR(20),
	LAST_NAME		VARCHAR(20),
	PARENT_ID		BIGINT REFERENCES RESIDENT(RESIDENT_ID)
);
--  

create table APP_USER
(
  USER_ID           INT NOT NULL,
  USER_NAME         VARCHAR(36) NOT NULL,
  ENCRYTED_PASSWORD VARCHAR(128) NOT NULL,
  ACTIVE            VARCHAR(10) NOT NULL,
  EMAIL		    	VARCHAR (128) UNIQUE NOT NULL,
  CREATED_ON	    TIMESTAMP NOT NULL DEFAULT NOW(),
  LAST_LOGIN	    TIMESTAMP 
) ;
--  
alter table APP_USER
  add constraint APP_USER_PK primary key (USER_ID);
 
alter table APP_USER
  add constraint APP_USER_UK unique (USER_NAME);
 
 
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
  ID      INT not null,
  USER_ID INT not null,
  ROLE_ID INT not null
);
--  
alter table USER_ROLE
  add constraint USER_ROLE_PK primary key (ID);
 
alter table USER_ROLE
  add constraint USER_ROLE_UK unique (USER_ID, ROLE_ID);
 
alter table USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (USER_ID)
  references APP_USER (USER_ID);
 
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
  
--------------------------------------
 
insert into App_User (USER_ID, USER_NAME, ENCRYTED_PASSWORD, ACTIVE, EMAIL)
values (2, 'dbuser1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu','A', 'dbuser1@email.com');
 
insert into App_User (USER_ID, USER_NAME, ENCRYTED_PASSWORD, ACTIVE, EMAIL)
values (1, 'dbadmin1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu','A','dbadmin1@email.com');

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



Commit;