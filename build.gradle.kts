import org.gradle.api.JavaVersion.VERSION_11
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
     id( "com.gradle.plugin-publish") version "0.10.1"
}

val ver = "0.1.3"
version = ver
group = "online.colaba"
description = "EASY-DEPLOY gradle needed tasks"

repositories { mavenLocal(); mavenCentral() }

gradlePlugin {
    plugins {
        val dockerPlugin by registering {
            id = "online.colaba.docker"; implementationClass = "online.colaba.Docker"
            description = "Docker needed tasks"
        }
        val dockerMainPlugin by registering {
            id = "online.colaba.dockerMain"; implementationClass = "online.colaba.DockerMain"
            description = "Docker needed tasks for root multi-project"
        }
        val sshPlugin by registering {
            id = "online.colaba.ssh"; implementationClass = "online.colaba.Ssh"
            description = "Ssh needed tasks for FTP deploy"
        }
    }

}

pluginBundle {
    website = "https://github.com/steklopod/gradle-docker-plugin"
    vcsUrl = "https://github.com/steklopod/gradle-docker-plugin"

    (plugins) {
        "dockerPlugin" {
            displayName = "Docker needed tasks"
            tags = listOf("docker", "kotlin", "build.gradle.kts")
            version = ver
        }
        "dockerMainPlugin" {
            displayName = "Docker needed tasks for root multi-project"
            tags = listOf("docker", "kotlin", "build.gradle.kts")
            version = ver
        }
        "sshPlugin" {
            displayName = "FTP deploy ssh-plugin gradle tasks"
            tags = listOf("ssh", "kotlin", "sftp", "ftp")
            version = ver
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

