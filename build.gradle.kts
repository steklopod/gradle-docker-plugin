plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.11.0"
}

val pluginsVersion = "1.1.1"
description = "Easy deploy by SSH with docker"
version = pluginsVersion
group = "online.colaba"

repositories { mavenLocal(); mavenCentral() }

val dockerPlugin = "dockerPlugin"

gradlePlugin {
    plugins {
        create(dockerPlugin) {
            id = "$group.docker"; implementationClass = "$group.DockerPlugin"
            description = "Docker needed tasks"
        }
    }

}

pluginBundle {
    website = "https://github.com/steklopod/gradle-docker-plugin"
    vcsUrl = "https://github.com/steklopod/gradle-docker-plugin.git"

    (plugins) {
        dockerPlugin {
            displayName = "Docker needed tasks."
            tags = listOf("docker", "kotlin", "deploy", "build.gradle.kts", "docker-compose")
            version = pluginsVersion
        }

    }
}

dependencies { implementation("org.hidetake:groovy-ssh:2.10.1") }

//configure<JavaPluginConvention> { sourceCompatibility = VERSION_11; targetCompatibility = VERSION_11 }

tasks {
    val java = "11"
    compileKotlin { kotlinOptions { jvmTarget = java }; sourceCompatibility = java; targetCompatibility = java }
}

defaultTasks("tasks", "publishPlugins")

