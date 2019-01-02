-- PostGreSQL - Initial Datasetup
-- Create table

create table RESIDENT
(
  RESIDENT_ID       BIGINT PRIMARY KEY NOT NULL,
  ACTIVE	    	VARCHAR(10) NOT NULL,
  FIRST_NAME        VARCHAR(20) NOT NULL,
  MIDDLE        	VARCHAR(20),
  LAST_NAME        	VARCHAR(20) NOT NULL,
  ADDRESS			VARCHAR(128),
  CITY				VARCHAR(20),
  STATE				VARCHAR(20),
  ZIP_CODE			VARCHAR(20),
  PHONE				VARCHAR(15),
  MOBILE_PHONE		VARCHAR(15),
  EMAIL				VARCHAR(128),
  COMMUNITY_NAME	VARCHAR(30),
  LAST_UPDATED		TIMESTAMP NOT NULL DEFAULT NOW(),
  LAST_UPDATED_BY	VARCHAR(50) NOT NULL
) ;

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