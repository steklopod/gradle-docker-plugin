package online.colaba

import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import java.io.File
import java.nio.file.Files


open class OpenApiAxiosApiTs : Executor() {
    init {
        group = "$dockerPrefix-${project.name}"
        description = "Generating [ TypeScript Axios API] from OPEN API docs.yaml."
    }

    @get:Input var fromLocation : String = "/src/test/resources"
    @get:Input var fromFilename : String = "docs.yaml"

    @get:Input var toFolder   : String = "/frontend/types/api"
    @get:Input var toFilename : String = "schema-${project.name}"

    @get:Input var addInfo    : String = "--generator-name typescript-axios --additional-properties=library=spring-boot"

    @TaskAction fun run() {
        val from = "$fromLocation/$fromFilename"
        val fromSchema = fromOpenApiScheme(from)
        val toFolder = File("${project.rootDir}/$toFolder".normal())

        when {
            fromSchema.exists() -> {
                println("\t 🔪 [${project.name.toUpperCase()}] 🔫 Found schema: $fromFilename")

                super.command = "openapi-generator-cli generate -i $fromSchema -o $toFolder $addInfo"
                super.exec()

                File("${project.rootDir}/${project.name}/Users").deleteRecursively() // fix empty folder creation

                File("$toFolder/.openapi-generator").deleteRecursively()

                toFolder.walk().forEach { if (!it.name.endsWith(".ts")) it.delete() }
            }
            fromSchema.parentFile.exists() && project.name != "gateway" -> {
                println("🔮 [OPEN API] Before run this task: 🧬 install local `openapi-generator-cli`")
                println("\t you should have [$fromFilename] openapi file in 🧿 ${project.name}$fromLocation 🧿 and [${project.rootDir}${this.toFolder}] folder")
                System.err.println("\t 🧨 [${project.name}] 🧨 || Not found file: $fromFilename ($fromSchema)\n")
            }
    } }


}

fun Project.registerOpenApiAxiosApiTsTask() = tasks.register<OpenApiAxiosApiTs>("api")
val Project.api: TaskProvider<OpenApiAxiosApiTs>
    get() = tasks.named<OpenApiAxiosApiTs>("api")
