plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.13.0"
}

val pluginsVersion = "1.1.2"
version = pluginsVersion
description = "Easy deploy by SSH with docker"
group = "online.colaba"

repositories { mavenLocal(); mavenCentral() }

val dockerPlugin = "dockerPlugin"

gradlePlugin { plugins { create(dockerPlugin) {
    id = "$group.docker"; implementationClass = "$group.DockerPlugin"; description = "Docker needed tasks"
} } }

pluginBundle {
    website = "https://github.com/steklopod/gradle-docker-plugin"
    vcsUrl = "https://github.com/steklopod/gradle-docker-plugin.git"
    (plugins) { dockerPlugin {
            displayName = "Docker needed tasks."
            tags = listOf("docker", "kotlin", "deploy", "docker-compose")
            version = pluginsVersion
    } }
}

tasks { compileKotlin { kotlinOptions { jvmTarget = "15" } } }

defaultTasks("clean", "build", "publishPlugins")
