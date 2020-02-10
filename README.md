# employee-portal
<b>Employee Portal</b>

The is an application with basic features like :
1. Retrieving and viewing a list of employees containg name,salary and hire date.
2. Viewing specific empoyee details
3. Adding new employee record
4. Deleting existing employee record.


<b>This repository contains following :</b>
1. employee-portal-api (Spring boot rest api)
2. API doc generated via swagger)

<b>Tech-stack :</b>

JAVA 11
Spring Boot 2.2.4
Liquibase 3.6.2
In memory H2 DB

<b>Test-cases :</b> 

1. com.inovia.assignment.dao.EmployeeDAOTest 
2. com.inovia.assignment.service.EmployeeServiceTest 
3. com.inovia.assignment.rest.EmployeeControllerTest


<b>Getting Started</b>

<b>Pre Requisites</b>
Java 11


<b>To Use</b>
1. You can import api project as an existing maven app in eclipse/Intellij.

<b>To Test</b>
1. Unzip provided zip file and go to root folder(employee-portal-api) of application
2. open cmd prompt there, execute following command and wait for it to build and deploy API.

	Linux:
		`./mvnw spring-boot:run`

	Windows:
		`mvnw.cmd spring-boot:run`
3. To check whether application is up or not via http://localhost:9090/actuator/health
4. You can test API is working via http://localhost:9090/employees
5. You can see API docs via http://localhost:9090/swagger-ui.html
6. You will see the app running.

For any questions/comments feel free to reach me at <b>shalinidixit.stkhlm@gmail.com</b>
<b>Author</b>
Shalini Dixit.
