# <ins>*Resident Resource Network - Hope *  </ins>

### Project Architecture

![Architecture](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/architecture.png?raw=true)

### * First time users for local setup please jump to * <ins> Software Download and Setup Step </ins>

### Technological Stack for this Project

> - Java 1.8 or higher 
> - Spring Boot 2.x or higher (see version in pom.xml) - spring boot comes with embedded tomcat
> - Thymeleaf for UI bindings to Backing bean
> - Bootstrap Css for styling
> - jQuery for browser scripting and traversing
> - PostgreSQL for database - our database can be hosted online like in elephantsql platform
> - Datatables.net API for all tabular presentation
> - C3 D3 Js for all Charting in reports Tab.

Java / spring - Intermediate knowledge is required
Others (thymeleaf/ bootstrap / jquery/ etc..) - Beginners knowledge should be enough to maintain project.
PostgreSql - Good to have SQL knowledge that should be enough.

 
### Production Deployment and prerequisites

In order to deploy to production, please see database-setup-prod.dml.sql you need to modify this file and add few things: (look for all "TODO" in this file) and add accordingly

> - Cross check Property Table and validate each insert statements to see if properties names, county , state , city all looks correct 
> - Create Insert Statements for REFERRAL_PARTNER TABLE - (basically All Agencies names)
> - Insert row into Service coordinator table. <ins> Remember to always insert Encrypted password in DB not direct password </ins>
> - how to Encrypt Password - see image below

 ![encryptPassword](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/How-to-EncryptPassword.png?raw=true)
 
> - use this encrypted password for your given password and go to section writeup insert into service_coordinator dml (refer example in data.sql for same)

> - once you do this you have to write up insert into user_role table as well (user_id , role_id ) is must. user_id = sc_id from service_coordinator table. ROLE_Id = 1 or 2 (1 = ROLE_ADMIN and 2 = ROLE_USER) - Look for TODO in this .dml.sql file.

> - Once this file - database-setup-prod.dml.sql is modified, saved,  copy paste entire contents and run in production Schema (this is one time task) - once SC started using website - <ins> *DO NOT run contents of this file as it will overwrite database.* </ins>

> - This concludes Database setup

### Deploy to Cloud - May have to look Microsoft Azure Spring boot deployment and <ins> how routing <ins> is done. (we have shown in past how to deploy with Pivotal Cloud Foundry)
	
	> - routing is nothing but global load balancer url or host url of the project that is connected to source IP of the system
	> - Memory instance and all have to be defined as per Azure documentation.


### Database Management

> - If you go with ElephantSQL, have their own database backup so you not going to loose any data in case of disaster.
> - Once resident is in System he/she should be always in system and should never be deleted from the System.
> - Quarterly and Demographics Report includes Inactive and Active Resident both to produce accurate number of resident served for a given year.
> - Action Plan, Contact Notes and all 7 life domain assessment maintains system of record by "on_this_date" so you will find this field in these table.
> - There may be scenario like in 10 years if there are tons of assessment per resident and you want to archive those you should only archive these table - Action Plan / Contact Notes and Resident Assessment Questionnaire but System should be performant enough to handle data quantity easily.
> - Its good idea to go over database-setup-prod.dml.sql to understand database in depth and then also understand how views are written.

 ![ERDiagram](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/ER-Diagram.png?raw=true)

> - we extracted ER Diagram from DBeaver tool and then saved as PNG in repo, if more tables are created in future ER Diagram needs to be recreated and export as PNG.

<ins> *hint:* </ins> please see which dataTaype is json , date, number, varchar etc to look closely how data is getting stored in each

> - Views are created to make data extract from json field easy based on per life domain and so on.


### Project Screen Shots:

![Admin vs Non Admin SC](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/AdminVsNonAdminSCView.png?raw=true)
![AllResident](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/All_Resident.PNG?raw=true)
![onboardingModify](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/OnboardingModify.PNG?raw=true)
![referralForm](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/referralform.PNG?raw=true)
![intakeForm](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/intakeForm1.PNG?raw=true)
![intakeCont...](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/intakeform2.PNG?raw=true)
![Housing Questionnaire](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/Q_Housing.PNG?raw=true)
![Money Mgmt Questionnaire](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/Q_MoneyManagement.PNG?raw=true)
![Employment Questionnaire](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/Q_Employment.PNG?raw=true)
![Education Questionnaire](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/Q_Education.PNG?raw=true)
![NetworkSupport Questionnaire](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/Q_NetworkSupport.PNG?raw=true)
![Household Questionnaire](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/Q_Household.PNG?raw=true)
![DisabilityPhysicalHealth Questionnaire](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/Q_DisabilityHealth.PNG?raw=true)
![actionPlan](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/Action_Plan1.PNG?raw=true)
![actionPlan Contd..](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/Action_Plan2.PNG?raw=true)
![Contact notes](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/ContactNotes.PNG?raw=true)
![Report1](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/Reports1.PNG?raw=true)
![Report2](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/Reports2.PNG?raw=true)
![Report3](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/Reports3.PNG?raw=true)
![Report4](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/Report4.PNG?raw=true)
![Report5](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/pages_screenshots/Report5.PNG?raw=true)



### Software Download and Setup

> - Download JDK1.8 from [JDK](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
> - Download [Maven](https://maven.apache.org/download.cgi#) choose zip one and unzip in any location and give location of your repository where you want maven to download all related dependencies

 ![maven Settings](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/mavenSettings.png?raw=true)
 
> - Download [Eclipse](https://www.eclipse.org/downloads/) or any similar Development Space
> - Create a folder "workspace" in any drive and open eclipse Project here.
> - Go to Help Menu under Eclipse then Marketplace and download plugins atleast (eclipse web developer Tool, Dbeaver, Egit)
> - It may ask to restart eclipse several time after each plugin as shown below but you can do so once after installing all the plugins

 ![MarketPlace Step](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/EclipsePlugins.png?raw=true)

> - Configure JDK Version in Eclipse as shown below:
 
 ![JRE Settings](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/JDK-Setting.png?raw=true)

> - Download [lombok](https://projectlombok.org/download) and follow instruction from same website. find your eclipse.ini file and add what shown in image below, restart eclipse (Lombok is useful in compiling @Data Annotation locally to support getter/setter else you will get compile time errors on all getter setter)
 
 ![Lombok Settings](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/lombok-setting.png?raw=true)
 
> - Eclipse Maven setting --> window preferences type "maven" and follow below screen shots:

 ![Maven Setting 1](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/eclipse-maven-setting.png?raw=true)

 ![Maven Setting 2](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/eclipse-maven-setting2.png?raw=true)
 
> - Download [DBeaver](https://dbeaver.io/download/)

> - Git Clone project from github release 1 Branch - [release1](https://github.com/FFGResidentResource/hope.git)

### Database configuration

> - we are using Postgresql in our project for backend Relational Database. Please follow link here to create your own account:

> - [ElephantSQL](https://www.elephantsql.com/) - Choose <ins> TinyTurle Free </ins> Plan while creating instance (it gives you 20 MB which is more than enough to Store 1000s of record in each table)
 
> - After creating instance go to "Details" Tab and it will look like this

 ![DB Details](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/ElephantSQL-DB-Example.png?raw=true)
 
> - Let's Open this DB on DBeaver now, open DBeaver that you downloaded from above step and File -> New -> DBeaver (Database Connection) as shown in iamge below:

 ![DB Connection Details 1](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/dbeaver-conn-1.png?raw=true)

 ![DB Connection Details 2](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/dbeaver-conn-2.png?raw=true)

> - Put values from your elephantSQL Account as shown below:

 ![DB Connection Details 3](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/dbeaver-conn-3.png?raw=true) 
 
> - Copy entire contents of data.sql from github location - [data.sql](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/data/data.sql) and put it in new SQL Editor of DBeaver and execute entire SQL Script (Alt +X) in Dbeaver. This will create All tables/ views and insert initial Data

 ![DB Connection Details 4](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/dbeaver-conn-4.png?raw=true) 
 
> - After Executing Script your Table and views looks like as below:

 ![DB Connection Details 5](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/dbeaver-conn-5.png?raw=true) 

> - Please go through data.sql file completely and you will understand how each views are written for complete Reporting.

### Configure Local Workspace 

> - Clone Projet from GitHub and import as git project into Eclipse

 ![Git Clone](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/clone-git-project.png?raw=true)
 
> - Configure Eclipse Maven Run Configurations, Go to "Run" and "Do run configurations" right click above maven build and choose "Create new"
 
  ![maven build](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/maven-build-run-config.png?raw=true)
  
> - clean build Project go to Project and choose Clean build as shown in image below:

 ![maven build](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/clean-build-project.png?raw=true)
  
> - In order to deploy to cloud you may also need to generate JAR add one more Maven jar Config as shown in picture below, after run you will see jar created in target folder (Refresh Target folder).

  ![Maven Jar Build](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/maven-jarbuild-run-config.png?raw=true)

### Start Server

> - open Application.properties under resources and put url, username and password from ElephantSQL Details tab after logging in ElephanSQL Account

 ![Maven Jar Build](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/jdbc-setup-and-server-startup.png?raw=true)

> - Configure Spring Boot Settings (You may have to add Spring Tool Suite 4 from Eclipse Marketplace as well) and see below Screenshot:

 ![Maven Jar Build](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/run-project-localhost.png?raw=true)

> - open http://localhost:8080 Looks port in server logs where your server started. This port can specified in application.properties if you want to override on top of 8080, google how to override spring boot port.

### Deploy on Cloud - TBD

[Azure Spring Cloud](https://tanzu.vmware.com/content/webinars/dec-5-introducing-azure-spring-cloud-a-managed-runtime-for-spring-based-apps-webinar)

### How to debug UI 

 ![UI Debugging Examnple](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/how-to-debug-ui.png?raw=true)
 
 ![UI Debugging cond...](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/how-to-debug-ui2.png?raw=true)
 
### How to use onboarding Modify functionality

> Together with name, property search we have also enable email, text, voicemail, address search as well. Please see screen shot below:

 ![onboarding modify](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/how-to-use-onboarding-modify.png?raw=true)
 

### When you get following page meaning you are missing entry in USER_ROLE Table

![Common Error](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/common_error.PNG?raw=true)


