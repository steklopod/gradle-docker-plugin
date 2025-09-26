package online.colaba

import org.gradle.api.Project
import java.io.File

const val dockerPrefix = "docker"


fun String.normalizeForWindows(): String = replace("\\", "/")

fun Project.localExists(directory: String): Boolean = File("$rootDir/$directory".normalizeForWindows()).exists()

fun Project.isJava() = localExists("src/main") || localExists("build/libs")
fun Project.isSpringBoot(): Boolean =  isJava()
        && !name.endsWith("lib") && !name.contains("front")
        && !setOf("static", "frontend", "postgres", "nginx", "elastic", "static", "broker", "redis").contains(name)
fun Project.isWebService(): Boolean = isSpringBoot()
        && !setOf(/* "mail", */ "gateway").contains(name)
        && !name.endsWith("-server")
        && !name.endsWith("-lib")
    .apply { if (this) println("💬 Project [${name}] is WEB service. OpenApi generator will be added.") }
