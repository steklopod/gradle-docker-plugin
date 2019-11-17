# Gradle `ftpDeploy` plugin  [![Build Status](https://travis-ci.org/steklopod/gradle-docker-plugin.svg?branch=master)](https://travis-ci.org/steklopod/gradle-docker-plugin)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=bugs)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=security_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)

🛡️ Gradle `ftpDeploy` plugin for root multi-project

> Default `backend`'s **jar** distribution path: `${project.rootDir}/backend/build/libs/*.jar`


### Structure must be
```shell script
 project
    |-backend/
      | - build/libs/*.jar
    |-frontend/
      | - dist/
```

### Quick start (`1 step only`)
1. In your `build.gradle.kts` file:
```kotlin
plugins {
     id("online.colaba.ftpDeploy") version "0.1.2"
}
```

### Execute by FTP 🎯
1. Variant 1
```shell script
./gradlew publish
```
2. Variant 2
```shell script
gradle ssh
```

### Available gradle tasks for `ftpDeploy` plugin`:

> Name of service for all tasks equals to ${project.name} 

* `publish` - send by ftp
* `ssh` - send by ftp


### Customization
1. Variant 1
```kotlin
        publish{
            host = "0.0.0.0"; user = "user"
            commands = "echo PUBLISH_COMMAND"
        }
```
2. Variant 2
```kotlin

        tasks {
            val ssh by registering(Publisher::class) {
            host = "0.0.0.0"; user = "user"
            commands = "echo SSH_COMMAND"
            }
        }
```

> Default `backend`'s **jar** distribution path: `${project.rootDir}/backend/build/libs/*.jar`
