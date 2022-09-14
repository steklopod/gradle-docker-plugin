plugins {
    id("org.gradle.kotlin.kotlin-dsl") version "3.1.0"
    id("com.gradle.plugin-publish") version "1.0.0"
    id("com.github.ben-manes.versions") version "0.42.0"
}

val pluginsVersion = "1.2.94"
version = pluginsVersion
group = "online.colaba"
description = "Docker helper tasks 🐳"

repositories { mavenCentral() }

gradlePlugin { plugins { create(name) {
    id = "$group.docker"; implementationClass = "$group.DockerPlugin"; description = "Docker needed tasks";
    displayName = "Docker & docker-compose tasks 🐳. Bonus: `openapi-generator-cli generate`  java spring --> axios type script"
} } }
pluginBundle {
    website = "https://github.com/steklopod/gradle-docker-plugin"
    vcsUrl = "https://github.com/steklopod/gradle-docker-plugin.git"
    tags = listOf("docker", "deploy", "docker-compose")
}

defaultTasks("clean", "build", "publishPlugins")
