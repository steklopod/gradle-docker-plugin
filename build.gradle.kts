plugins {
    `kotlin-dsl`
    id("org.sonarqube") version "3.3"
    id("com.gradle.plugin-publish") version "0.18.0"
    id("com.github.ben-manes.versions") version "0.39.0"
}

val name = "docker"
val pluginsVersion = "1.2.90"
description = "Docker helper tasks"
group = "online.colaba"
version = pluginsVersion
repositories { mavenLocal(); mavenCentral() }
gradlePlugin { plugins { create(name) {
    id = "$group.$name"; implementationClass = "$group.DockerPlugin"; description = "Docker needed tasks"
} } }
pluginBundle {
    website = "https://github.com/steklopod/gradle-docker-plugin"
    vcsUrl = "https://github.com/steklopod/gradle-docker-plugin.git"
    (plugins) { name {
            displayName = "Docker & docker-compose tasks. Bonus: `openapi-generator-cli generate`  java spring --> axios type script"
            tags = listOf("docker", "deploy", "docker-compose")
            version = pluginsVersion
} } }
tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions { jvmTarget = "17" } }
}

defaultTasks("clean", "build", "publishPlugins")
