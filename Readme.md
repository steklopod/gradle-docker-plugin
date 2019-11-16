# Gradle docker-plugin

Gradle plugin for docker is for any type and language


### Quickstart

> In your `build.gradle.kt` file:

```kotlin
plugins {
     id("online.colaba.docker") version "0.1"
}
```

### Available docker-tasks for `docker`-plugin:

> Name of service for all tasks equalse to ${project.name} 

`./gradlew stop` - stops docker-container 

`./gradlew containers` - print current docker-services

`./gradlew remove` - removes docker-service

`./gradlew deploy` - compose up  docker-service from `docker-compose.yml`file

`./gradlew deployDev` - compose up  docker-service from `docker-compose.dev.yml` file [optional]

`./gradlew redeploy` - compose up after removing current docker-service

`./gradlew redeployDev` -- compose up after removing current docker-service from `docker-compose.dev.yml` file [optional]


#### Apply only for subprojects

```kotlin
subprojects {
    apply<Docker>()
    registerExecutorTask()
}
```

You only need to have `docker-compose.yml`

`docker-compose.yml` & `Dockerfile` are optionals.
