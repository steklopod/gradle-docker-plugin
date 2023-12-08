plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.2.1"
    id("com.github.ben-manes.versions") version "0.50.0"
}

val pluginsVersion = "1.3.10"
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

kotlin { jvmToolchain(17) }
java { toolchain { languageVersion.set(JavaLanguageVersion.of(17)) } }
