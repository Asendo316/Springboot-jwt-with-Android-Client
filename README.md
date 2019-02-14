# Springboot-jwt-with-Android-Client
A simple Spring Boot Application for Securing a REST API with JSON Web Token (JWT)and calling it in a client android application
This application can be used start your spring boot REST API project with a fully functional security module and an android application to communicate with the api

## Prerequisites

* Spring Boot go to http://docs.spring.io/spring-boot/docs/1.5.3.RELEASE/reference/htmlsingle/ to learn more about spring boot
* JWT to https://jwt.io/ to learn more about JSON WEB 
* H2 Database Engine - Go to www.h2database.com to learn more about H2 Database Engine
* https://github.com/google/gson to learn about the JSON Deserializer 
* https://mvnrepository.com/artifact/org.apache.httpcomponents/httpmime 
* https://www.getpostman.com/ to learn more about testing REST APIs with the PostMan Application

## To Run the REST API

* Build the Project
- On a Windows Machine:
  Navigate to the build director i.e /target in CMD and run  java -jar springboot-almondrest-service-0.0.1-SNAPSHOT.jar 
- On a Linux|Unix Machine: run mvn clean package then run the resulting jar as any other executable ./springboot-almondrest-service-0.0.1-SNAPSHOT.jar 

## To test the REST API

* Launch PostMan
* Under Authorization type select Basic Auth and in the username field enter **'testjwtclientid'** and for the password enter **'XY7kmzoNzl100'**
* Under headers you would add a new key **'Content-Type'** with Value **'application/x-www-form-urlencoded'**
* Inthe body section would select the x-www-form-urlencoded format and create three keys **'grant_type'**,**'username'** and **'password'** with values **'password'**,**'admin.admin'**, and **'jwtpass'** respectively
* To generate a token, you would send a post request to this url http://localhost:8080/oauth/token 

## To test the mobile APP

* Build the project
* Run it in your prefred emulator
* You would recieve a response containing the JWT Token 
