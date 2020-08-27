-- PostGreSQL - Initial Datasetup
-- Create table

--FOLLOWING DROP only requires in NON-PROD Environments - BEGIN
DROP SEQUENCE if exists SC_SQ;
DROP SEQUENCE if exists PROP_SQ;
DROP SEQUENCE if exists RESIDENT_SQ;
DROP SEQUENCE if exists CHILD_SQ;
DROP SEQUENCE if exists REF_SQ;
DROP SEQUENCE if exists CHOICE_SQ;
DROP SEQUENCE if exists SCORE_SQ;
DROP SEQUENCE if exists RAQ_SQ;
DROP SEQUENCE if exists RSG_SQ;
DROP SEQUENCE if exists AQ_SQ;
DROP SEQUENCE if exists AP_SQ;
DROP SEQUENCE if exists CN_SQ;
DROP SEQUENCE if exists RF_SQ;
DROP SEQUENCE if exists UR_SQ;
DROP SEQUENCE if exists RP_SQ;


DROP VIEW if exists AGENCY_RESIDENT_VIEW;
DROP VIEW if exists ASSESSMENT_COMPLETED_VIEW;
DROP VIEW if exists MOVING_DOWN_VIEW CASCADE;
DROP VIEW if exists MOVING_UP_VIEW CASCADE;
DROP VIEW if exists NEW_RESIDENT_VIEW;
DROP VIEW if exists ONGOING_RESIDENT_VIEW;
DROP VIEW if exists OUTCOMES_ACHIEVED_VIEW;
DROP VIEW if exists RESIDENT_ACTION_PLAN_VIEW;
DROP VIEW if exists RESIDENT_SERVED_VIEW;
DROP VIEW if exists SERVICE_CATEGORY_VIEW;
DROP VIEW if exists REFERRAL_REASON_VIEW;

DROP TABLE if exists REFERRAL_FORM;
DROP TABLE if exists ACTION_PLAN CASCADE;
DROP TABLE if exists CASE_NOTES;
DROP TABLE if exists RESIDENT_SCORE_GOAL CASCADE;
DROP TABLE if exists QUESTION_CHOICE;
DROP TABLE if exists RESIDENT_ASSESSMENT_QUESTIONNAIRE;
DROP TABLE if exists CHILD;
DROP TABLE if exists RESIDENT;
DROP TABLE if exists ASSESSMENT_TYPE;
DROP TABLE if exists ASSESSMENT_QUESTIONNAIRE;
DROP TABLE if exists REFERRAL;
DROP TABLE if exists REFERRAL_PARTNER;

DROP TABLE if exists SCORE;
DROP TABLE if exists CHOICE;
DROP TABLE if exists Persistent_Logins;
DROP TABLE if exists USER_ROLE;
DROP TABLE if exists APP_ROLE;
DROP TABLE if exists SERVICE_COORDINATOR;
DROP TABLE if exists PROPERTY;


--FOLLOWING DROP only requires in NON-PROD Environments - END


--COPY FROM HERE FOR PRODUCTION ENVIRONMENT - BEGIN

CREATE SEQUENCE SC_SQ START 1;

CREATE SEQUENCE PROP_SQ START 1;

CREATE SEQUENCE RESIDENT_SQ START 1;

CREATE SEQUENCE CHILD_SQ START 1;

CREATE SEQUENCE REF_SQ START 1;

CREATE SEQUENCE SCORE_SQ START 1;

CREATE SEQUENCE RSG_SQ START 1;

CREATE SEQUENCE AQ_SQ START 1;

CREATE SEQUENCE CHOICE_SQ START 1;

CREATE SEQUENCE RAQ_SQ START 1;

CREATE SEQUENCE AP_SQ START 1;

CREATE SEQUENCE CN_SQ START 1;

CREATE SEQUENCE RF_SQ START 1;

CREATE SEQUENCE UR_SQ START 1;

CREATE SEQUENCE RP_SQ START 1;

CREATE TABLE REFERRAL_PARTNER (
	REF_PAR_ID INT 				PRIMARY KEY NOT NULL,
	REFERRAL_PARTNER_NAME 		VARCHAR(500),
	ACTIVE						BOOLEAN DEFAULT TRUE
);

INSERT INTO REFERRAL_PARTNER values (nextval('RP_SQ'),'ABC Company');
INSERT INTO REFERRAL_PARTNER values (nextval('RP_SQ'),'Dental Associates Ohio');
INSERT INTO REFERRAL_PARTNER values (nextval('RP_SQ'),'XYZ Education Department');
INSERT INTO REFERRAL_PARTNER values (nextval('RP_SQ'),'Employment Division');
INSERT INTO REFERRAL_PARTNER values (nextval('RP_SQ'),'Ohio Housing Board');
INSERT INTO REFERRAL_PARTNER values (nextval('RP_SQ'),'Food Company');
INSERT INTO REFERRAL_PARTNER values (nextval('RP_SQ'),'Ohio Housing Board');
INSERT INTO REFERRAL_PARTNER values (nextval('RP_SQ'),'Erie Money Management');


CREATE TABLE ASSESSMENT_QUESTIONNAIRE(
	QUESTION_ID		INT PRIMARY KEY NOT NULL,
	QUESTION_NUMBER INT,
	QUESTION		VARCHAR(500),
	LIFE_DOMAIN		VARCHAR(50),
	SORT			INT
);


INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 1,'Are you currently housed?', 'HOUSING', 1);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 2,'Are you at immediate risk of losing your housing?', 'HOUSING', 2);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 3,'Are you obtaining rental assistance or rent subsidy (e.g., housing choice voucher)?', 'HOUSING',3);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 4,'Can you continue living in your current residence for as long as you would like?', 'HOUSING', 4);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 5,'Do you - or whoever is responsible for paying for your housing - spend more than 30% of your income on jousting related expenses?', 'HOUSING',5);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 6,'Do you have any safety concerns or accessibility concerns with respect to the physical structure of your housing?', 'HOUSING',6);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 7,'Do you consider your housing to be adequate and meeting your needs?', 'HOUSING',7);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 8,'Is the housing subsidized, low-income housing, a subsidized co-op, or some other type of government-supported housing?', 'HOUSING',8);
--MONEY MANAGEMENT
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 1,'Do you know how to use a budget and use it on a regular basis?', 'MONEY MANAGEMENT',9);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 2,'Do you know whether or not you have monthly deficit or surplus?', 'MONEY MANAGEMENT',10);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 3,'Are you able to track expenses?', 'MONEY MANAGEMENT',11);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 4,'Can you prioritize your expenses?', 'MONEY MANAGEMENT',12);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 5,'Do you organize your bills and other financial paperwork?', 'MONEY MANAGEMENT',13);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 6,'Are you able to pay bills on time?', 'MONEY MANAGEMENT',14);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 7,'Do you feel you understand your debts and have control over them?', 'MONEY MANAGEMENT',15);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 8,'Are you currently experiencing garnishments?', 'MONEY MANAGEMENT',16);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 9,'Do you have savings or checking account?', 'MONEY MANAGEMENT',17);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 10,'Are you able to save money? if yes, is it sporadically or on a consistent basis?', 'MONEY MANAGEMENT',18);
--EMPLOYMENT
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 1,'How old are you?', 'EMPLOYMENT',19);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 2,'Do you have a job?', 'EMPLOYMENT',20);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 3,'Do you work full time(32+ hours a week)?', 'EMPLOYMENT',21);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 4,'Does the job allow you to pay for all your basic needs (i.e., food, clothing, shelter)?', 'EMPLOYMENT',22);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 5,'Does the job offer health insurance options, paid time off, and some type of retirement plan?', 'EMPLOYMENT',23);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 6,'Have you been employed at the current workplace for at least three months?', 'EMPLOYMENT',24);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 7,'Can you remain at the current job for as long as you like?', 'EMPLOYMENT',25);
--EDUCATION
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 1,'Do you have a high school diploma or GED?', 'EDUCATION',26);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 2,'Are you enrolled in high school, GED program, or an alternative education program?', 'EDUCATION',27);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 3,'Do you have any special education needs or other accommodations that are not being met?', 'EDUCATION',28);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 4,'Do you regularly attend the classes you are enrolled in?', 'EDUCATION',29);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 5,'Have you completed additional training or education beyond receiving a high school diploma or GED(an received a degree or vocational certification?', 'EDUCATION',30);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 6,'Are you currently getting additional training or education to help you job opportunities or to earn more money?', 'EDUCATION',31);
--NETWORK_SUPPORT
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 1,'Do you receive any type of support (emotional, financial, or material) from your extended family, friends, or other members of your social circle?', 'NETWORK SUPPORT',32);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 2,'Does your extended family or friends want to provide support?', 'NETWORK SUPPORT',33);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 3,'Does your extended family or friends tend to be demanding, critical, or a bad influence?', 'NETWORK SUPPORT',34);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 4,'When or if necessary, would you be able to rely on someone among your extended family/friends to provide Emotional Support?', 'NETWORK SUPPORT',35);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 5,'When or if necessary, would you be able to rely on someone among your extended family/friends to provide Financial support?', 'NETWORK SUPPORT',36);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 6,'When or if necessary, would you be able to rely on someone among your extended family/friends to provide  Material support, such as a place to stay, food, or clothing?', 'NETWORK SUPPORT',37);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 7,'Is your support network growing?', 'NETWORK SUPPORT',38);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 8,'Are you aware of where to go in the community for help?', 'NETWORK SUPPORT',39);
--HOSUEHOLD_MANAGEMENT
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 1,'Has the resident received a notification in the past 6 months regarding the cleanliness or condition of their home?', 'HOUSEHOLD MANAGEMENT',40);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 2,'Are you able to keep your home clean and organized?', 'HOUSEHOLD MANAGEMENT',41);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 3,'Are you able to do laundry and have clean clothes available for your family members?', 'HOUSEHOLD MANAGEMENT',42);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 4,'Do you do the dishes after each meal and keep the kitchen clean?', 'HOUSEHOLD MANAGEMENT',43);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 5,'Do you regularly take out the trash and recycle?', 'HOUSEHOLD MANAGEMENT',44);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 6,'Are you able to plan and prepare meals on a regular basis?', 'HOUSEHOLD MANAGEMENT',45);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 7,'Are you able to replenish household supplies before you run out (i.e., laundry detergent, toilet paper, etc.)?', 'HOUSEHOLD MANAGEMENT',46);
INSERT INTO ASSESSMENT_QUESTIONNAIRE VALUES(nextval('AQ_SQ'), 8,'Are you able to maintain the outside of your home, if required (i.e., yard, sidewalks, etc.)?', 'HOUSEHOLD MANAGEMENT',47);

CREATE TABLE CHOICE(
	CHOICE_ID INT PRIMARY KEY NOT NULL,
	CHOICE 	  VARCHAR(50),
	SORT	  INT
);

INSERT INTO CHOICE VALUES(nextval('CHOICE_SQ'), 'Yes', 1);
INSERT INTO CHOICE VALUES(nextval('CHOICE_SQ'), 'No', 2);
INSERT INTO CHOICE VALUES(nextval('CHOICE_SQ'), 'Don''t know', 3);
INSERT INTO CHOICE VALUES(nextval('CHOICE_SQ'), 'Did not answer', 4);
INSERT INTO CHOICE VALUES(nextval('CHOICE_SQ'), 'Sporadically', 5);
INSERT INTO CHOICE VALUES(nextval('CHOICE_SQ'), 'Consistently', 6);
INSERT INTO CHOICE VALUES(nextval('CHOICE_SQ'), 'Under 18', 7);
INSERT INTO CHOICE VALUES(nextval('CHOICE_SQ'), '18-25', 8);
INSERT INTO CHOICE VALUES(nextval('CHOICE_SQ'), '26-35', 9);
INSERT INTO CHOICE VALUES(nextval('CHOICE_SQ'), '36-50', 10);
INSERT INTO CHOICE VALUES(nextval('CHOICE_SQ'), '+50', 11);

CREATE TABLE QUESTION_CHOICE(
	QUESTION_ID INT REFERENCES ASSESSMENT_QUESTIONNAIRE(QUESTION_ID) NOT NULL,
	CHOICE_ID	INT REFERENCES CHOICE(CHOICE_ID) NOT NULL
);

INSERT INTO QUESTION_CHOICE VALUES (1, 1);
INSERT INTO QUESTION_CHOICE VALUES (1, 2);
INSERT INTO QUESTION_CHOICE VALUES (1, 3);
INSERT INTO QUESTION_CHOICE VALUES (1, 4);

INSERT INTO QUESTION_CHOICE VALUES (2, 1);
INSERT INTO QUESTION_CHOICE VALUES (2, 2);
INSERT INTO QUESTION_CHOICE VALUES (2, 3);
INSERT INTO QUESTION_CHOICE VALUES (2, 4);

INSERT INTO QUESTION_CHOICE VALUES (3, 1);
INSERT INTO QUESTION_CHOICE VALUES (3, 2);
INSERT INTO QUESTION_CHOICE VALUES (3, 3);
INSERT INTO QUESTION_CHOICE VALUES (3, 4);

INSERT INTO QUESTION_CHOICE VALUES (4, 1);
INSERT INTO QUESTION_CHOICE VALUES (4, 2);
INSERT INTO QUESTION_CHOICE VALUES (4, 3);
INSERT INTO QUESTION_CHOICE VALUES (4, 4);

INSERT INTO QUESTION_CHOICE VALUES (5, 1);
INSERT INTO QUESTION_CHOICE VALUES (5, 2);
INSERT INTO QUESTION_CHOICE VALUES (5, 3);
INSERT INTO QUESTION_CHOICE VALUES (5, 4);

INSERT INTO QUESTION_CHOICE VALUES (6, 1);
INSERT INTO QUESTION_CHOICE VALUES (6, 2);
INSERT INTO QUESTION_CHOICE VALUES (6, 3);
INSERT INTO QUESTION_CHOICE VALUES (6, 4);

INSERT INTO QUESTION_CHOICE VALUES (7, 1);
INSERT INTO QUESTION_CHOICE VALUES (7, 2);
INSERT INTO QUESTION_CHOICE VALUES (7, 3);
INSERT INTO QUESTION_CHOICE VALUES (7, 4);

INSERT INTO QUESTION_CHOICE VALUES (8, 1);
INSERT INTO QUESTION_CHOICE VALUES (8, 2);
INSERT INTO QUESTION_CHOICE VALUES (8, 3);
INSERT INTO QUESTION_CHOICE VALUES (8, 4);

INSERT INTO QUESTION_CHOICE VALUES (9, 1);
INSERT INTO QUESTION_CHOICE VALUES (9, 2);
INSERT INTO QUESTION_CHOICE VALUES (9, 3);
INSERT INTO QUESTION_CHOICE VALUES (9, 4);

INSERT INTO QUESTION_CHOICE VALUES (10, 1);
INSERT INTO QUESTION_CHOICE VALUES (10, 2);
INSERT INTO QUESTION_CHOICE VALUES (10, 3);
INSERT INTO QUESTION_CHOICE VALUES (10, 4);

INSERT INTO QUESTION_CHOICE VALUES (11, 1);
INSERT INTO QUESTION_CHOICE VALUES (11, 2);
INSERT INTO QUESTION_CHOICE VALUES (11, 3);
INSERT INTO QUESTION_CHOICE VALUES (11, 4);

INSERT INTO QUESTION_CHOICE VALUES (12, 1);
INSERT INTO QUESTION_CHOICE VALUES (12, 2);
INSERT INTO QUESTION_CHOICE VALUES (12, 3);
INSERT INTO QUESTION_CHOICE VALUES (12, 4);

INSERT INTO QUESTION_CHOICE VALUES (13, 1);
INSERT INTO QUESTION_CHOICE VALUES (13, 2);
INSERT INTO QUESTION_CHOICE VALUES (13, 3);
INSERT INTO QUESTION_CHOICE VALUES (13, 4);

INSERT INTO QUESTION_CHOICE VALUES (14, 1);
INSERT INTO QUESTION_CHOICE VALUES (14, 2);
INSERT INTO QUESTION_CHOICE VALUES (14, 3);
INSERT INTO QUESTION_CHOICE VALUES (14, 4);

INSERT INTO QUESTION_CHOICE VALUES (15, 1);
INSERT INTO QUESTION_CHOICE VALUES (15, 2);
INSERT INTO QUESTION_CHOICE VALUES (15, 3);
INSERT INTO QUESTION_CHOICE VALUES (15, 4);

INSERT INTO QUESTION_CHOICE VALUES (16, 1);
INSERT INTO QUESTION_CHOICE VALUES (16, 2);
INSERT INTO QUESTION_CHOICE VALUES (16, 3);
INSERT INTO QUESTION_CHOICE VALUES (16, 4);

INSERT INTO QUESTION_CHOICE VALUES (17, 1);
INSERT INTO QUESTION_CHOICE VALUES (17, 2);
INSERT INTO QUESTION_CHOICE VALUES (17, 3);
INSERT INTO QUESTION_CHOICE VALUES (17, 4);

INSERT INTO QUESTION_CHOICE VALUES (18, 2);
INSERT INTO QUESTION_CHOICE VALUES (18, 5);
INSERT INTO QUESTION_CHOICE VALUES (18, 6);
INSERT INTO QUESTION_CHOICE VALUES (18, 4);

INSERT INTO QUESTION_CHOICE VALUES (19, 7);
INSERT INTO QUESTION_CHOICE VALUES (19, 8);
INSERT INTO QUESTION_CHOICE VALUES (19, 9);
INSERT INTO QUESTION_CHOICE VALUES (19, 10);
INSERT INTO QUESTION_CHOICE VALUES (19, 11);

INSERT INTO QUESTION_CHOICE VALUES (20, 1);
INSERT INTO QUESTION_CHOICE VALUES (20, 2);
INSERT INTO QUESTION_CHOICE VALUES (20, 3);
INSERT INTO QUESTION_CHOICE VALUES (20, 4);

INSERT INTO QUESTION_CHOICE VALUES (21, 1);
INSERT INTO QUESTION_CHOICE VALUES (21, 2);
INSERT INTO QUESTION_CHOICE VALUES (21, 3);
INSERT INTO QUESTION_CHOICE VALUES (21, 4);

INSERT INTO QUESTION_CHOICE VALUES (22, 1);
INSERT INTO QUESTION_CHOICE VALUES (22, 2);
INSERT INTO QUESTION_CHOICE VALUES (22, 3);
INSERT INTO QUESTION_CHOICE VALUES (22, 4);

INSERT INTO QUESTION_CHOICE VALUES (23, 1);
INSERT INTO QUESTION_CHOICE VALUES (23, 2);
INSERT INTO QUESTION_CHOICE VALUES (23, 3);
INSERT INTO QUESTION_CHOICE VALUES (23, 4);

INSERT INTO QUESTION_CHOICE VALUES (24, 1);
INSERT INTO QUESTION_CHOICE VALUES (24, 2);
INSERT INTO QUESTION_CHOICE VALUES (24, 3);
INSERT INTO QUESTION_CHOICE VALUES (24, 4);

INSERT INTO QUESTION_CHOICE VALUES (25, 1);
INSERT INTO QUESTION_CHOICE VALUES (25, 2);
INSERT INTO QUESTION_CHOICE VALUES (25, 3);
INSERT INTO QUESTION_CHOICE VALUES (25, 4);

INSERT INTO QUESTION_CHOICE VALUES (26, 1);
INSERT INTO QUESTION_CHOICE VALUES (26, 2);
INSERT INTO QUESTION_CHOICE VALUES (26, 3);
INSERT INTO QUESTION_CHOICE VALUES (26, 4);

INSERT INTO QUESTION_CHOICE VALUES (27, 1);
INSERT INTO QUESTION_CHOICE VALUES (27, 2);
INSERT INTO QUESTION_CHOICE VALUES (27, 3);
INSERT INTO QUESTION_CHOICE VALUES (27, 4);

INSERT INTO QUESTION_CHOICE VALUES (28, 1);
INSERT INTO QUESTION_CHOICE VALUES (28, 2);
INSERT INTO QUESTION_CHOICE VALUES (28, 3);
INSERT INTO QUESTION_CHOICE VALUES (28, 4);

INSERT INTO QUESTION_CHOICE VALUES (29, 1);
INSERT INTO QUESTION_CHOICE VALUES (29, 2);
INSERT INTO QUESTION_CHOICE VALUES (29, 3);
INSERT INTO QUESTION_CHOICE VALUES (29, 4);

INSERT INTO QUESTION_CHOICE VALUES (30, 1);
INSERT INTO QUESTION_CHOICE VALUES (30, 2);
INSERT INTO QUESTION_CHOICE VALUES (30, 3);
INSERT INTO QUESTION_CHOICE VALUES (30, 4);

INSERT INTO QUESTION_CHOICE VALUES (31, 1);
INSERT INTO QUESTION_CHOICE VALUES (31, 2);
INSERT INTO QUESTION_CHOICE VALUES (31, 3);
INSERT INTO QUESTION_CHOICE VALUES (31, 4);

INSERT INTO QUESTION_CHOICE VALUES (32, 1);
INSERT INTO QUESTION_CHOICE VALUES (32, 2);
INSERT INTO QUESTION_CHOICE VALUES (32, 3);
INSERT INTO QUESTION_CHOICE VALUES (32, 4);

INSERT INTO QUESTION_CHOICE VALUES (33, 1);
INSERT INTO QUESTION_CHOICE VALUES (33, 2);
INSERT INTO QUESTION_CHOICE VALUES (33, 3);
INSERT INTO QUESTION_CHOICE VALUES (33, 4);

INSERT INTO QUESTION_CHOICE VALUES (34, 1);
INSERT INTO QUESTION_CHOICE VALUES (34, 2);
INSERT INTO QUESTION_CHOICE VALUES (34, 3);
INSERT INTO QUESTION_CHOICE VALUES (34, 4);

INSERT INTO QUESTION_CHOICE VALUES (35, 1);
INSERT INTO QUESTION_CHOICE VALUES (35, 2);
INSERT INTO QUESTION_CHOICE VALUES (35, 3);
INSERT INTO QUESTION_CHOICE VALUES (35, 4);

INSERT INTO QUESTION_CHOICE VALUES (36, 1);
INSERT INTO QUESTION_CHOICE VALUES (36, 2);
INSERT INTO QUESTION_CHOICE VALUES (36, 3);
INSERT INTO QUESTION_CHOICE VALUES (36, 4);

INSERT INTO QUESTION_CHOICE VALUES (37, 1);
INSERT INTO QUESTION_CHOICE VALUES (37, 2);
INSERT INTO QUESTION_CHOICE VALUES (37, 3);
INSERT INTO QUESTION_CHOICE VALUES (37, 4);

INSERT INTO QUESTION_CHOICE VALUES (38, 1);
INSERT INTO QUESTION_CHOICE VALUES (38, 2);
INSERT INTO QUESTION_CHOICE VALUES (38, 3);
INSERT INTO QUESTION_CHOICE VALUES (38, 4);

INSERT INTO QUESTION_CHOICE VALUES (39, 1);
INSERT INTO QUESTION_CHOICE VALUES (39, 2);
INSERT INTO QUESTION_CHOICE VALUES (39, 3);
INSERT INTO QUESTION_CHOICE VALUES (39, 4);

INSERT INTO QUESTION_CHOICE VALUES (40, 1);
INSERT INTO QUESTION_CHOICE VALUES (40, 2);
INSERT INTO QUESTION_CHOICE VALUES (40, 3);
INSERT INTO QUESTION_CHOICE VALUES (40, 4);

INSERT INTO QUESTION_CHOICE VALUES (41, 1);
INSERT INTO QUESTION_CHOICE VALUES (41, 2);
INSERT INTO QUESTION_CHOICE VALUES (41, 3);
INSERT INTO QUESTION_CHOICE VALUES (41, 4);

INSERT INTO QUESTION_CHOICE VALUES (42, 1);
INSERT INTO QUESTION_CHOICE VALUES (42, 2);
INSERT INTO QUESTION_CHOICE VALUES (42, 3);
INSERT INTO QUESTION_CHOICE VALUES (42, 4);

INSERT INTO QUESTION_CHOICE VALUES (43, 1);
INSERT INTO QUESTION_CHOICE VALUES (43, 2);
INSERT INTO QUESTION_CHOICE VALUES (43, 3);
INSERT INTO QUESTION_CHOICE VALUES (43, 4);

INSERT INTO QUESTION_CHOICE VALUES (44, 1);
INSERT INTO QUESTION_CHOICE VALUES (44, 2);
INSERT INTO QUESTION_CHOICE VALUES (44, 3);
INSERT INTO QUESTION_CHOICE VALUES (44, 4);

INSERT INTO QUESTION_CHOICE VALUES (45, 1);
INSERT INTO QUESTION_CHOICE VALUES (45, 2);
INSERT INTO QUESTION_CHOICE VALUES (45, 3);
INSERT INTO QUESTION_CHOICE VALUES (45, 4);

INSERT INTO QUESTION_CHOICE VALUES (46, 1);
INSERT INTO QUESTION_CHOICE VALUES (46, 2);
INSERT INTO QUESTION_CHOICE VALUES (46, 3);
INSERT INTO QUESTION_CHOICE VALUES (46, 4);

INSERT INTO QUESTION_CHOICE VALUES (47, 1);
INSERT INTO QUESTION_CHOICE VALUES (47, 2);
INSERT INTO QUESTION_CHOICE VALUES (47, 3);
INSERT INTO QUESTION_CHOICE VALUES (47, 4);

CREATE TABLE SCORE(
	SCORE_ID		INT PRIMARY KEY NOT NULL,
	SCORE_VALUE		VARCHAR(50)
);

INSERT INTO SCORE VALUES(nextval('SCORE_SQ'),'In Crisis (1)');
INSERT INTO SCORE VALUES(nextval('SCORE_SQ'),'Vulnerable (2)');
INSERT INTO SCORE VALUES(nextval('SCORE_SQ'),'Safe (3)');
INSERT INTO SCORE VALUES(nextval('SCORE_SQ'),'Building Capacity (4)');
INSERT INTO SCORE VALUES(nextval('SCORE_SQ'),'Empowered (5)');
INSERT INTO SCORE VALUES(nextval('SCORE_SQ'),'DkDa (6)');

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

CREATE table PROPERTY (
	PROP_ID			INT PRIMARY KEY NOT NULL,
	PROP_NAME		VARCHAR(128) UNIQUE NOT NULL,
	CITY			VARCHAR(50),
	STATE			VARCHAR(50),
	COUNTY			VARCHAR(20) 
	UNIT			INT,
	UNIT_FEE		INT,
	ACTIVE			BOOLEAN DEFAULT TRUE,
	TOTAL_RESIDENTS	INT,
	RESIDENT_COUNCIL BOOLEAN DEFAULT FALSE
);

CREATE table SERVICE_COORDINATOR (
	SC_ID				INT PRIMARY KEY NOT NULL,
	PROP_ID             INT REFERENCES PROPERTY(PROP_ID),
	USER_NAME       	VARCHAR(50) NOT NULL UNIQUE,
	ENCRYPTED_PASSWORD 	VARCHAR(128) NOT NULL,
	ACTIVE          	BOOLEAN DEFAULT FALSE,
	EMAIL		   	 	VARCHAR (128) UNIQUE,
	CREATED_ON	    	TIMESTAMP DEFAULT NOW(),
	LAST_LOGIN	    	TIMESTAMP,
	DATE_MODIFIED		TIMESTAMP,
	MODIFIED_BY			VARCHAR(50)
	
);

CREATE table RESIDENT (
	
	RESIDENT_ID		BIGINT PRIMARY KEY NOT NULL,
	ACTIVE			BOOLEAN DEFAULT TRUE,
	IS_RESIDENT 	BOOLEAN DEFAULT TRUE,
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
	EMAIL			VARCHAR(256),
	ADDRESS			VARCHAR(256),
	ACK_PR			BOOLEAN DEFAULT FALSE,
	ALLOW_CONTACT	BOOLEAN DEFAULT FALSE,
	WANTS_SURVEY	BOOLEAN DEFAULT FALSE,
	PHOTO_RELEASE	BOOLEAN DEFAULT FALSE,
	DATE_ADDED		TIMESTAMP DEFAULT NOW(),
	DATE_MODIFIED	TIMESTAMP DEFAULT NOW(),
	MODIFIED_BY		VARCHAR(50),
	SERVICE_COORD	VARCHAR(50),
	A_TYPE			INT REFERENCES ASSESSMENT_TYPE(A_ID),
	A_DATE			DATE,
	AGE				VARCHAR(3),
	PRI_LANGUAGE	VARCHAR(128) DEFAULT 'English',
	MARITAL_STATUS	VARCHAR(128) DEFAULT 'Married',
	ANNUAL_GROSS 	VARCHAR(128),
	GENDER			VARCHAR(128) DEFAULT 'Information not collected',
	ETHNICITY		VARCHAR(128) DEFAULT 'Information not collected',
	RACE			VARCHAR(128) DEFAULT 'Information not collected',
	H_O_H			VARCHAR(128) DEFAULT 'Information not collected',
	VETERAN			VARCHAR(128) DEFAULT 'Information not collected',
	DISABILITY		VARCHAR(128) DEFAULT 'Information not collected',
	RC_OR_EX_OFF	VARCHAR(128) DEFAULT 'Information not collected',
	SSI				VARCHAR(128) DEFAULT 'Information not collected',
	SSDI			VARCHAR(128) DEFAULT 'Information not collected',
	HEALTH_COVERAGE	VARCHAR(128) DEFAULT 'Information not collected',
	HIGHEST_EDU		VARCHAR(128) DEFAULT 'Information not collected',
	SAFE_DAY		BOOLEAN DEFAULT TRUE,
	SAFE_NIGHT 		BOOLEAN DEFAULT TRUE
	
);

alter table RESIDENT
  add constraint RESIDENT_UK unique (FIRST_NAME, LAST_NAME, PROP_ID, ADDRESS);
  
CREATE table CHILD (
	CHILD_ID		BIGINT PRIMARY KEY NOT NULL,
	FULL_NAME		VARCHAR(50),
	PARENT_ID		BIGINT REFERENCES RESIDENT(RESIDENT_ID),
	PVR_FLAG		BOOLEAN DEFAULT FALSE
);

CREATE TABLE REFERRAL_FORM (
	REFERRAL_FORM_ID			BIGINT PRIMARY KEY NOT NULL,
	RESIDENT_ID					BIGINT REFERENCES RESIDENT(RESIDENT_ID),
	INTERPRETATION      		BOOLEAN DEFAULT FALSE,
	REFERRED_BY					VARCHAR(100),
	DATE_ADDED					DATE DEFAULT NOW(),
	DATE_MODIFIED				DATE DEFAULT NOW(),
	REFERRAL_REASON				JSON DEFAULT '{ "Non/late payment of rent": "false", "Utility Shut-off, scheduled for (Date):":"", "Housekeeping/home management":"false", "Lease violation for:": "", "Employment/job readiness":"false", "Education/job training":"false", "Noticeable change in:":"", "Resident-to-resident conflict issues":"false", "Suspected abuse/domestic violence/exploitation":"false", "Childcare/afterschool care":"false", "Transportation":"false", "Safety":"false", "Healthcare/medical issues":"false", "Other:":"" }',
	COMMENTS					VARCHAR(1000),
	PREVIOUS_ATTEMPTS			VARCHAR(1000),
	SELF_SUFFICIENCY			JSON DEFAULT '{ "Improve knowledge of resources":"false", "Improve educational status":"false", "Obtain/maintain employment":"false", "Move to home ownership":"false", "Other":"" }',
	RF_HOUSING_STABILITY		JSON DEFAULT '{ "Avoid  eviction":"false", "resolve lease violation":"false", "Other":""}',
	SAFE_SUPPORTIVE_COMMUNITY	JSON DEFAULT '{ "Greater sense of satisfaction":"false","Greater sense of safety":"false", "Greater sense of community/support":"false", "Other":""}',
	RF_FOLLOWUP_NOTES			VARCHAR(1000),
	RES_APP_SCHEDULED			JSON DEFAULT '{ "Resident Appointment Scheduled?":""}',
	SERVICE_COORD				VARCHAR(50)
);

CREATE TABLE ACTION_PLAN(
    ACTION_PLAN_ID			BIGINT PRIMARY KEY NOT NULL,
	RESIDENT_ID				BIGINT REFERENCES RESIDENT(RESIDENT_ID),	
    ACTIVE              	BOOLEAN DEFAULT TRUE,   
	PLAN_OF_ACTION			JSON DEFAULT '{ "HOUSING": "", "MONEY MANAGEMENT": "", "EMPLOYMENT": "", "EDUCATION": "", "NETWORK SUPPORT": "", "HOUSEHOLD MANAGEMENT": "" }',
	PLAN_DETAILS			JSON DEFAULT '{ "HOUSING": "", "MONEY MANAGEMENT": "", "EMPLOYMENT": "", "EDUCATION": "", "NETWORK SUPPORT": "", "HOUSEHOLD MANAGEMENT": "" }',
	REFERRAL_PARTNER		JSON DEFAULT '{ "HOUSING": "", "MONEY MANAGEMENT": "", "EMPLOYMENT": "", "EDUCATION": "", "NETWORK SUPPORT": "", "HOUSEHOLD MANAGEMENT": "" }',
	ANTICIPATED_OUTCOMES	JSON DEFAULT '{ "HOUSING": "", "MONEY MANAGEMENT": "", "EMPLOYMENT": "", "EDUCATION": "", "NETWORK SUPPORT": "", "HOUSEHOLD MANAGEMENT": "" }',
	ANTICIPATED_DATE		JSON DEFAULT '{ "HOUSING": "", "MONEY MANAGEMENT": "", "EMPLOYMENT": "", "EDUCATION": "", "NETWORK SUPPORT": "", "HOUSEHOLD MANAGEMENT": "" }',
	OUTCOME_ACHIEVED		JSON DEFAULT '{ "HOUSING": "", "MONEY MANAGEMENT": "", "EMPLOYMENT": "", "EDUCATION": "", "NETWORK SUPPORT": "", "HOUSEHOLD MANAGEMENT": "" }',
	COMPLETION_DATE			JSON DEFAULT '{ "HOUSING": "", "MONEY MANAGEMENT": "", "EMPLOYMENT": "", "EDUCATION": "", "NETWORK SUPPORT": "", "HOUSEHOLD MANAGEMENT": "" }',
	ACHIEVED_SSM			JSON DEFAULT '{ "HOUSING": "", "MONEY MANAGEMENT": "", "EMPLOYMENT": "", "EDUCATION": "", "NETWORK SUPPORT": "", "HOUSEHOLD MANAGEMENT": "" }',
	FOLLOWUP_NOTES			VARCHAR(2000),
	DATE_ADDED				DATE DEFAULT NOW(),
	DATE_MODIFIED			DATE DEFAULT NOW(),
	SERVICE_COORD			VARCHAR(50)
);
	
CREATE TABLE CASE_NOTES(
	CASE_NOTES_ID	BIGINT PRIMARY KEY NOT NULL,
	DESCRIPTION		VARCHAR(2000),
	ASSESSMENT		VARCHAR(2000),
	PLAN			VARCHAR(2000),
	RESIDENT_ID		BIGINT REFERENCES RESIDENT(RESIDENT_ID),
	SERVICE_COORD	VARCHAR(50),
	DATE_ADDED		DATE DEFAULT NOW(),		
	DATE_MODIFIED	DATE DEFAULT NOW()
	
);


CREATE TABLE RESIDENT_ASSESSMENT_QUESTIONNAIRE(
	RAQ_ID			BIGINT PRIMARY KEY NOT NULL,
	RESIDENT_ID		BIGINT REFERENCES RESIDENT(RESIDENT_ID),
	QUESTION_ID		INT REFERENCES ASSESSMENT_QUESTIONNAIRE(QUESTION_ID),
	CHOICE_ID		INT REFERENCES CHOICE(CHOICE_ID),
	LIFE_DOMAIN		VARCHAR(50),
	ON_THIS_DATE	DATE
);


CREATE TABLE RESIDENT_SCORE_GOAL(
	RSG_ID			BIGINT PRIMARY KEY NOT NULL,
	RESIDENT_ID		BIGINT REFERENCES RESIDENT(RESIDENT_ID),
	LIFE_DOMAIN		VARCHAR(50),
	SCORE			INT,
	GOAL			INT,
	ON_THIS_DATE	DATE	
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

INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Cutter Apts','','','', 50, 1000, TRUE, 1200, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Eastmoor Square','','','', 50, 1000, TRUE, 1300, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Fair Park','Sardinia','OH','Brown', 40, 1000, TRUE, 1500, FALSE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Faith Village','Columbus','OH','Franklin', 144, 1000, TRUE, 1500, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Fostoria Townhomes II','Fostoria','OH','Seneca', 40, 1000, TRUE, 1500, FALSE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Glenview States','','','', 50, 1000, TRUE, 1500, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Indian Meadows','','','', 50, 1000, TRUE, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Kenmore Square','','','', 50, 1000, TRUE, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Lawrence Village','South Point','OH','Lawrence', 70, 1000, TRUE, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Ohio Townhomes','','','', 50, 1000, TRUE, 1500, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Post Oaks','','','', 50, 1000, TRUE, 1500, FALSE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Rosewind','','','', 50, 1000, TRUE, 1500, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'The Meadows (CMHA)','','','', 50, 1000, TRUE, 1100, FALSE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'The Meadows (Marrysville)','','','', 50, 900, TRUE, 1500, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Thornwood','','','', 50, 1000, TRUE, 1000, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Villages at Roll Hill','Cincinnati','OH','Hamilton', 703, 1000, TRUE, 500, TRUE);
INSERT INTO PROPERTY values (nextval('PROP_SQ'),'Washington Court Apts','','','', 50, 1000, TRUE, 500, FALSE);




--------------------------------------
 
insert into SERVICE_COORDINATOR (SC_ID, USER_NAME, ENCRYPTED_PASSWORD, ACTIVE, EMAIL)
values (nextval('SC_SQ'), 'dbadmin1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu',TRUE,'dbadmin1@email.com');

insert into SERVICE_COORDINATOR (SC_ID, USER_NAME, ENCRYPTED_PASSWORD, ACTIVE, EMAIL,  PROP_ID)
values (nextval('SC_SQ'), 'dbuser1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu',TRUE, 'dbuser1@email.com', 1);

insert into SERVICE_COORDINATOR (SC_ID, USER_NAME, ENCRYPTED_PASSWORD, ACTIVE, EMAIL, PROP_ID)
values (nextval('SC_SQ'), 'dbuser2', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu',FALSE,'dbuser2@email.com', 2);

insert into SERVICE_COORDINATOR (SC_ID, USER_NAME, ENCRYPTED_PASSWORD, ACTIVE, EMAIL,  PROP_ID)
values (nextval('SC_SQ'), 'dbuser3', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu',FALSE,'dbuser3@email.com', 3);

insert into SERVICE_COORDINATOR (SC_ID, USER_NAME, ENCRYPTED_PASSWORD, ACTIVE, EMAIL)
values (nextval('SC_SQ'), 'dbadmin2', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu',FALSE,'dbadmin2@email.com');

-------------------------------------------
 
insert into app_role (ROLE_ID, ROLE_NAME)
values (1, 'ROLE_ADMIN');
 
insert into app_role (ROLE_ID, ROLE_NAME)
values (2, 'ROLE_USER');
 
--- 

insert into user_role (ID, USER_ID, ROLE_ID)
values (nextval('UR_SQ'), 1, 1);
 
insert into user_role (ID, USER_ID, ROLE_ID)
values (nextval('UR_SQ'), 1, 2);
 
insert into user_role (ID, USER_ID, ROLE_ID)
values (nextval('UR_SQ'), 2, 2);

insert into user_role (ID, USER_ID, ROLE_ID)
values (nextval('UR_SQ'), 3, 2);

insert into user_role (ID, USER_ID, ROLE_ID)
values (nextval('UR_SQ'), 4, 2);

insert into user_role (ID, USER_ID, ROLE_ID)
values (nextval('UR_SQ'), 5, 1);

insert into user_role (ID, USER_ID, ROLE_ID)
values (nextval('UR_SQ'), 5, 2);

---
--
CREATE VIEW SERVICE_CATEGORY_VIEW
AS 
SELECT P."SRVC_CAT"||'_'||P."QUARTER"||'_'||P."YEAR" AS "PRIMARY_CAT", P."SRVC_CAT" AS "SRVC_CAT", P."QUARTER" AS "QUARTER", P."YEAR" AS "YEAR", SUM(P."COUNT"::FLOAT) AS "COUNT" from (
SELECT
    plan_of_action->>'HOUSING' AS "SRVC_CAT", 
    count(plan_of_action->>'HOUSING') AS "COUNT",
    extract(quarter from date_Added) as "QUARTER",
 extract (year from date_added) as "YEAR",
 'HOUSING' AS "LIFE_DOMAIN"
FROM
    ACTION_PLAN where  plan_of_action->>'HOUSING' not in ('')
GROUP BY
     plan_of_action->>'HOUSING', extract(quarter from date_Added), extract (year from date_added)
UNION 
SELECT
    plan_of_action->>'MONEY MANAGEMENT' AS "SRVC_CAT", 
    count(plan_of_action->>'MONEY MANAGEMENT') AS "COUNT",
    extract(quarter from date_Added) as "QUARTER",
 extract (year from date_added) as "YEAR",
 'MONEY MANAGEMENT' AS "LIFE_DOMAIN"
FROM
    ACTION_PLAN where  plan_of_action->>'MONEY MANAGEMENT' not in ('')
GROUP BY
     plan_of_action->>'MONEY MANAGEMENT', extract(quarter from date_Added), extract (year from date_added)
UNION 
SELECT
    plan_of_action->>'EDUCATION' AS "SRVC_CAT", 
    count(plan_of_action->>'EDUCATION') AS "COUNT",
    extract(quarter from date_Added) as "QUARTER",
 extract (year from date_added) as "YEAR",
 'EDUCATION' AS "LIFE_DOMAIN"
FROM
    ACTION_PLAN where  plan_of_action->>'EDUCATION' not in ('')
GROUP BY
     plan_of_action->>'EDUCATION', extract(quarter from date_Added), extract (year from date_added)
UNION 
SELECT
    plan_of_action->>'EMPLOYMENT' AS "SRVC_CAT", 
    count(plan_of_action->>'EMPLOYMENT') AS "COUNT",
    extract(quarter from date_Added) as "QUARTER",
 extract (year from date_added) as "YEAR",
 'EMPLOYMENT' AS "LIFE_DOMAIN"
FROM
    ACTION_PLAN where  plan_of_action->>'EMPLOYMENT' not in ('')
GROUP BY
     plan_of_action->>'EMPLOYMENT', extract(quarter from date_Added), extract (year from date_added)
UNION
SELECT
    plan_of_action->>'NETWORK SUPPORT' AS "SRVC_CAT", 
    count(plan_of_action->>'NETWORK SUPPORT') AS "COUNT",
    extract(quarter from date_Added) as "QUARTER",
 extract (year from date_added) as "YEAR",
 'NETWORK SUPPORT' AS "LIFE_DOMAIN"
FROM
    ACTION_PLAN where  plan_of_action->>'NETWORK SUPPORT' not in ('')
GROUP BY
     plan_of_action->>'NETWORK SUPPORT', extract(quarter from date_Added), extract (year from date_added)
UNION
SELECT
    plan_of_action->>'HOUSEHOLD MANAGEMENT' AS "SRVC_CAT", 
    count(plan_of_action->>'HOUSEHOLD MANAGEMENT') AS "COUNT",
    extract(quarter from date_Added) as "QUARTER",
 extract (year from date_added) as "YEAR",
 'HOUSEHOLD MANAGEMENT' AS "LIFE_DOMAIN"
FROM
    ACTION_PLAN where  plan_of_action->>'HOUSEHOLD MANAGEMENT' not in ('')
GROUP BY
     plan_of_action->>'HOUSEHOLD MANAGEMENT', extract(quarter from date_Added), extract (year from date_added)) P
GROUP BY P."SRVC_CAT", P."QUARTER", P."YEAR"
ORDER BY P."SRVC_CAT"
;


--new = totla of current quarter for current year 
CREATE VIEW NEW_RESIDENT_VIEW
AS 
select z."ID" as "RES_ID" from (
select distinct P."ID", P."ACK", extract( quarter from P."RES_DATE") as "RESQ" , extract( year from P."RES_DATE") as "RESY" , extract( quarter from P."SSM_DATE") as "SSMQ", extract( year from P."SSM_DATE") as "SSMY", extract ( quarter from P."CN_DATE" ) as "CNQ", extract ( year from P."CN_DATE" ) as "CNY", extract ( quarter from P."AP_DATE") as "APQ" , extract ( year from P."AP_DATE") as "APY" 
from (
select r.resident_id as "ID", r.ack_pr as "ACK", r.date_modified as "RES_DATE", rsg.on_this_date as "SSM_DATE", cn.DATE_MODIFIED as "CN_DATE", ap.DATE_MODIFIED as "AP_DATE" from resident r 
left join resident_score_goal rsg on rsg.resident_id = r.resident_id
left join case_notes cn on cn.resident_id = r.resident_id 
left join action_Plan ap on ap.resident_id = r.resident_id ) P 
where ( p."ACK" = true or P."SSM_DATE" is not null or P."CN_DATE" is not null or P."AP_DATE" is not null)
) z 
where (
	(	z."RESQ" = extract (quarter from now()) and z."RESY" = extract (year from now())) 
	OR (z."SSMQ" = extract (quarter from now()) AND z."SSMY" = extract (year from now())) 
	OR (z."CNQ"	 = extract (quarter from now()) AND z."CNY" = extract (year from now())) 
	OR (z."APQ"	 = extract (quarter from now()) AND z."APY"  = extract (year from now())))
group by z."ID"
;

--Ongoing = total of all previous quarters for current year
CREATE VIEW ONGOING_RESIDENT_VIEW
AS 
select z."ID" as "RES_ID" from (
select distinct P."ID", P."ACK", extract( quarter from P."RES_DATE") as "RESQ" , extract( year from P."RES_DATE") as "RESY" , extract( quarter from P."SSM_DATE") as "SSMQ", extract( year from P."SSM_DATE") as "SSMY", extract ( quarter from P."CN_DATE" ) as "CNQ", extract ( year from P."CN_DATE" ) as "CNY", extract ( quarter from P."AP_DATE") as "APQ" , extract ( year from P."AP_DATE") as "APY" 
from (
select r.resident_id as "ID", r.ack_pr as "ACK", r.date_modified as "RES_DATE", rsg.on_this_date as "SSM_DATE", cn.DATE_MODIFIED as "CN_DATE", ap.DATE_MODIFIED as "AP_DATE" from resident r 
left join resident_score_goal rsg on rsg.resident_id = r.resident_id
left join case_notes cn on cn.resident_id = r.resident_id 
left join action_Plan ap on ap.resident_id = r.resident_id ) P 
where ( p."ACK" = true or P."SSM_DATE" is not null or P."CN_DATE" is not null or P."AP_DATE" is not null)
) z 
where (
	(	z."RESQ" < extract (quarter from now()) and z."RESY" = extract (year from now())) 
	OR (z."SSMQ" < extract (quarter from now()) AND z."SSMY" = extract (year from now())) 
	OR (z."CNQ"	 < extract (quarter from now()) AND z."CNY" = extract (year from now())) 
	OR (z."APQ"	 < extract (quarter from now()) AND z."APY"  = extract (year from now())))
group by z."ID";


--Number of Resident Served
CREATE VIEW RESIDENT_SERVED_VIEW
AS
select z."ID" as "RES_ID", z."RESQ", z."RESY", z."SSMQ", z."SSMY", z."CNQ", z."CNY", z."APQ", z."APY", z."PROP_ID" from (
select distinct P."ID", P."ACK", extract( quarter from P."RES_DATE") as "RESQ" , extract( year from P."RES_DATE") as "RESY" , extract( quarter from P."SSM_DATE") as "SSMQ", extract( year from P."SSM_DATE") as "SSMY", extract ( quarter from P."CN_DATE" ) as "CNQ", extract ( year from P."CN_DATE" ) as "CNY", extract ( quarter from P."AP_DATE") as "APQ" , extract ( year from P."AP_DATE") as "APY", P."PROP_ID" as "PROP_ID"
from (
select r.resident_id as "ID", r.ack_pr as "ACK", r.date_modified as "RES_DATE", rsg.on_this_date as "SSM_DATE", cn.DATE_MODIFIED as "CN_DATE", ap.DATE_MODIFIED as "AP_DATE", r.PROP_ID as "PROP_ID" from resident r 
left join resident_score_goal rsg on rsg.resident_id = r.resident_id
left join case_notes cn on cn.resident_id = r.resident_id 
left join action_Plan ap on ap.resident_id = r.resident_id ) P 
where ( p."ACK" = true or P."SSM_DATE" is not null or P."CN_DATE" is not null or P."AP_DATE" is not null)
) z 
group by z."ID", z."RESQ", z."RESY", z."SSMQ", z."SSMY", z."CNQ", z."CNY", z."APQ", z."APY", z."PROP_ID"
ORDER BY "RES_ID";

--REsident who completed all the 6 assessment
CREATE VIEW ASSESSMENT_COMPLETED_VIEW
AS
select z."RES_ID", z."QUARTER", z."YEAR", z."PROP_ID" from (
select rsg.resident_id as "RES_ID", extract ( quarter from rsg.on_this_date) as "QUARTER" , extract (year from rsg.on_this_date) as "YEAR", rsg.life_domain, r.prop_id as "PROP_ID" from resident_score_goal rsg
join resident r on r.resident_id = rsg.resident_id 
group by rsg.resident_id, extract (quarter from rsg.on_this_date), extract(year from rsg.on_this_date), rsg.life_domain, r.prop_id ) z
where exists (
select 1 from resident_score_goal where extract (year from on_this_date) = z."YEAR" and extract (quarter from on_this_date) = z."QUARTER" and resident_id = z."RES_ID" and life_domain = 'HOUSING'
)
and exists (
select 1 from resident_score_goal where extract (year from on_this_date) = z."YEAR" and extract (quarter from on_this_date) = z."QUARTER" and resident_id = z."RES_ID" and life_domain = 'NETWORK SUPPORT'
)
and exists (
select 1 from resident_score_goal where extract (year from on_this_date) = z."YEAR" and extract (quarter from on_this_date) = z."QUARTER" and resident_id = z."RES_ID" and life_domain = 'EMPLOYMENT'
)
and exists (
select 1 from resident_score_goal where extract (year from on_this_date) = z."YEAR" and extract (quarter from on_this_date) = z."QUARTER" and resident_id = z."RES_ID" and life_domain = 'EDUCATION'
)
and exists (
select 1 from resident_score_goal where extract (year from on_this_date) = z."YEAR" and extract (quarter from on_this_date) = z."QUARTER" and resident_id = z."RES_ID" and life_domain = 'MONEY MANAGEMENT'
)
and exists (
select 1 from resident_score_goal where extract (year from on_this_date) = z."YEAR" and extract (quarter from on_this_date) = z."QUARTER" and resident_id = z."RES_ID" and life_domain = 'HOUSEHOLD MANAGEMENT'
)
group by z."RES_ID", z."QUARTER", z."YEAR", z."PROP_ID"
;


--Resident by agency referred
CREATE VIEW AGENCY_RESIDENT_VIEW
AS
SELECT z."AGENCY", z."RES_ID", z."QUARTER", z."YEAR", P.PROP_ID as "PROP_ID", P.PROP_NAME as "PROP_NAME" from (
select referral_partner->>'HOUSING' as "AGENCY", resident_id as "RES_ID", extract (quarter from DATE_MODIFIED) as "QUARTER", extract (year from DATE_MODIFIED) as "YEAR" 
from action_plan ap
where referral_partner->>'HOUSING' not in ('')
union
select referral_partner->>'MONEY MANAGEMENT' as "AGENCY", resident_id as "RES_ID", extract (quarter from DATE_MODIFIED) as "QUARTER", extract (year from DATE_MODIFIED) as "YEAR" 
from action_plan ap
where referral_partner->>'MONEY MANAGEMENT' not in ('')
union
select referral_partner->>'EMPLOYMENT' as "AGENCY", resident_id as "RES_ID", extract (quarter from DATE_MODIFIED) as "QUARTER", extract (year from DATE_MODIFIED) as "YEAR" 
from action_plan ap
where referral_partner->>'EMPLOYMENT' not in ('')
union
select referral_partner->>'EDUCATION' as "AGENCY", resident_id as "RES_ID", extract (quarter from DATE_MODIFIED) as "QUARTER", extract (year from DATE_MODIFIED) as "YEAR" 
from action_plan ap
where referral_partner->>'EDUCATION' not in ('')
union
select referral_partner->>'NETWORK SUPPORT' as "AGENCY", resident_id as "RES_ID", extract (quarter from DATE_MODIFIED) as "QUARTER", extract (year from DATE_MODIFIED) as "YEAR" 
from action_plan ap
where referral_partner->>'NETWORK SUPPORT' not in ('')
union
select referral_partner->>'HOUSEHOLD MANAGEMENT' as "AGENCY", resident_id as "RES_ID", extract (quarter from DATE_MODIFIED) as "QUARTER", extract (year from DATE_MODIFIED) as "YEAR" 
from action_plan ap
where referral_partner->>'HOUSEHOLD MANAGEMENT' not in ('')
 ) Z
JOIN RESIDENT R on R.RESIDENT_ID = Z."RES_ID"
JOIN PROPERTY P on P.PROP_ID = R.PROP_ID
ORDER BY z."AGENCY";


--Resident Who moved atleast one level up
CREATE VIEW MOVING_UP_RESIDENTS_VIEW
AS
select z."RES_ID" as "RES_ID", z."QUARTER" as "QUARTER", z."YEAR" as "YEAR", P.PROP_ID as "PROP_ID", P.PROP_NAME as "PROP_NAME", z."LIFE_DOMAIN" as "LIFE_DOMAIN", z."SCORE" as "SCORE" from (
select rsg.resident_id as "RES_ID", extract (quarter from rsg.on_this_date) as "QUARTER", extract (year from rsg.on_this_date) as "YEAR" , rsg.life_domain as "LIFE_DOMAIN", rsg.score as "SCORE"
from resident_Score_goal rsg
join resident_score_goal rsg1 on rsg1.resident_id = rsg.resident_id and rsg1.life_domain = rsg.life_domain and rsg.score > rsg1.score and rsg.on_this_date > rsg1.on_this_Date
)z
JOIN RESIDENT R on R.RESIDENT_ID = z."RES_ID"
JOIN PROPERTY P on P.PROP_ID = R.PROP_ID
order by z."YEAR", z."QUARTER";

--Resident Who moved one more level down
CREATE VIEW MOVING_DOWN_RESIDENTS_VIEW
AS
select z."RES_ID" as "RES_ID", z."QUARTER" as "QUARTER", z."YEAR" as "YEAR", P.PROP_ID as "PROP_ID", P.PROP_NAME as "PROP_NAME", z."LIFE_DOMAIN" as "LIFE_DOMAIN", z."SCORE" as "SCORE" from (
select rsg.resident_id as "RES_ID", extract (quarter from rsg.on_this_date) as "QUARTER", extract (year from rsg.on_this_date) as "YEAR" , rsg.life_domain as "LIFE_DOMAIN", rsg.score as "SCORE"
from resident_Score_goal rsg
join resident_score_goal rsg1 on rsg1.resident_id = rsg.resident_id and rsg1.life_domain = rsg.life_domain and rsg.score < rsg1.score and rsg.on_this_date > rsg1.on_this_Date
)z
JOIN RESIDENT R on R.RESIDENT_ID = z."RES_ID"
JOIN PROPERTY P on P.PROP_ID = R.PROP_ID
order by z."YEAR", z."QUARTER";


CREATE VIEW OUTCOMES_ACHIEVED_VIEW
AS
SELECT z."RES_ID" as "RES_ID", z."OUTCOMES" as "OUTCOMES", z."QUARTER" as "QUARTER", z."YEAR" as "YEAR", P.PROP_ID as "PROP_ID", P.PROP_NAME as "PROPERTY" from (
select OUTCOME_ACHIEVED ->> 'HOUSING' as "OUTCOMES", extract (quarter from date_modified) as "QUARTER", extract(year from date_modified) as "YEAR", RESIDENT_ID as "RES_ID" from ACTION_PLAN
WHERE  OUTCOME_ACHIEVED ->> 'HOUSING' not in ('')
UNION
select OUTCOME_ACHIEVED ->> 'MONEY MANAGEMENT' as "OUTCOMES", extract (quarter from date_modified) as "QUARTER", extract(year from date_modified) as "YEAR", RESIDENT_ID as "RES_ID" from ACTION_PLAN
WHERE  OUTCOME_ACHIEVED ->> 'MONEY MANAGEMENT' not in ('')
UNION
select OUTCOME_ACHIEVED ->> 'EMPLOYMENT' as "OUTCOMES", extract (quarter from date_modified) as "QUARTER", extract(year from date_modified) as "YEAR", RESIDENT_ID as "RES_ID" from ACTION_PLAN
WHERE  OUTCOME_ACHIEVED ->> 'EMPLOYMENT' not in ('')
UNION
select OUTCOME_ACHIEVED ->> 'EDUCATION' as "OUTCOMES", extract (quarter from date_modified) as "QUARTER", extract(year from date_modified) as "YEAR", RESIDENT_ID as "RES_ID" from ACTION_PLAN
WHERE  OUTCOME_ACHIEVED ->> 'EDUCATION' not in ('')
UNION
select OUTCOME_ACHIEVED ->> 'NETWORK SUPPORT' as "OUTCOMES", extract (quarter from date_modified) as "QUARTER", extract(year from date_modified) as "YEAR", RESIDENT_ID as "RES_ID" from ACTION_PLAN
WHERE  OUTCOME_ACHIEVED ->> 'NETWORK SUPPORT' not in ('')
UNION
select OUTCOME_ACHIEVED ->> 'HOUSEHOLD MANAGEMENT' as "OUTCOMES", extract (quarter from date_modified) as "QUARTER", extract(year from date_modified) as "YEAR", RESIDENT_ID as "RES_ID" from ACTION_PLAN
WHERE  OUTCOME_ACHIEVED ->> 'HOUSEHOLD MANAGEMENT' not in ('')
) z
JOIN RESIDENT R on R.RESIDENT_ID = Z."RES_ID"
JOIN PROPERTY P on P.PROP_ID = R.PROP_ID
ORDER BY z."OUTCOMES";


CREATE VIEW RESIDENT_ACTION_PLAN_VIEW
AS
select z."RES_ID", z."LIFE_DOMAIN", z."PLAN_OF_ACTION", Z."QUARTER", z."YEAR", P.PROP_ID as "PROP_ID", P.PROP_NAME as "PROPERTY" from (
select RESIDENT_ID as "RES_ID", 'HOUSING' as "LIFE_DOMAIN", plan_of_Action ->>'HOUSING' as "PLAN_OF_ACTION", EXTRACT (QUARTER from date_modified) as "QUARTER", extract (year from date_modified) as "YEAR" from action_plan
where plan_of_Action ->>'HOUSING' not in ('')
UNION ALL
select RESIDENT_ID as "RES_ID",'MONEY MANAGEMENT' as "LIFE_DOMAIN", plan_of_Action ->>'MONEY MANAGEMENT' as "PLAN_OF_ACTION",  EXTRACT (QUARTER from date_modified) as "QUARTER", extract (year from date_modified) as "YEAR" from action_plan
where plan_of_Action ->>'MONEY MANAGEMENT' not in ('')
UNION ALL
select RESIDENT_ID as "RES_ID",'EMPLOYMENT' as "LIFE_DOMAIN", plan_of_Action ->>'EMPLOYMENT' as "PLAN_OF_ACTION", EXTRACT (QUARTER from date_modified) as "QUARTER", extract (year from date_modified) as "YEAR" from action_plan
where plan_of_Action ->>'EMPLOYMENT' not in ('')
UNION ALL
select RESIDENT_ID as "RES_ID",'EDUCATION' as "LIFE_DOMAIN", plan_of_Action ->>'EDUCATION' as "PLAN_OF_ACTION", EXTRACT (QUARTER from date_modified) as "QUARTER", extract (year from date_modified) as "YEAR" from action_plan
where plan_of_Action ->>'EDUCATION' not in ('')
UNION ALL
select RESIDENT_ID as "RES_ID",'NETWORK SUPPORT' as "LIFE_DOMAIN", plan_of_Action ->>'NETWORK SUPPORT' as "PLAN_OF_ACTION", EXTRACT (QUARTER from date_modified) as "QUARTER", extract (year from date_modified) as "YEAR" from action_plan
where plan_of_Action ->>'NETWORK SUPPORT' not in ('')
UNION ALL
select RESIDENT_ID as "RES_ID",'HOUSEHOLD MANAGEMENT' as "LIFE_DOMAIN", plan_of_Action ->>'HOUSEHOLD MANAGEMENT' as "PLAN_OF_ACTION", EXTRACT (QUARTER from date_modified) as "QUARTER", extract (year from date_modified) as "YEAR" from action_plan
where plan_of_Action ->>'HOUSEHOLD MANAGEMENT' not in ('')) z
JOIN RESIDENT R on R.RESIDENT_ID = Z."RES_ID"
JOIN PROPERTY P on P.PROP_ID = R.PROP_ID;


CREATE VIEW REFERRAL_REASON_VIEW
AS
select z."RES_ID" as "RES_ID", z."REASON" as "REASONS", z."QUARTER" as "QUARTER", z."YEAR" as "YEAR", P.PROP_ID as "PROP_ID", P.PROP_NAME AS "PROPERTY" from (
select 'Childcare/afterschool care' as "REASON", extract (quarter from date_modified) as "QUARTER", extract( year from date_modified) as "YEAR", resident_id as "RES_ID" from referral_form
where referral_reason ->> 'Childcare/afterschool care' not in ('', 'false')
UNION ALL
select 'Education/job training' as "REASON", extract (quarter from date_modified) as "QUARTER", extract( year from date_modified) as "YEAR", resident_id as "RES_ID" from referral_form
where referral_reason ->> 'Education/job training' not in ('', 'false')
UNION ALL
select 'Employment/job readiness' as "REASON", extract (quarter from date_modified) as "QUARTER", extract( year from date_modified) as "YEAR", resident_id as "RES_ID" from referral_form
where referral_reason ->> 'Employment/job readiness' not in ('', 'false')
UNION ALL
select 'Healthcare/medical issues' as "REASON", extract (quarter from date_modified) as "QUARTER", extract( year from date_modified) as "YEAR", resident_id as "RES_ID" from referral_form
where referral_reason ->> 'Healthcare/medical issues' not in ('', 'false')
UNION ALL
select 'Housekeeping/home management' as "REASON", extract (quarter from date_modified) as "QUARTER", extract( year from date_modified) as "YEAR", resident_id as "RES_ID" from referral_form
where referral_reason ->> 'Housekeeping/home management' not in ('', 'false')
UNION ALL
select 'Lease violation for:' as "REASON", extract (quarter from date_modified) as "QUARTER", extract( year from date_modified) as "YEAR", resident_id as "RES_ID" from referral_form
where referral_reason ->> 'Lease violation for:' not in ('', 'false')
UNION ALL
select 'Non/late payment of rent' as "REASON", extract (quarter from date_modified) as "QUARTER", extract( year from date_modified) as "YEAR", resident_id as "RES_ID" from referral_form
where referral_reason ->> 'Non/late payment of rent' not in ('', 'false')
UNION ALL
select 'Noticeable change in:' as "REASON", extract (quarter from date_modified) as "QUARTER", extract( year from date_modified) as "YEAR", resident_id as "RES_ID" from referral_form
where referral_reason ->> 'Noticeable change in:' not in ('', 'false')
UNION ALL
select 'Other:' as "REASON", extract (quarter from date_modified) as "QUARTER", extract( year from date_modified) as "YEAR", resident_id as "RES_ID" from referral_form
where referral_reason ->> 'Other:' not in ('', 'false')
UNION ALL
select 'Resident-to-resident conflict issues' as "REASON", extract (quarter from date_modified) as "QUARTER", extract( year from date_modified) as "YEAR", resident_id as "RES_ID" from referral_form
where referral_reason ->> 'Resident-to-resident conflict issues' not in ('', 'false')
UNION ALL
select 'Safety' as "REASON", extract (quarter from date_modified) as "QUARTER", extract( year from date_modified) as "YEAR", resident_id as "RES_ID" from referral_form
where referral_reason ->> 'Safety' not in ('', 'false')
UNION ALL
select 'Suspected abuse/domestic violence/exploitation' as "REASON", extract (quarter from date_modified) as "QUARTER", extract( year from date_modified) as "YEAR", resident_id as "RES_ID" from referral_form
where referral_reason ->> 'Suspected abuse/domestic violence/exploitation' not in ('', 'false')
UNION ALL
select 'Transportation' as "REASON", extract (quarter from date_modified) as "QUARTER", extract( year from date_modified) as "YEAR", resident_id as "RES_ID" from referral_form
where referral_reason ->> 'Transportation' not in ('', 'false')
UNION ALL
select 'Utility Shut-off, scheduled for (Date):' as "REASON", extract (quarter from date_modified) as "QUARTER", extract( year from date_modified) as "YEAR", resident_id as "RES_ID" from referral_form
where referral_reason ->> 'Utility Shut-off, scheduled for (Date):' not in ('', 'false')) z
JOIN RESIDENT R on R.RESIDENT_ID = Z."RES_ID"
JOIN PROPERTY P on P.PROP_ID = R.PROP_ID;



--Example for Questionnaire
--select aq.question_number, aq.question, c.choice from question_choice qc
--join assessment_questionnaire aq on aq.question_id = qc.question_id and aq.life_domain = 'HOUSING'
--join choice c on c.choice_id = qc.choice_id;

Commit;