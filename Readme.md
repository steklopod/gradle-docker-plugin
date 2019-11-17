# Gradle docker-plugin [![Build Status](https://travis-ci.org/steklopod/gradle-docker-plugin.svg?branch=master)](https://travis-ci.org/steklopod/gradle-docker-plugin)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=bugs)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=security_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)

🛡️ Gradle `docker plugin` for projects with any types and languages. 
It gives helpful gradle's tasks for working with docker containers.

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

> Name of service for all tasks equals to ${project.name}. You can customize options of each task.

* `containers` - print current docker-services

* `stop` - stops docker-container 

* `remove` - removes docker-service

* `deploy` - compose up  docker-service from `docker-compose.yml`file

* `redeploy` - compose up after removing current docker-service

##### Apply only for subprojects

```kotlin
subprojects {
    apply<Docker>()
}
```

___
## [Example](https://github.com/steklopod/gradle-docker-plugin/tree/master/examples/hello) 🎫

> Structure
```shell script
hello
     |  - build.gradle.kts
     |  - docker-compose.yml
     |  - docker-compose.dev.yml (optional)
```

> `docker-compose.yml` file
```shell script
version: "3.7"
services:
  hello:
    image: hello-world
    container_name: hello
```

### Rerun/start 🎯

```shell script
gradle deploy
```

> equivalent to `docker-compose up --build --force-recreate -d` command. 

___
##### Optional tasks

> `docker-compose.dev.yml`, `Dockerfile` & `Dockerfile.dev` files are optionals.

* `deployDev` - compose up  docker-service from `docker-compose.dev.yml` file [optional]

* `redeployDev` -- compose up after removing current docker-service from `docker-compose.dev.yml` file [optional]

