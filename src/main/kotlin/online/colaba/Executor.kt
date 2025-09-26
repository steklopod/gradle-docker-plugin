package online.colaba

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

open class Executor : DefaultTask() {
    init { group = "help"; description = "Execute a command line process on local machine" }

    @get:Input @Optional var command: String? = null

    @TaskAction
    open fun exec() {
        command?.run {
            println("\n 🪄✨ Executing local command: \n $this \n")

            val commandList = cliPrefix() + this.splitBySpace()
            println("🔧 Command list: $commandList")

            val process = ProcessBuilder(commandList)
                .directory(project.projectDir)
                .inheritIO()
                .start()

            val exitCode = process.waitFor()
            if (exitCode != 0) {
                throw RuntimeException("Command failed with exit code: $exitCode")
            }
        }
    }

    private fun cliPrefix(): List<String> {
        val isWindows = System.getProperty("os.name").contains("windows", ignoreCase = true)
        return if (isWindows) listOf("cmd", "/c") else listOf()
    }

    private fun String.splitBySpace(): List<String> =
        replace("  ", " ").split(" ").filter(String::isNotBlank)
}
