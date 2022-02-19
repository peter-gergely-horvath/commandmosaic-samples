# memoapp-springboot-jpa-angular

This project demonstrates using CommandMosaic for a 
Spring Boot-JPA application, with an Angular frontend.

## Prerequisites

Java 8 - Download and install the JDK 8.  
Node.js - Download and install Node JS.   
Angular CLI - used to bootstrap the Angular frontend.

## Project modules

The project build is split into two modules: 

 * **backend** - contains the Java server code, with CommandMosaic library
 * **frontend** - contains the Angular based webapp. Maven is used to 
                    invoke the `npm`-based Angular build and package the output.


## `backend` module

Backend depends on frontend, so that the frontend is built first,
then the resulting artifacts can be packaged into the executable
JAR file of the backend. 

This module demonstrates the implementation of Java services using
CommandMosaic: all service calls made by the frontend are implemented
as CommandMosaic Command classes.


## `frontend` module

This module contains a standard Angular application: Maven is merely
used to prepare and invoke the `npm`-based Angular build.

## Building the  project

Simply run `mvn clean package` in the project root.

The runnable application JAR will be located in `backend/target`


## How CommandMosaic is used within the project

CommandMosaic is used in conjunction with Spring Boot: command dispatching
happens through one, single `HttpRequestHandler` (a single endpoint URL). 
Client code executes HTTP POSTs against it, which causes different commands 
to be dispatched on the server.

## CommandMosaic in the backend and frontend

Please check READMEs and documentation within both the 
`backend` and `frontend` modules for further information.
