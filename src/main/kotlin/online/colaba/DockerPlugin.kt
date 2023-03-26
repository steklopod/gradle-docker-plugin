package online.colaba

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering

class DockerPlugin : Plugin<Project> { override fun apply(project: Project): Unit = project.run {

description = "Easy deploy with docker. And TypeScript generator with axios"


tasks {
    registerOpenApiAxiosApiTsTask(); registerOpenApiFetchApiTsTask();
    registerExecutorTask(); registerDockerTask(); registerDockerComposeTask();

    val logs by registering(Docker::class) { exec = "logs ${project.name}"; description = "Print logs of current docker container" }

    val deploy      by registering(ComposeDocker::class){ finalizedBy(logs); description = "Docker compose up (default with recreate & rebuild)" }
    val deployDev   by registering(ComposeDocker::class){ finalizedBy(logs); isDev = true; description = "Docker compose up from `docker-compose.DEV.yml` file" }
    val deployInfra by registering(ComposeDocker::class){ finalizedBy(logs); isInfra = true; description = "Docker compose up from `docker-compose.INFRA.yml` file" }

    val stop   by registering(ComposeDocker::class) { exec = "down ${project.name}"; recreate= false; description = "Stop docker container" }
    val remove by registering(Docker::class) {
        dependsOn(stop)
        exec = "rm -f ${project.name}"
        description = "Remove docker container (default container to remove = {project.name})"
    }

    register("recompose") { dependsOn(remove); finalizedBy(deploy); description = "Compose up after removing current docker services" }
    register("recomposeDev") { dependsOn(remove); finalizedBy(deployDev); description = "Compose up from `docker-compose.dev.yml` file after removing current dev docker services" }

} } }
