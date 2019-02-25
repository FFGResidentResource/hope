-- PostGreSQL - Initial Datasetup
-- Create table

--DROP SEQUENCE SC_SQ;
--DROP SEQUENCE PROP_SQ;
--DROP SEQUENCE RESIDENT_SQ;
--DROP SEQUENCE CHILD_SQ;
--DROP SEQUENCE REF_SQ;
--DROP SEQUENCE CHOICE_SQ;
--DROP SEQUENCE SCORE_SQ;
--DROP SEQUENCE HOUSING_SQ;
--DROP SEQUENCE RSG_SQ;
--DROP SEQUENCE RHA_SQ;

--DROP TABLE RESIDENT_SCORE_GOAL;
--DROP TABLE RESIDENT_HOUSING_ANSWERS;
--DROP TABLE CHILD;
--DROP TABLE RESIDENT;
--DROP TABLE ASSESSMENT_TYPE;
--DROP TABLE REFERRAL;
--DROP TABLE PROPERTY;
--DROP TABLE HOUSING_QUESTIONS;
--DROP TABLE SCORE;
--DROP TABLE CHOICE;
--DROP TABLE Persistent_Logins;
--DROP TABLE USER_ROLE;
--DROP TABLE APP_ROLE;
--DROP TABLE SERVICE_COORDINATOR;


CREATE SEQUENCE SC_SQ START 1;

CREATE SEQUENCE PROP_SQ START 1;

CREATE SEQUENCE RESIDENT_SQ START 1;

CREATE SEQUENCE CHILD_SQ START 1;

CREATE SEQUENCE REF_SQ START 1;

CREATE SEQUENCE CHOICE_SQ START 1;

CREATE SEQUENCE SCORE_SQ START 1;

CREATE SEQUENCE RSG_SQ START 1;

CREATE SEQUENCE HOUSING_SQ START 1;

CREATE SEQUENCE RHA_SQ START 1;


CREATE TABLE HOUSING_QUESTIONS(
	QUESTION_ID		INT PRIMARY KEY NOT NULL,
	QUESTION		VARCHAR(500),
	CHOICE_CAT		VARCHAR(20)
);


INSERT INTO HOUSING_QUESTIONS VALUES(nextval('HOUSING_SQ'), 'Are you currently in jail, or a juvenile detention facility?', 'Simple');
INSERT INTO HOUSING_QUESTIONS VALUES(nextval('HOUSING_SQ'), 'Are you currently housed?', 'Simple');
INSERT INTO HOUSING_QUESTIONS VALUES(nextval('HOUSING_SQ'), 'Are you at immediate risk of losing your housing?', 'Simple');
INSERT INTO HOUSING_QUESTIONS VALUES(nextval('HOUSING_SQ'), 'Do you currently reside in a house or an apartment (versus couch surfing or some other arrangement)?', 'Simple');
INSERT INTO HOUSING_QUESTIONS VALUES(nextval('HOUSING_SQ'), 'Where do you currently reside?', 'Housing');
INSERT INTO HOUSING_QUESTIONS VALUES(nextval('HOUSING_SQ'), 'Are you paying for the hotel/motel using your own money (or a friend’s or family member’s money), or do you receive assistance from a social service program?', 'Simple');
INSERT INTO HOUSING_QUESTIONS VALUES(nextval('HOUSING_SQ'), 'Did you obtain your housing through a social service program, Child Protective Services, or some other organization?', 'Simple');
INSERT INTO HOUSING_QUESTIONS VALUES(nextval('HOUSING_SQ'), 'Can you continue living in your current residence for as long as you would like?', 'Simple');
INSERT INTO HOUSING_QUESTIONS VALUES(nextval('HOUSING_SQ'), 'Do you – or whoever is responsible for paying for your housing – spend more than 30% of your income on jousting related expenses?', 'Simple');
INSERT INTO HOUSING_QUESTIONS VALUES(nextval('HOUSING_SQ'), 'Do you have any safety concerns or accessibility concerns with respect to the physical structure of your housing?', 'Simple');
INSERT INTO HOUSING_QUESTIONS VALUES(nextval('HOUSING_SQ'), 'Do you consider your housing to be adequate and meeting your needs?', 'Simple');
INSERT INTO HOUSING_QUESTIONS VALUES(nextval('HOUSING_SQ'), 'Is the housing subsidized, low-income housing, a subsidized co-op, or some other type of government-supported housing?', 'Simple');


CREATE TABLE SCORE(
	SCORE_ID		INT PRIMARY KEY NOT NULL,
	SCORE_VALUE		VARCHAR(50)
);

INSERT INTO SCORE VALUES(nextval('SCORE_SQ'),'In Crisis (1)');
INSERT INTO SCORE VALUES(nextval('SCORE_SQ'),'Vulnerable (2)');
INSERT INTO SCORE VALUES(nextval('SCORE_SQ'),'Safe (3)');
INSERT INTO SCORE VALUES(nextval('SCORE_SQ'),'Building Capacity (4)');
INSERT INTO SCORE VALUES(nextval('SCORE_SQ'),'Empowered (5)');

CREATE TABLE CHOICE(
	CHOICE_ID		INT PRIMARY KEY NOT NULL,
	CHOICE_VALUE	VARCHAR(128) NOT NULL,
	CHOICE_CAT		VARCHAR(20) NOT NULL		
);

INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Yes', 'Simple');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'No', 'Simple');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Don''t know', 'Simple');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Did not answer', 'Simple');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Boarding home', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Dorm/co-op', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Emergency shelter', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Foster family home', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Friend/family house', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Group home', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Hotel/motel', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Medical or psychiatric hospital', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Place not meant for habitation', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Rapid re-housing', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Residential project/halfway house', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Residential treatment center', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Safe haven', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Supervised independent living program', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Supportive housing', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Transitional living program ', 'Housing');
INSERT INTO CHOICE values(nextval('CHOICE_SQ'), 'Unsubsidized apartment/house', 'Housing');


CREATE TABLE ASSESSMENT_TYPE(	
	A_ID	INT PRIMARY KEY NOT NULL,
	A_VALUE	VARCHAR(10) NOT NULL
);

INSERT INTO ASSESSMENT_TYPE values(1, 'Initial');
INSERT INTO ASSESSMENT_TYPE values(2, '6-Month');
INSERT INTO ASSESSMENT_TYPE values(3, 'Annual');
INSERT INTO ASSESSMENT_TYPE values(4, 'Other');

CREATE TABLE REFERRAL(
	REF_ID		INT	PRIMARY KEY NOT NULL,
	REF_VALUE	VARCHAR(128) NOT NULL
);

INSERT INTO REFERRAL values(nextval('REF_SQ'), 'Self(Resident)/Walk-In');
INSERT INTO REFERRAL values(nextval('REF_SQ'), 'Property Mgmt (Includes Community Manager and Maintenance)');
INSERT INTO REFERRAL values(nextval('REF_SQ'), 'Service Coordinator');
INSERT INTO REFERRAL values(nextval('REF_SQ'), 'Other Resident');
INSERT INTO REFERRAL values(nextval('REF_SQ'), 'Other');

CREATE table SERVICE_COORDINATOR (
	SC_ID				INT PRIMARY KEY NOT NULL,
	USER_NAME       	VARCHAR(50) NOT NULL UNIQUE,
	ENCRYPTED_PASSWORD 	VARCHAR(128) NOT NULL,
	ACTIVE          	BOOLEAN DEFAULT FALSE,
	EMAIL		   	 	VARCHAR (128) UNIQUE,
	CREATED_ON	    	TIMESTAMP DEFAULT NOW(),
	LAST_LOGIN	    	TIMESTAMP 
);

CREATE table PROPERTY (
	PROP_ID			INT PRIMARY KEY NOT NULL,
	PROP_NAME		VARCHAR(128) UNIQUE NOT NULL,
	UNIT			INT,
	UNIT_FEE		INT,
	ACTIVE			BOOLEAN DEFAULT TRUE
);


CREATE table RESIDENT (
	RESIDENT_ID		BIGINT PRIMARY KEY NOT NULL,
	ACTIVE			BOOLEAN DEFAULT FALSE,
	REF_TYPE		INT REFERENCES REFERRAL(REF_ID) NOT NULL,	
	FIRST_NAME		VARCHAR(128),
	MIDDLE			VARCHAR(128),
	LAST_NAME		VARCHAR(128),
	PROP_ID			INT REFERENCES PROPERTY(PROP_ID),
	VIA_VOICEMAIL	BOOLEAN DEFAULT FALSE,
	VIA_TEXT		BOOLEAN DEFAULT FALSE,
	VIA_EMAIL		BOOLEAN DEFAULT FALSE,
	VOICEMAIL_NO	VARCHAR(20),
	TEXT_NO			VARCHAR(20),
	EMAIL			VARCHAR(256) UNIQUE,
	ADDRESS			VARCHAR(256),
	ACK_PR			BOOLEAN DEFAULT TRUE,
	ALLOW_CONTACT	BOOLEAN DEFAULT FALSE,
	WANTS_SURVEY	BOOLEAN DEFAULT FALSE,
	PHOTO_RELEASE	BOOLEAN DEFAULT FALSE,
	DATE_ADDED		TIMESTAMP DEFAULT NOW(),
	DATE_MODIFIED	TIMESTAMP,	
	MODIFIED_BY		VARCHAR(50),
	SERVICE_COORD	VARCHAR(50),
	A_TYPE			INT REFERENCES ASSESSMENT_TYPE(A_ID),
	A_DATE			TIMESTAMP
);


alter table RESIDENT
  add constraint RESIDENT_UK unique (FIRST_NAME, LAST_NAME, PROP_ID, ADDRESS);


CREATE table CHILD (
	CHILD_ID		BIGINT PRIMARY KEY NOT NULL,
	FULL_NAME		VARCHAR(50),	
	PARENT_ID		BIGINT REFERENCES RESIDENT(RESIDENT_ID),
	PVR_FLAG		BOOLEAN DEFAULT FALSE
);

CREATE TABLE RESIDENT_HOUSING_ANSWERS(

	RHA_ID			BIGINT PRIMARY KEY NOT NULL,
	RESIDENT_ID		BIGINT REFERENCES RESIDENT(RESIDENT_ID),
	QUESTION_ID		INT REFERENCES HOUSING_QUESTIONS(QUESTION_ID),
	ANSWER			INT	REFERENCES CHOICE(CHOICE_ID)	
);

CREATE TABLE RESIDENT_SCORE_GOAL(

	RSG_ID			BIGINT PRIMARY KEY NOT NULL,
	RESIDENT_ID		BIGINT REFERENCES RESIDENT(RESIDENT_ID),
	CATEGORY		VARCHAR(25),
	SCORE			INT,
	GOAL			INT
);
 
-- Create table
CREATE table APP_ROLE
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
CREATE table USER_ROLE
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