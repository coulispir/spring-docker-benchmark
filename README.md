<p align="center">
  <a href="https://www.theblueground.com/">
    <img src="blueground.svg" width="200" alt="Blueground Logo">
  </a>
  <p align="center">
    <strong>Boilerplate microservice in Kotlin</strong>
  </p>
</p>

## Table of contents

- [Purpose](#purpose)
- [What's inside](#what's-inside)
  - [Prerequisites](#prerequisites)
  - [Main Stack](#main-stack)
  - [Other stuff](#other-stuff)
- [Development](#development)
  - [Build the application](#build-the-application)
  - [Test the application](#test-the-application)
  - [Run the application](#run-the-application)
  - [Check the application](#check-the-application)
  - [Profiles](#profiles)
- [Rest API](#rest-api)
  - [Specification](#specification)
  - [Documentation](#documentation)
  - [Response Model](#response-model)
- [References](#references)

## Purpose

The purpose of this project is to provide the boilerplate code for new Kotlin Spring Boot microservices

## What's inside

### Prerequisites

The project requires Docker and Java 8 pre-installed in your environment

### Main Stack

- [Gradle 5.4.1](https://gradle.org/)
- [Kotlin 1.3.30](https://kotlinlang.org/)
- [Groovy 2.5.6](http://groovy-lang.org/)
- [Spock 1.2](http://spockframework.org/)
- [Spring Boot 2.1.5](https://spring.io/projects/spring-boot)
- [Spring Cloud Greenwich](https://cloud.spring.io/spring-cloud-static/Greenwich.SR1/single/spring-cloud.html)

### Other Stuff

- [Detekt 1.0.0-RC14](https://arturbosch.github.io/detekt/)
- [JaCoCo 0.8.2](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
- [Springfox Swagger 2.9.2](https://springfox.github.io/springfox/docs/current/)
- [AsciiDoc 1.6.0](https://asciidoctor.org/docs/user-manual/)
- [Gradle Git Properties 2.0.0](https://github.com/n0mer/gradle-git-properties)

## Development

### Build the application

```
# build the application with gradle wrapper
./gradlew build
```

### Test the application

```
# run unit tests
./gradlew test

# run functional tests
./gradlew functionalTest

# create report for code coverage
./gradlew jacocoTestReport
```

Check for the generated reports inside `build/` directory 

### Run the application

To successfully run the application with Docker Compose, you need to have a `docker.env` file in the root of the project,
that defines the environment variables passed to the spawned container:

```
cp docker.env.sample docker.env
```

```
# run the application with docker compose (requires to build the application first)
docker-compose up

# stop the application with docker compose
docker-compose down

# run the application with docker compose via gradle
./gradlew dockerStart

# stop the application with docker compose via gradle
./gradlew dockerStop

# run the application with java -jar
java -jar app/build/libs/app-0.0.1-SNAPSHOT.jar
```

### Check the application

To verify that the application is up and running:
```
curl http://localhost:8080/actuator/health
```
...and the response should be the following:
   
```
{
  "status": "UP"
}
```

You can get more details about the health status of all components (eg. db, jms, etc...),
if you access the health endpoint using Basic Authentication:

```
curl -u actuator:actuator http://localhost:8080/actuator/health
```

You can get git and build info details by accessing the `/actuator/info` endpoint:
```
curl -u actuator:actuator http://localhost:8080/actuator/info
```

You can shutdown the application via actuator's endpoint `/actuator/shutdown`

```
curl -u actuator:actuator -X POST http://localhost:8080/actuator/shutdown
```

### Profiles

The profiles which are currently used are `local` (default profile), `test` and `prod`.
Normally for local development the default profile `local`, should be used. 
However in some cases, during development **only**, the special profile `dev` can be used to to speed up development. 
This profile applies some tweaks (disable swagger/actuator, lazy bean initialization) for faster application startup

## Rest API

### Specification
The Rest API is designed based on the principles of [JSON:API specification](https://jsonapi.org/)

### Documentation
In order to document the API, we use the OpenAPI Specification (formerly Swagger Specification).
OpenAPI Specification is an API description format for REST APIs.
The swagger spec file (json) is generated using the Springfox library and is rendered via the Swagger UI. 
In order to view the documentation via the Swagger UI, access the below url

```
http://localhost:8080/swagger-ui.html
```

Springfox and Swagger UI, has limitations in terms of documenting the API with manual documentation and extra examples.
For this reason we use the AsciiDoc plugin in order to combine the auto-generated specification file (`swagger.json`)
and handwritten documentation. That way we provide a complete documentation to the API clients.
In order to generate the documentation file (`documentation.html`) execute the following command:

```
./gradlew asciidoctor
```

In addition, the documentation is also generated during `release` task, where the full build and test lifecycle is executed. 
During a unit test the file `swagger.json` is produced, which is used by `Swagger2Markup` plugin. The plugin produces
auto-generated docs inside `build` folder. Then, during `bootJar` the auto-generated docs are converted to static 
html documentation via `asciiDoctor`, which is finally included in static resources of the produced jar 

In order to view the AsciiDoctor documentation, access the below url which is protected with basic authentication:

```
http://localhost:8080/static/docs/documentation.html
```
 
In order to add a new example in a specific operation in the documentation, perform the following steps:

* Add a nickname in the ApiOperation, eg. `nickname = get-greeting`
* Create a folder `get-greeting` inside `src/docs/paths`
* Add inside a `.adoc` file with the following format: `operation-after-*.adoc`

### Response model
The response object of each API resource is strictly defined and consists of 3 top-level objects:
- `data:` A generic data object which contains the actual response payload (returned in case of successful response)
- `errors:` A list of error objects (returned in case of error)
- `meta:` A generic meta object, which contains metadata information, if any

The objects `data` and `errors` cannot co-exist.

An example of the `data` object is the following:
```
{
  "data": {
    "lang": "en",
    "greeting": "Hello",
    "name": "World"
  }
}
```

An example of the `errors` object is the following:
 
```
{
  "errors": [
    {
      "code": "SIZE_BETWEEN",
      "message": "Size must be between 2 and 10",
      "field": "name",
      "params": {
        "rejectedValue": "f",
        "min": "2",
        "max": "10"
      }
    },
    {
      "code": "REQUIRED_FIELD",
      "message": "Field is required",
      "field": "description",
      "params": {}
    },
    {
      "code": "VALUE_GE_MIN",
      "message": "Must be greater than or equal to 100",
      "field": "price",
      "params": {
        "rejectedValue": "10",
        "min": "100"
      }
    }
  ]
}
```

## References

- [Kotlin DSL for Gradle](https://github.com/gradle/kotlin-dsl)
- [New Gradle Tasks API](https://blog.gradle.org/preview-avoiding-task-configuration-time)
- [Organizing Gradle Projects](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html)
- [Detekt Configuration](https://github.com/arturbosch/detekt/blob/master/detekt-cli/src/main/resources/default-detekt-config.yml)
- [JSON:API specification](https://jsonapi.org/)
- [Asciidoctor Syntax Quick Reference](https://asciidoctor.org/docs/asciidoc-syntax-quick-reference/)
- [Asciidoctor Gradle Plugin](https://asciidoctor.org/docs/asciidoctor-gradle-plugin/)
- [Swagger2Markup Documentation](http://swagger2markup.github.io/swagger2markup/1.3.1/)
