# Dr. Dewan Grading Proxy Server

## Required Software
- Java
- Maven
- Quarkus
- PostgreSQL

## Populating the PostgreSQL Database

In order to run the PostgreSQL database, you must have it installed, but once you have done that, you should be able to just dump the data from the following file into your database to create it and populate it with the right data.

The file mentioned can be found in the `src/main/resources/dewan-database-with-data.sql`

## How to create and view assignments

If you go to the `src/main/java/api/calls/internal/AssignmentsResource` you will see two endpoints: one for creation and another for visualization.

By running `quarkus dev` and going to the following url: http://localhost:8080/q/swagger-ui/ . You should be able to see all the current application end points, like this: 

![Screen Shot 2023-01-27 at 8.29.57 AM.png](..%2F..%2FDesktop%2FScreen%20Shot%202023-01-27%20at%208.29.57%20AM.png)

If you go to the `GET /assignment-information/` end point and execute it, you should be able to see something like this: 

![Screen Shot 2023-01-27 at 8.31.17 AM.png](..%2F..%2FDesktop%2FScreen%20Shot%202023-01-27%20at%208.31.17%20AM.png)

This shows all the current created assignments, in order to create one, just use the POST end point.

## Pulling Logs from Server

Open the `src/main/java/api/calls/internal/LogsResource.java` file, this is the method use to pull data from the server.

The file uses the specifications set in `src/main/java/api/calls/internal/ServerInputWrapper.java` to retrieve the data. In order to change the data you are requesting, just change the constants in the file.

When you are ready to start pulling data from the server, just try it out.

## Creating CSV Files from Database Rows

In order to create database files for analysis, all you need to do is go to `src/main/java/api/calls/internal/CSVResource.java` that is the code to create anonymized CSV files for each student. 

It can be called in the same way as other endpoints, by simply going to the following directory: http://localhost:8080/q/swagger-ui/ . 

In the end, the files should appear in the `src/main/resources/student-logs/` folder.

## Running the website and having information fed through this server

The front-end page is located in the `src/main/webapp/my-app` directory, if you navigate to it and run `npm start`, you should see the website show up.

Make sure to run the `quarkus dev` command in the main directory of the project before, so that the server can feed data to the front-end application.

# How to install software

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
