import org.gradle.api.JavaVersion.VERSION_11
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
     id( "com.gradle.plugin-publish") version "0.10.1"
}

version = "0.1"
group = "online.colaba"
description = "Docker needed tasks"

repositories { mavenLocal(); mavenCentral() }

gradlePlugin {
    plugins {
        val dockerPlugin by registering {
            id = "online.colaba.docker"; implementationClass = "Docker"
            description = "Docker needed tasks"
        }

        val dockerMainPlugin by registering {
            id = "online.colaba.dockerMain"; implementationClass = "DockerMain"
            description = "Docker needed tasks for root multi-project"
        }
    }

}

pluginBundle {
    website = "https://colaba.online/"
    vcsUrl = "https://github.com/steklopod/gradle-docker-plugin"

    (plugins) {
        "dockerPlugin" {
            displayName = "Docker needed tasks"
            tags = listOf("docker", "kotlin")
            version = "0.1"
        }
        "dockerMainPlugin" {
            displayName = "Docker needed tasks for root multi-project"
            tags = listOf("docker", "kotlin")
            version = "0.1"
        }

    }
}

dependencies{
    implementation("org.hidetake:groovy-ssh:2.10.1")
}

configure<JavaPluginConvention> { sourceCompatibility = VERSION_11; targetCompatibility = VERSION_11 }

tasks{
    withType<Wrapper> { gradleVersion = "6.0" }
    withType<KotlinCompile> { kotlinOptions { jvmTarget = "11" } }
}

defaultTasks("tasks", "publishPlugins")

