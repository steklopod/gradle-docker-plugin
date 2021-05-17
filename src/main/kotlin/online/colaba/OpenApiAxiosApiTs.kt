package online.colaba

import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import java.io.File


open class OpenApiAxiosApiTs : Executor() {
    init {
        group = "$dockerPrefix-${project.name}"
        description = "Generating [ TypeScript Axios API] from OPEN API docs.yaml."
    }

    @get:Input var fromLocation : String = "/src/test/resources"
    @get:Input var fromFilename : String = "docs.yaml"

    @get:Input var toFolder   : String = "/frontend/types/api"
    @get:Input var toFilename : String = "schema-${project.name}"

    @get:Input var generatorName : String  = "typescript-axios"
    @get:Input var addInfo       : String = "library=spring-boot,beanValidations=true,swaggerAnnotations=true,supportsES6=true,withInterfaces=true"

    @get:Input var onlyTS : Boolean = true

    @TaskAction fun run() {
        val from: File = fromOpenApiScheme("$fromLocation/$fromFilename")
        val to = File("${project.rootDir}/$toFolder".normal())

        when {
            from.exists() -> {
                println("\t 👉🏻 [${project.name.toUpperCase()}] 🔫 Found schema: $fromFilename")
                println("📌 FROM: $from")
                println("📌 TO: $to")

                val generator = "--generator-name $generatorName "
                val additional = "--additional-properties=$addInfo"
                super.command = "openapi-generator-cli generate -i $from -o $to $generator $additional"
                super.exec()

                val fromSubprojectRoot = "${project.rootDir}/${project.name}"

                File("$fromSubprojectRoot/Users").deleteRecursively()
                println("\n🕷 Removed: ${project.name}/Users")

                File("$to/.openapi-generator").deleteRecursively()
                println("🕷 Removed: $to/.openapi-generator")

                "openapitools.json".run {
                    File("$fromSubprojectRoot/$this").delete()
                    println("🕷 Removed: ${project.name}/$this")

                    File("${project.rootDir}/$this").delete()
                    println("🕷 Removed: ${project.name}/$this \n")
                }

                if (onlyTS) to.walk().forEach {
                    if (it.isFile && !it.name.endsWith(".ts")) {
                        it.delete()
                        println("🕷 Removed: ${it.toString().substringAfter(project.rootDir.toString())}")
                    }
                }
            }

            from.parentFile.exists() && project.name != "gateway" -> {
                println("🔮 [OPEN API] Before run this task: 🧬 install local `openapi-generator-cli`")
                println("\t you should have [$fromFilename] openapi file in 🧿 ${project.name}$fromLocation 🧿 and [${project.rootDir}${toFolder}] folder")
                System.err.println("\t 🧨 [${project.name}] 🧨 || Not found file: $fromFilename ($from)\n")
            }
    } }


}

fun Project.registerOpenApiAxiosApiTsTask() = tasks.register<OpenApiAxiosApiTs>("api")
val Project.api: TaskProvider<OpenApiAxiosApiTs>
    get() = tasks.named<OpenApiAxiosApiTs>("api")
