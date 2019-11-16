import org.gradle.api.tasks.Exec

object Main

const val frontendService = "frontend"
const val backendService = "backend"
const val nginxService = "nginx"

const val buildGroup = "build"
const val removeGroup = "remove"
const val stopGroup = "stop"

val isWindows = System.getProperty("os.name").toLowerCase().contains("windows")
val windowsPrefix = if (isWindows) listOf("cmd", "/c") else listOf()
val userHomePath: String = System.getProperty("user.home")

fun Exec.execute(command: String) { commandLine(windowsPrefix + command.splitBySpace()) }

fun String.normalizeForWindows():String = this.replace("\\", "/")

fun String.splitBySpace():List<String> = this.replace("  ", " ").split(" ")
