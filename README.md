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

### Excel uploads

You can upload excels for bulk uploading Nace records. I am using [Ozlerhakan's library](https://github.com/ozlerhakan/poiji) to parse the excel
and it consumes xls files very well. (can use a sample xls file added under resources)

(Do pay attentions to the headers they are important)


| order  | level | code | parent | description     | thisItemIncludes  | thisItemAlsoIncludes  | rulings          | thisItemExcludes   | referenceToIsicRev4 |
|--------|:-----:|-----:|--------|-----------------|-------------------|-----------------------|------------------|--------------------|---------------------|
| 398481 |   1   |    A |        | AGRICULTURE  A  | Includes Data  A  | AlsoIncludes Data  A  | rulings Data A   | exclusion Data A   | refrences data A    |
| 398485 |   2   |  A.1 | A      | SUB-AGRICULTURE | Includes Data A.1 | AlsoIncludes Data A.1 | rulings Data A.1 | exclusion Data A.1 | refrences data A.1  |