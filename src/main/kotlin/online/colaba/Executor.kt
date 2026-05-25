package online.colaba

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault

@DisableCachingByDefault(because = "Shells out to external commands (docker / npm / openapi-generator); output is not a deterministic function of declared inputs.")
open class Executor : DefaultTask() {
    init { group = "help"; description = "Execute a command line process on local machine" }

    @get:Input @Optional var command: String? = null

    @TaskAction
    open fun exec() {
        command?.run {
            val startTime = System.currentTimeMillis()

            printExecutionHeader(this)

            val commandList = cliPrefix() + this.splitBySpace()
            println("🔧 Command list: $commandList")
            println()

            try {
                val process = ProcessBuilder(commandList)
                    .directory(project.projectDir)
                    .inheritIO()
                    .start()

                val exitCode = process.waitFor()
                val duration = System.currentTimeMillis() - startTime

                if (exitCode != 0) {
                    printExecutionError(exitCode, duration)
                    throw RuntimeException("Command failed with exit code: $exitCode")
                }

                printExecutionSuccess(duration)

            } catch (e: Exception) {
                val duration = System.currentTimeMillis() - startTime
                if (e !is RuntimeException) {
                    printExecutionError(-1, duration)
                }
                throw e
            }
        }
    }

    private fun printExecutionHeader(command: String) {
        println()
        println("┌─────────────────────────────────────────────────────────────────┐")
        println("│              🪄 EXECUTING LOCAL COMMAND                          │")
        println("└─────────────────────────────────────────────────────────────────┘")
        println("📋 Command: $command")
        println("📍 Working dir: ${project.projectDir}")
    }

    private fun printExecutionSuccess(duration: Long) {
        println()
        println("┌─────────────────────────────────────────────────────────────────┐")
        println("│              ✅ COMMAND EXECUTED SUCCESSFULLY                    │")
        println("└─────────────────────────────────────────────────────────────────┘")
        println("⏱️  Duration: ${formatDuration(duration)}")
        println()
    }

    private fun printExecutionError(exitCode: Int, duration: Long) {
        println()
        println("┌─────────────────────────────────────────────────────────────────┐")
        println("│                  ❌ COMMAND EXECUTION FAILED                     │")
        println("└─────────────────────────────────────────────────────────────────┘")
        println("💥 Exit code: $exitCode")
        println("⏱️  Duration: ${formatDuration(duration)}")
        println()
    }

    private fun formatDuration(millis: Long): String {
        return when {
            millis < 1000 -> "${millis}ms"
            millis < 60000 -> "${String.format("%.1f", millis / 1000.0)}s"
            else -> "${millis / 60000}m ${(millis % 60000) / 1000}s"
        }
    }

    private fun cliPrefix(): List<String> {
        val isWindows = System.getProperty("os.name").contains("windows", ignoreCase = true)
        return if (isWindows) listOf("cmd", "/c") else listOf()
    }

    private fun String.splitBySpace(): List<String> =
        replace("  ", " ").split(" ").filter(String::isNotBlank)
}
