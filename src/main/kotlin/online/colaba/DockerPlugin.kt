package online.colaba

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

class DockerPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        description = "Docker needed tasks"

        registerDockerTask()
        registerExecutorTask()

        tasks {
            docker {   }

            execute{   }

            val deploy by registering(DockerCompose::class){ description = "Docker compose up (default with recreate & rebuild)" }
            val deployDev by registering(DockerCompose::class) { isDev = true; description = "Docker compose up from `docker-compose.dev.yml` file" }

            val remove by registering(Docker::class) { exec = "rm -f ${project.name}"; description = "Remove docker container (default container to remove = {project.name})" }
            register("logs", Docker::class) { exec = "logs ${project.name}"; description = "Logs of current docker container" }
            register("stop", Docker::class) { exec = "stop ${project.name}"; description = "Stop docker container" }
            register("recompose") { dependsOn(remove); finalizedBy(deploy); description = "Compose up after removing current docker service" }
            register("recomposeDev") { dependsOn(remove); finalizedBy(deployDev); description = "Compose up from `docker-compose.dev.yml` file after removing current docker service" }

            register("npm-install", Executor::class) { npm("install"); description = "npm install" }
            register("npm-build", Executor::class) { npmRun("build"); description = "npm run build" }
            register("npm-generate", Executor::class) { npmRun("generate"); description = "npm run generate" }
            register("npm-start", Executor::class) { npmRun("start"); description = "npm run start" }
        }
    }
}
