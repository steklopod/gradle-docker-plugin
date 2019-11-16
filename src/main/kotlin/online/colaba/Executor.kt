import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register


const val npmPrefix = "npm"

open class Executor : Exec() {
    init {
        group = "execute"
        description = "Executes a command line process (example: `echo HELLO`, no need ['cmd', '/c'] prefix for windows)"
    }

    @get:Input
    var command = "echo ${project.name}"

    @TaskAction
    override fun exec() {
        commandLine = windowsPrefix + command.splitBySpace()
        super.exec()
    }

    fun npm(npmCommand: String) {  this.command ="$npmPrefix $npmCommand"; group = npmPrefix }
    fun npmRun(npmRunCommand: String) { npm("run $npmRunCommand") }
}

fun Project.registerExecutorTask() = tasks.register<Executor>("execute")

val Project.execute: TaskProvider<Executor>
    get() = tasks.named<Executor>("execute")
