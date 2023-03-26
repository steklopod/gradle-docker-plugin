plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.1.0"
    id("com.github.ben-manes.versions") version "0.46.0"
}

val pluginsVersion = "1.3.4-RC2"
version = pluginsVersion
group = "online.colaba"
description = "Docker helper tasks 🐳"

repositories { mavenCentral() }

gradlePlugin { plugins { create(name) {
    id = "$group.docker"; implementationClass = "$group.DockerPlugin"; description = "Docker needed tasks";
    displayName = "Docker & docker-compose tasks 🐳. Bonus: `openapi-generator-cli generate`  java spring --> axios type script"

    tags.set(listOf("docker", "deploy", "docker-compose"))
    website.set("https://github.com/steklopod/gradle-docker-plugin.git")
    vcsUrl.set("https://github.com/steklopod/gradle-ssh-plugin.git")
} } }

defaultTasks("clean", "build", "publishPlugins")

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach { kotlinOptions { jvmTarget = "17" } }
java { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17  }
