package online.colaba

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*


class DockerPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        description = "Docker needed tasks"

        val name = project.name

        registerExecutorTask()
        registerDockerTask()

        tasks {
            docker {}
            register("stop", Docker::class) { command = "$dockerPrefix stop $name" }

            val remove by registering(Docker::class) { exec = "rm -f ${project.name}"; containers() }

            val deploy by registering(DockerCompose::class) { containers() }
            val deployDev by registering(DockerCompose::class) { isDev = true; containers() }

            register("redeploy", DockerCompose::class) { dependsOn(remove); finalizedBy(deploy); containers() }
            register("redeployDev", DockerCompose::class) { dependsOn(remove); finalizedBy(deployDev); containers() }

            register("npm-install", DockerCompose::class) { npm("install") }
            register("npm-build", DockerCompose::class) { npmRun("build") }
            register("npm-generate", DockerCompose::class) { npmRun("generate") }
            register("npm-start", DockerCompose::class) { npmRun("start") }
        }
    }
}
