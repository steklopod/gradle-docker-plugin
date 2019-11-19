import org.gradle.api.JavaVersion.VERSION_11
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.10.1"
}

val pluginsVersion = "0.1.4"
description = "EASY-DEPLOY gradle needed tasks"
version = pluginsVersion
group = "online.colaba"

repositories { mavenLocal(); mavenCentral() }

gradlePlugin {
    plugins {
        val dockerPlugin by registering {
            id = "online.colaba.docker"; implementationClass = "online.colaba.Docker"
            description = "Docker needed tasks"
        }
    }

}

pluginBundle {
    website = "https://github.com/steklopod/gradle-docker-plugin"
    vcsUrl = "https://github.com/steklopod/gradle-docker-plugin"

    (plugins) {
        "dockerPlugin" {
            displayName =
                "\uD83D\uDEE1️ Docker needed tasks. \nYou only need to have docker-compose.yml file in root of project\n" +
                        "Available gradle's tasks for docker plugin:\n" +
                        "- containers - print current docker-services;\n" +
                        "- deploy - compose up docker-service;\n" +
                        "- stop - stops docker-container;\n" +
                        "- remove - removes docker-service;\n" +
                        "- redeploy - compose up after removing current docker-service."
            tags = listOf("docker", "kotlin", "deploy", "build.gradle.kts", "docker-compose", "\uD83E\uDD1F\uD83C\uDFFB")
            version = pluginsVersion
        }

    }
}

dependencies {
    implementation("org.hidetake:groovy-ssh:2.10.1")
}

configure<JavaPluginConvention> { sourceCompatibility = VERSION_11; targetCompatibility = VERSION_11 }

tasks {
    withType<Wrapper> { gradleVersion = "6.0" }
    withType<KotlinCompile> { kotlinOptions { jvmTarget = "11" } }
}

defaultTasks("tasks", "publishPlugins")

