# <ins>*Resident Resource Network - Hope* </ins>

### Software Download and Setup

> - Download JDK1.8 from [JDK](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
> - Download [Maven](https://maven.apache.org/download.cgi#) choose zip one and unzip in any location and give location of your repository where you want maven to download all related dependencies

 ![maven Settings](src/main/resources/public/images/project_Setup/mavenSettings.png)
 
> - Download [Eclipse](https://www.eclipse.org/downloads/) or any similar Development Space
> - Create a folder "workspace" in any drive and open eclipse Project here.
> - Go to Help Menu under Eclipse then Marketplace and download plugins atleast (eclipse web developer Tool, Dbeaver, Egit)
> - It may ask to restart eclipse several time after each plugin as shown below but you can do so once after installing all the plugins

 ![MarketPlace Step](src/main/resources/public/images/project_Setup/EclipsePlugins.png)

> - Configure JDK Version in Eclipse as shown below:
 
 ![JRE Settings](src/main/resources/public/images/project_Setup/JDK-Setting.png)

> - Download [lombok](https://projectlombok.org/download) and follow instruction from same website. find your eclipse.ini file and add what shown in image below, restart eclipse (Lombok is useful in compiling @Data Annotation locally to support getter/setter else you will get compile time errors on all getter setter)
 
 ![Lombok Settings](src/main/resources/public/images/project_Setup/lombok-Setting.png)
 
> - Eclipse Maven setting --> window preferences type "maven" and follow below screen shots:

 ![JRE Settings](src/main/resources/public/images/project_Setup/eclipse-maven-setting.png)

 ![JRE Settings](src/main/resources/public/images/project_Setup/eclipse-maven-setting2.png)
 
> - Download [DBeaver](https://dbeaver.io/download/)

> - Git Clone project from github release 1 Branch - [release1](https://github.com/FFGResidentResource/hope.git)

### Database configuration

> we are using Postgresql in our project for backend Relational Database. Please follow link here to create your own account:

> [ElephantSQL](https://www.elephantsql.com/) - Choose TinyTurle Free Plan while creating instance (it gives you 20 MB which is more than enough to Store 1000s of record in each table)
 
> After creating instance go to "Details" Tab and it will look like this

 ![DB Details](src/main/resources/public/images/project_Setup/ElephantSQL-DB-Example.png)
 
> Let's Open this DB on DBeaver now, open DBeaver that you downloaded from above step and File -> New -> DBeaver (Database Connection) as shown in iamge below:

 ![DB Connection Details](src/main/resources/public/images/project_Setup/dbeaver-conn-1.png)

 ![DB Connection Details](src/main/resources/public/images/project_Setup/dbeaver-conn-2.png)

> Put values from your elephantSQL Account as shown below:

 ![DB Connection Details](src/main/resources/public/images/project_Setup/dbeaver-conn-3.png) 
 
> Copy entire contents of data.sql from github location - [data.sql](https://github.com/FFGResidentResource/hope/blob/Release1/src/main/resources/data/data.sql) and put it in new SQL Editor of DBeaver and execute entire SQL Script (Alt +X) in Dbeaver. This will create All tables/ views and insert initial Data

 ![DB Connection Details](src/main/resources/public/images/project_Setup/dbeaver-conn-4.png) 
 
> After Executing Script your Table and views looks like as below:

 ![DB Connection Details](src/main/resources/public/images/project_Setup/dbeaver-conn-5.png) 
 