import org.gradle.api.JavaVersion.VERSION_11
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
     id( "com.gradle.plugin-publish") version "0.10.1"
}

version = "0.1.2"
group = "online.colaba"
description = "EASY-DEPLOY gradle needed tasks"

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
        val ftpDeployPlugin by registering {
            id = "online.colaba.ftpDeploy"; implementationClass = "FtpDeploy"
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
            version = "0.1.2"
        }
        "dockerMainPlugin" {
            displayName = "Docker needed tasks for root multi-project"
            tags = listOf("docker", "kotlin", "build.gradle.kts")
            version = "0.1.2"
        }
        "ftpDeployPlugin" {
            displayName = "FTP deploy ssh-plugin gradle tasks"
            tags = listOf("ssh", "kotlin", "sftp", "ftp", "build.gradle.kts")
            version = "0.1.2"
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

