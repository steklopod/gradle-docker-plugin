# Gradle docker-plugin [![Build Status](https://travis-ci.org/steklopod/gradle-docker-plugin.svg?branch=master)](https://travis-ci.org/steklopod/gradle-docker-plugin)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=bugs)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=security_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)

🛡️ Gradle `docker plugin` for projects with types and languages

### Quickstart

You only need to have `docker-compose.yml` file in root of project

> In your `buildgradle.kt` file:

```kotlin
plugins {
     id("online.colaba.docker") version "0.1.1"
}
```

### Available docker-tasks for `docker`-plugin:

> Name of service for all tasks equalse to ${project.name} 

`gradle stop` - stops docker-container 

`gradle containers` - print current docker-services

`gradle remove` - removes docker-service

`gradle deploy` - compose up  docker-service from `docker-compose.yml`file

`gradle deployDev` - compose up  docker-service from `docker-compose.dev.yml` file [optional]

`gradle redeploy` - compose up after removing current docker-service

`gradle redeployDev` -- compose up after removing current docker-service from `docker-compose.dev.yml` file [optional]


#### Apply only for subprojects

```kotlin
subprojects {
    apply<Docker>()
    registerExecutorTask()
}
```

> `docker-compose.yml` & `Dockerfile` files are optionals.

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
