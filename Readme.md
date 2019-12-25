### 🛡️ [`docker` - gradle plugin](https://plugins.gradle.org/plugin/online.colaba.docker) for projects with any types and languages [![Build Status](https://travis-ci.com/steklopod/gradle-docker-plugin.svg?branch=master)](https://travis-ci.com/steklopod/gradle-docker-plugin) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=bugs)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=security_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)

This plugin gives helpful gradle tasks for working with docker containers.

#### Quick start

1. You only need to have `docker-compose.yml` file in root of your project

2. In your `build.gradle.kts` file:

```kotlin
plugins {
     id("online.colaba.docker") version "1.0.3"
}
```

#### Run task 🎯

```shell script
./gradlew deploy
```
> this task is equivalent to `docker-compose up --build --force-recreate --detach` command. 

### Available gradle's tasks for `docker` plugin:

* `deploy` - compose up project from `docker-compose.yml` file (default with recreate & rebuild)
* `deployDev`  - compose up docker container from `docker-compose.dev.yml` file
* `recompose`, `recomposeDev`  - compose up after removing current docker-service
* `stop`, `remove`      - stop/remove docker container
* `logs`, `docker`  - print current docker-services

>Name of service for all tasks equals to **${project.name}**. You can customize options of each task.

#### Customize it in your `build.gradle.kts` file

```kotlin
tasks{
    docker{
        exec = "rm -f ${project.name}"
    }
}
```

#### Run customized task

```shell script
gradle docker
```

#### Another version of customization:
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

#### Run customized task

```shell script
gradle deploy
```
___
#### [Example](https://github.com/steklopod/gradle-docker-plugin/tree/master/examples/hello) 🎫

* Structure:
```shell script
[project]
     | - build.gradle.kts
     | - docker-compose.yml
     | - docker-compose.dev.yml (optional)
```
> `docker-compose.dev.yml`, `Dockerfile` & `Dockerfile.dev` files are optionals

* `docker-compose.yml` file:
```yaml
version: "3.7"
services:
  hello:
    image: hello-world
    container_name: hello
```

___

##### Optional


With `docker plugin` you have additional bonus task for executing a command line process on local PC [linux/windows]:
```kotlin
tasks{
       execute{
                command = "echo ${project.name}"
       }
}
```
