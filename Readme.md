# Gradle docker-plugin [![Build Status](https://travis-ci.org/steklopod/gradle-docker-plugin.svg?branch=master)](https://travis-ci.org/steklopod/gradle-docker-plugin)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=bugs)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=security_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)

🛡️ Gradle `docker plugin` for projects with types and languages

### Quick start

You only need to have `docker-compose.yml` file in root of project

> In your `build.gradle.kt` file:

```kotlin
plugins {
     id("online.colaba.docker") version "0.1.1"
}
```

#### Run

```shell script
./gradlew containers
```

### Available gradle tasks for `docker-plugin`:

> Name of service for all tasks equals to ${project.name} 

* `stop` - stops docker-container 

* `containers` - print current docker-services

* `remove` - removes docker-service

* `deploy` - compose up  docker-service from `docker-compose.yml`file

* `redeploy` - compose up after removing current docker-service

##### Optional tasks

> `docker-compose.dev.yml`, `Dockerfile` & `Dockerfile.dev` files are optionals.

* `deployDev` - compose up  docker-service from `docker-compose.dev.yml` file [optional]

* `redeployDev` -- compose up after removing current docker-service from `docker-compose.dev.yml` file [optional]


##### Apply only for subprojects

```kotlin
subprojects {
    apply<Docker>()
}
```


[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
