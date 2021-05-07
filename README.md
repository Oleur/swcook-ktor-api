[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/Oleur/swcook-ktor-api)

[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/Oleur/swcook-ktor-api)

[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/Oleur/swcook-ktor-api)

# swcook-ktor-api
Sample cooking API made with Kotlin Ktor to showcase the library capabilities.
This sample project offers a simple REST API where you can get or create recipes with a list of ingredients and cooking steps.
This sample is using MVC and Clean Architecture concepts in order to have a clean seperation of the layers. And thanks to Swagger, we can share code between the backend application and mobile apps.

If you want to know more about Ktor, check the official documentation: https://ktor.io/docs/welcome.html

## Libraries and tech specs
To demonstrate the great capabilities of Ktor and its reliability, many Ktor features and 3rd party libraries have been used.

Ktor features:
- **Location**: Handles routes in a typed way.
- **StatusPage**: Properly handle errors and exception in your application.
- **ContentNegotiation**: Content negotiation and Serializing/deserializing the content in JSON.
- **DataConversion**: Handle custom types.
- **Logging**: Logging calls with the SLF4J library (Logback).

External libraries:
- **[Valiktor](https://github.com/valiktor/valiktor)**: Requests data validation.
- **[Moshi](https://github.com/square/moshi)**: Requests data validation.
- **[Exposed](https://github.com/JetBrains/Exposed)**: ORM framework in Kotlin to deal with the Postgres database.
- **[HikariCP](https://github.com/brettwooldridge/HikariCP)**: JDBC connection pool
- **[Swagger-gradle-codegen](https://github.com/Yelp/swagger-gradle-codegen)**: Generate networking code from a Swagger spec file.
- **[Koin](https://github.com/InsertKoinIO/koin)**: Dependency injection framework for Kotlin.
- **[Flyway](https://github.com/flyway/flyway)**: Handle database migrations.


## Run the web service
- Make sure that docker and docker-compose are installed on your machine
- Go to /docker/dev/ and and run the following command to start the database: `docker-compose up`
- Open the project with Intellij (works with the version 2020.3.1), configure the application and run.
- Open a web browser for example, and go to http://0.0.0.0:8080/ and you should see "Server is running" in the page :)

## FOSDEM resources
I built this web service for a talk I gave at FOSDEM 2021. Here are the slides and the video of the conference:
- Slides (_coming soon_)
- Video (_coming soon_)


https://www.gitpod.io/docs/languages/kotlin
https://www.gitpod.io/docs/config-docker

1. 
Reference a Dockerfile next to your .gitpod.yml file:
image:
  file: .gitpod.Dockerfile

2. no arquivo gitpod.Dockerfile
USER gitpod
RUN brew install kotlin