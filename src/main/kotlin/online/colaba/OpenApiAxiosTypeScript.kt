package online.colaba

import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import java.io.File


open class OpenApiAxiosTypeScript : Executor() {
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
        "library=spring-boot",
        "swaggerAnnotations=true",
        "supportsES6=true",
        "withInterfaces=true",
        "serviceImplementation=true",
        "nullSafeAdditionalProps=true",
        "enumPropertyNaming=UPPERCASE",
        "typescriptThreePlus=true"
    )
    @get:Input var deleteNotTSFiles : Boolean = true

    @get:Input var separateModels : Boolean = true
    @get:Input var apiPackage     : String  = "controllers"
    @get:Input var modelPackage   : String  = "models"

    @get:Input var enablePostProcessFile : Boolean = false


    @TaskAction fun run() {
    var arguments = addInfo.joinToString(",")
    if(separateModels) arguments += ",withSeparateModelsAndApi=true,modelPackage=$modelPackage,apiPackage=$apiPackage"
    if(enablePostProcessFile) arguments += ",enablePostProcessFile=true"

    val from = File("${project.projectDir}$fromLocation/$fromFilename")
    val to = File("${project.rootDir}/$toFolder")

    if (from.exists()) {
//        from.delete()
        println("\t 👉🏻 [${project.name.toUpperCase()}] 🔫 Found schema: $fromFilename")
        println("📌 FROM: $fromLocation/$fromFilename")
        println("📌 TO: $toFolder")

        val generator = "--generator-name $generatorName"
        val additional = "--additional-properties=$arguments"

        command = "openapi-generator-cli generate -i $from -o $to $generator $additional"
        exec()

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
                it.delete()
                println("🕷 Removed: ${it.toString().substringAfter(project.rootDir.toString())}")
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

fun Project.registerOpenApiAxiosApiTsTask() = tasks.register<OpenApiAxiosTypeScript>("apiGen") {
    description = "Generate TypeScript frontend with Axios generator"

    val generateOpenApiDocs = tasks.findByName("generateOpenApiDocs")
    if (generateOpenApiDocs == null) System.err.println("⭕️ `generateOpenApiDocs` was not attached to `apiGen` task! You need to run: ➡ `gradle generateOpenApiDocs` ⬅ first")
    else {
        mustRunAfter(generateOpenApiDocs)
        dependsOn(generateOpenApiDocs)
        println("📝 Registered running task before `apiGen`: generating openapi spec with `generateOpenApiDocs` ")
    }
}

val Project.apiGen: TaskProvider<OpenApiAxiosTypeScript>
    get() = tasks.named<OpenApiAxiosTypeScript>("apiGen")
