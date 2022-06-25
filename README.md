# Nace-Data-Service

Spring Boot app.

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/javase/11-0-12-relnotes.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run this application on your local machine. One way is to execute the `main` method in the `com.dbank.ist.referencedata.nace.NaceServiceApplication` class from your IDE.

Or you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
Alternatively you can use the [Bootable jar](https://docs.spring.io/spring-boot/docs/1.3.8.RELEASE/reference/html/using-boot-running-your-application.html) like so:

(The first command builds the bootable jar)
```shell
mvn package
java -jar target/nace-service-0.0.1-SNAPSHOT-boot.jar 
```

## Specification

### Swagger
The swagger-ui is available at : http://localhost:10002/swagger-ui/index.html#/nace-resource

### Port Number

The default port is configured as 10002. You can choose to override the same by passing server.port via tha args.

```shell
java -jar target/nace-service-0.0.1-SNAPSHOT-boot.jar  --server.port=10005
```