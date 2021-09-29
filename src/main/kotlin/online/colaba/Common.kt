package online.colaba

import org.gradle.api.Project
import java.io.File

const val dockerPrefix = "docker"


fun String.normalizeForWindows(): String = replace("\\", "/")

fun Project.localExists(directory: String): Boolean = File("$rootDir/$directory".normalizeForWindows()).exists()

fun Project.isJava() = localExists("src/main") || localExists("build/libs")
fun Project.isSpringBoot(): Boolean =  isJava()
        && !name.endsWith("lib") && !name.contains("front")
        && !setOf("postgres", "db", "static", "broker").contains(name)
fun Project.isService(): Boolean =  isSpringBoot()
        && !setOf("mail", "gateway", "eureka-server", "eureka", "discovery").contains(name)
    .apply { println("Project [$name] isService: $this") }
