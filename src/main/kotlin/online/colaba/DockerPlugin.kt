package online.colaba

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*


class DockerPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        description = "Docker needed tasks"
        val name = project.name
        registerDockerTask()
        registerExecutorTask()

        tasks {
            docker {}

            execute{}

            val deploy by registering(DockerCompose::class)
            val deployDev by registering(DockerCompose::class) { isDev = true }

            val remove by registering(Docker::class) { exec = "rm -f ${project.name}" }
            register("stop", Docker::class) { exec = "stop $name" }
            register("recompose") { dependsOn(remove); finalizedBy(deploy) }
            register("recomposeDev") { dependsOn(remove); finalizedBy(deployDev) }

            register("npm-install", Executor::class) { npm("install") }
            register("npm-build", Executor::class) { npmRun("build") }
            register("npm-generate", Executor::class) { npmRun("generate") }
            register("npm-start", Executor::class) { npmRun("start") }
        }
    }
}
