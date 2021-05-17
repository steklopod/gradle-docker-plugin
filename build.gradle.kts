plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.13.0"
}

val name = "docker"
val pluginsVersion = "1.2.68"
description = "Docker helper tasks"
version = pluginsVersion
group = "online.colaba"
repositories { mavenLocal(); mavenCentral() }
gradlePlugin { plugins { create(name) {
    id = "$group.$name"; implementationClass = "$group.DockerPlugin"; description = "Docker needed tasks"
} } }
pluginBundle {
    website = "https://github.com/steklopod/gradle-docker-plugin"
    vcsUrl = "https://github.com/steklopod/gradle-docker-plugin.git"
    (plugins) { name {
            displayName = "Docker needed tasks."
            tags = listOf("docker", "deploy", "docker-compose")
            version = pluginsVersion
} } }
tasks { compileKotlin { kotlinOptions { jvmTarget = "15" } } }

defaultTasks("clean", "build", "publishPlugins")
