package online.colaba

import dockerPrefix
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register


open class DockerRemove : Executor() {
    init {
        group = dockerPrefix
        description = "Removes docker-container"
    }

    @get:Input
    var remove = project.name

    @TaskAction
    override fun exec() {
        super.command = "$dockerPrefix rm -f $remove"
        super.exec()
    }
}

fun Project.registerDockerRemoveTask() = tasks.register<DockerRemove>("online.colaba.getDockerRemove")

val Project.dockerRemove: TaskProvider<DockerRemove>
    get() = tasks.named<DockerRemove>("online.colaba.getDockerRemove")
