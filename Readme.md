## Docker plugin for gradle[![Build Status](https://travis-ci.org/steklopod/gradle-docker-plugin.svg?branch=master)](https://travis-ci.org/steklopod/gradle-docker-plugin)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=bugs)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=security_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)

🛡️ [`docker` - gradle plugin](https://plugins.gradle.org/plugin/online.colaba.docker) for projects with any types and languages. 
It gives helpful gradle's tasks for working with docker containers.

### Quick start

1. You only need to have `docker-compose.yml` file in root of project

2. In your `build.gradle.kts` file:

```kotlin
plugins {
     id("online.colaba.docker") version "0.2.2"
}
```

#### Run 🎯

```shell script
./gradlew deploy
```
> this task is equivalent to `docker-compose up --build --force-recreate --detach` command. 

### Available gradle's tasks for `docker` plugin:

Name of service for all tasks equals to ${project.name}. You can customize options of each task.

* `containers` - print current docker-services
* `deploy`     - compose up docker-service
* `stop`       - stops docker-container
* `remove`     - removes docker-service
* `recompose`, `recomposeDev`   - compose up after removing current docker-service

#### Customize

```kotlin
tasks{
    docker{
        exec = "rm -f ${project.name}"
    }
}
```

#### Run it

```shell script
./gradlew docker
```

___
Another version:
```kotlin
tasks{
    val remove by registering(Docker::class) { exec = "rm -f ${project.name}" }
    
    val deploy by existing(Docker::class){ 
                                            dependsOn(remove)
                                            recreate = false
                                            composeFile = "docker-compose.dev.yml"
                                         }
}
```

#### Run it

```shell script
./gradlew deploy
```
___
### [Example](https://github.com/steklopod/gradle-docker-plugin/tree/master/examples/hello) 🎫

* Structure
```shell script
hello
     |  - build.gradle.kts
     |  - docker-compose.yml
     |  - docker-compose.dev.yml (optional)
```

* `docker-compose.yml` file
```shell script
version: "3.7"
services:
  hello:
    image: hello-world
    container_name: hello
```

___
##### Optional

> `docker-compose.dev.yml`, `Dockerfile` & `Dockerfile.dev` files are optionals.

Optional tasks: 

* `deployDev` - compose up  docker-service from `docker-compose.dev.yml` file;
* `redeployDev` - compose up after removing current docker-service from `docker-compose.dev.yml` file.

