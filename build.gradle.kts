plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "2.0.0"
    id("com.github.ben-manes.versions") version "0.53.0"
}

val pluginsVersion = "1.4.10"
version = pluginsVersion
group = "online.colaba"
description = "Docker helper tasks 🐳"

repositories { mavenCentral() }

gradlePlugin { plugins { create(name) {
    id = "$group.docker"; implementationClass = "$group.TasksRegistrator"; description = "Docker needed tasks";
    displayName = "Docker & docker-compose tasks 🐳. Bonus: `openapi-generator-cli generate`  java spring --> axios type script"

    tags.set(listOf("docker", "deploy", "docker-compose"))
    website.set("https://github.com/steklopod/gradle-docker-plugin.git")
    vcsUrl.set("https://github.com/steklopod/gradle-ssh-plugin.git")
} } }

defaultTasks("clean", "build", "publishPlugins")

kotlin { jvmToolchain(25) }
java { sourceCompatibility = JavaVersion.VERSION_25; targetCompatibility = JavaVersion.VERSION_25 }
