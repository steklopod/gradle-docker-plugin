package online.colaba

import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

open class ComposeDocker : Executor() {
    init {
        group = "$dockerPrefix-${project.name}"
        description = "Docker-compose task"
    }

    @get:Input
    @Optional
    var composeFile: String? = null

    @get:Input
    @Optional
    var service: String? = null

    @get:Input
    var exec: String = "up "

    @get:Input
    var recreate: Boolean = true

    @get:Input
    var isDev: Boolean = false

    @get:Input
    var isInfra: Boolean = false

    @TaskAction
    override fun exec() {
        val recreateFlags = "--detach --build --force-recreate"
        val dockerComposedevFile = "docker-compose.dev.yml"

        if (isDev) composeFile = dockerComposedevFile
        if (isInfra) composeFile = dockerComposedevFile.replace("dev", "infra")

        composeFile?.let { exec = "-f $composeFile up " }

        if (recreate) exec += recreateFlags

        service?.let { exec += " $it" }
        val runCommand = "$dockerPrefix-compose $exec".trim()
        super.command = runCommand
        super.exec()
    }
}


fun Project.registerDockerComposeTask() = tasks.register<ComposeDocker>("dockerComposeUp")

val Project.dockerComposeUp: TaskProvider<ComposeDocker>
    get() = tasks.named<ComposeDocker>("dockerComposeUp")
