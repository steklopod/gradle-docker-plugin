package online.colaba

object Main

val isWindows = System.getProperty("os.name").toLowerCase().contains("windows")
val windowsPrefix = if (isWindows) listOf("cmd", "/c") else listOf()
val userHomePath: String = System.getProperty("user.home")

fun String.normalizeForWindows():String = this.replace("\\", "/")
fun String.splitBySpace():List<String> = this.replace("  ", " ").split(" ")
