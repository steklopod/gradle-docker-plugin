package online.colaba

import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import java.io.File
import java.nio.file.Files


open class OpenApiSchemaTs : Executor() {
    init {
        group = "$dockerPrefix-${project.name}"
        description = "Generating [ ../frontend/types/schema-${project.name}.ts ] from OPEN API docs.yaml."
    }

    @get:Input var fromLocation : String = "/src/test/resources"
    @get:Input var fromFilename : String = "docs.yaml"

    @get:Input var toFolder   : String = "/frontend/types"
    @get:Input var toFilename : String = "schema-${project.name}"

    @TaskAction fun run() {
        val fromSchema = File("${project.rootDir}/${project.name}$fromLocation/$fromFilename".normalizeForWindows().replace("${project.name}/${project.name}", project.name))
        val toSchema = File("${project.rootDir}/$toFolder/$toFilename.ts".normalizeForWindows().replace("${project.name}/${project.name}", project.name))

        Files.createDirectories(toSchema.parentFile.toPath())

        when {
            fromSchema.exists() -> {
                println("\t 🔪 [${project.name.toUpperCase()}] 🔫 Found schema: $fromFilename")
                super.command = "npx openapi-typescript $fromSchema --output $toSchema"
                super.exec()

                File("${project.rootDir}/${project.name}/Users").deleteRecursively() // fix empty folder creation
            }
            fromSchema.parentFile.exists() && project.name != "gateway" -> {
                println("🔮 [OPEN API] Before run this task: 🧬 `npm i -g openapi-typescript`")
                println("\t 2) you should have [$fromFilename] openapi file in 🧿 ${project.name}$fromLocation 🧿 and [${project.rootDir}$toFolder] folder")
                System.err.println("\t 🧨 [${project.name}] 🧨 || Not found file: $fromFilename ($fromSchema)\n")
            }
            else -> println("🕳 ok: [${project.name}] is not backend. So, nothing to create for this service.")
    } }
}

fun Project.registerTSOpenapiGeneratorTask() = tasks.register<OpenApiSchemaTs>("schema")
val Project.schema: TaskProvider<OpenApiSchemaTs>
    get() = tasks.named<OpenApiSchemaTs>("schema")
