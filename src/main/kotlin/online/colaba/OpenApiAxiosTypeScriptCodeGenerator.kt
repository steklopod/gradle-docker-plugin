package online.colaba

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import java.io.File


open class OpenApiAxiosTypeScriptCodeGenerator : Executor() {
    init {
        group = "$dockerPrefix-${project.name}"
        description = "Generating [ TypeScript Axios API] OPEN API."
    }

    @get:Input var fromLocation : String = "/src/test/resources"
    @get:Input var fromFilename : String = "openapi.json"

    @get:Input var toFolder   : String = "/frontend/api/${project.name}"
    @get:Input var toFilename : String = "schema-${project.name}"

    @get:Input var generatorName : String  = "typescript-axios"

    @get:Input var addInfo : Set<String> = setOf(
        "swaggerAnnotations=true",
        "supportsES6=true",
        "serviceImplementation=true",
        "nullSafeAdditionalProps=true",
        "enumPropertyNaming=UPPERCASE",
        "typescriptThreePlus=true",
        "legacyDiscriminatorBehavior=false",
    )
    @get:Input var deleteNotTSFiles : Boolean = true

    @get:Input var separateModels : Boolean = true
    @get:Input var apiPackage     : String  = "controllers"
    @get:Input var modelPackage   : String  = "models"

    @get:Input var enablePostProcessFile : Boolean = false


    @TaskAction fun run() {
        var arguments = addInfo.joinToString(",")
        if (separateModels) arguments += ",withSeparateModelsAndApi=true,modelPackage=$modelPackage,apiPackage=$apiPackage"
        if (enablePostProcessFile) arguments += ",enablePostProcessFile=true"

        val from = File("${project.projectDir}$fromLocation/$fromFilename")
        val to = File("${project.rootDir}/$toFolder")

        if (from.exists()) {
            println("👉🏻 [${project.name.toUpperCase()}] 🔫 Found schema: $fromFilename\n")
            println("📌 FROM: $fromLocation/$fromFilename")
            println("📌 TO: $toFolder")

            val generator = "--generator-name $generatorName"
            val additional = "--additional-properties=$arguments"

            val cliPath: String = ProcessBuilder("/bin/bash", "-l", "-c", "which openapi-generator-cli")
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .start()
                .inputStream.bufferedReader()
                .readText().trim()

            if (cliPath.isEmpty()) {
                throw GradleException("`openapi-generator-cli` not found in system. Please install it first: https://github.com/OpenAPITools/openapi-generator-cli?tab=readme-ov-file#installation")
            }

            super.command = "$cliPath generate -i $from -o $to $generator $additional"
            super.exec()

            println("🪲 Start deleting unnecessary files...")

            val fromSubprojectRoot = "${project.projectDir}"

            File("$fromSubprojectRoot/Users").deleteRecursively()
            println("\n🕷 Removed: ${project.name}/Users")

            File("$to/.openapi-generator").deleteRecursively()
            println("🕷 Removed: $to/.openapi-generator")

            "openapitools.json".run {
                if(File("$fromSubprojectRoot/$this").delete()) println("🕷 Removed: ${project.name}/$this")
                if(File("${project.rootDir}/$this").delete())  println("🕷 Removed: ${project.name}/$this \n")
            }

            if (deleteNotTSFiles) to.walk().forEach {
                if (it.isFile && !it.name.endsWith(".ts")) {
                    if(it.delete()) println("🕷 Removed: ${it.toString().substringAfter(project.rootDir.toString())}")
                }
            }
        } else if (from.parentFile.exists() && project.name != "gateway") {
            System.err.println("\t 🧨 [${project.name}] 🧨 | NOT FOUND FILE: 👻 $fromFilename ($from)\n")
            println("🔮 [OPEN API] Before run this task: 🧬 install local `openapi-generator-cli`")
            println("\t\t you should have [$fromFilename] openapi file in 🧿 ${project.name}$fromLocation 🧿 and [${project.rootDir}${toFolder}] folder")
            System.err.println("\t\t⭕️ Maybe `generateOpenApiDocs` was not attached. Please try to run: ➡️ `gradle generateOpenApiDocs` ⬅️ first")
        }
    }
}

fun Project.registerOpenApiAxiosApiTsTask() = tasks.register<OpenApiAxiosTypeScriptCodeGenerator>("apiGen") {
    description = "Generate TypeScript frontend with Axios generator"
    outputs.upToDateWhen { false }

    tasks.findByName("generateOpenApiDocs")?.let{
        mustRunAfter(it)
        dependsOn(it)
//        inputs.file(File("${project.projectDir}$fromLocation/$fromFilename"))
//        outputs.dir(File("${project.rootDir}/$toFolder"))
    }
}

val Project.apiGen: TaskProvider<OpenApiAxiosTypeScriptCodeGenerator>
    get() = tasks.named<OpenApiAxiosTypeScriptCodeGenerator>("apiGen")
