# <ins>*Resident Resource Network - Hope* </ins>

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
 
 
### Production Deployment and prerequisites

In order to deploy to production, please see database-setup-prod.dml.sql you need to modify this file and add few things: (look for all "TODO" in this file) and add accordingly

> - Cross check Property Table and validate each insert statements to see if properties names, county , state , city all looks correct 
> - Create Insert Statements for REFERRAL_PARTNER TABLE - (basically All Agencies names)
> - Insert row into Service coordinator table. <ins> Remember to always insert Encrypted password in DB not direct password </ins>
> - how to Encrypt Password - see image below

 ![encryptPassword](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/public/images/project_setup/How-to-EncryptPassword.png?raw=true)
 
> - use this encrypted password for your given password and go to section writeup insert into service_coordinator dml (refer example in data.sql for same)

> - once you do this you have to write up insert into user_role table as well (user_id , role_id ) is must. user_id = sc_id from service_coordinator table. ROLE_Id = 1 or 2 - Look for TODO in this .dml.sql file.

> - Once this file - database-setup-prod.dml.sql is modified, saved,  copy paste entire contents into DBeaver tool into Logged in production Schema (this is one time task) - once SC started using website - DO NOT run contents of this file as it will overwrite database.

> - this concludes Database setup
