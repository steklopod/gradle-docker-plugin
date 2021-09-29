package online.colaba

import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

open class Executor : Exec() {
    init { group = "help"; description = "Execute a command line process on local machine" }

    @get:Input @Optional var command: String? = null

    @TaskAction override fun exec() {
        command?.run {
            println("\t 🪄 Executing local command: [ $this ] ... \n")
            val prefix = cliPrefix()
            commandLine = prefix + split(" ").map { it.trim() }
            super.exec()
        }
    }

    private fun cliPrefix(): List<String> {
        val isWindows = System.getProperty("os.name").contains("windows")
        return if (isWindows) listOf("cmd", "/c") else listOf()
    }
}

fun Project.registerExecutorTask() = tasks.register<Executor>("execute")
val Project.execute: TaskProvider<Executor>
    get() = tasks.named<Executor>("execute")
