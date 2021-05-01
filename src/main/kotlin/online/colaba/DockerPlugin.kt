package online.colaba

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

class DockerPlugin : Plugin<Project> { override fun apply(project: Project): Unit = project.run {

description = "Docker needed tasks"

registerDockerTask(); registerExecutorTask(); registerDockerComposeTask(); registerTSOpenapiGeneratorTask()
tasks {
    execute         {  }
    schema          {  }

    docker          {  }
    dockerComposeUp {  }

    val logs by registering(Docker::class) { exec = "logs ${project.name}"; description = "Print logs of current docker container" }

    val deploy    by registering(ComposeDocker::class){ finalizedBy(logs); description = "Docker compose up (default with recreate & rebuild)" }
    val deployDev by registering(ComposeDocker::class) { finalizedBy(logs); isDev = true; description = "Docker compose up from `docker-compose.dev.yml` file" }

    val stop   by registering(Docker::class) { exec = "stop ${project.name}"; description = "Stop docker container" }
    val remove by registering(Docker::class) {
        dependsOn(stop)
        exec = "rm -f ${project.name}"
        description = "Remove docker container (default container to remove = {project.name})"
    }

    register("recompose") { dependsOn(remove); finalizedBy(deploy); description = "Compose up after removing current docker service" }
    register("recomposeDev") { dependsOn(remove); finalizedBy(deployDev); description = "Compose up from `docker-compose.dev.yml` file after removing current docker service" }

} } }
