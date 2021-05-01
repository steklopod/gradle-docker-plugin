package online.colaba

import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import java.io.File


open class SchemaGenerator : Executor() {
    init {
        group = "$dockerPrefix-${project.name}"
        description = "Generating [ ../frontend/types/schema.ts ] from OPEN API docs.yaml."
    }

    @get:Input var fromLocation : String = "/src/test/resources"
    @get:Input var fromFilename : String = "docs.yaml"

    @get:Input var toFolder   : String = "/frontend/types"
    @get:Input var toFilename : String = "schema"

    @TaskAction
    fun run() {
        println("🔮 [OPEN API] Before run this task, you should do:")
        println("\t 1) you should run `npm i -g openapi-typescript`")
        println("\t 2) you should have [$fromFilename] openapi file in 🧿 ${project.name}/$fromLocation 🧿 and [${project.rootDir}/$toFolder] folder")
        println("\t 3) you should have [${project.rootDir}/$toFolder] folder")

        val docLocation = "${project.rootDir}/${project.name}$fromLocation/$fromFilename"

        if (File(docLocation).exists()) {
            println("🔬 Found docs location: $docLocation")
            super.command = "npx openapi-typescript $docLocation --output ${project.rootDir}/$toFolder/$toFilename-${project.name}.ts".normalizeForWindows()
            super.exec()
        } else System.err.println("\t 🧨 ${project.name} 🧨 || Not found > $fromFilename < in [$docLocation]\n")
    }
}
fun Project.registerTSOpenapiGeneratorTask() = tasks.register<SchemaGenerator>("apiTs")

val Project.apiTs: TaskProvider<SchemaGenerator>
    get() = tasks.named<SchemaGenerator>("apiTs")
