package online.colaba

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering

class DockerMain : Plugin<Project> {
    companion object {
        const val frontendService = "frontend"
        const val backendService = "backend"
        const val nginxService = "nginx"

        const val dockerPrefix = "docker"

        const val buildGroup = "build"
        const val removeGroup = "remove"
    }

    override fun apply(project: Project): Unit = project.run {
        description = "Docker needed tasks for root multi-project"

        registerExecutorTask()

        tasks {
            val dockerPs by registering(Executor::class) { containers(); group = dockerPrefix }

            val build by registering {
                group = buildGroup
                dependsOn(":$frontendService:$buildGroup")
                finalizedBy(":$backendService:$buildGroup")
            }

            val removeBackAndFront by registering {
                group = dockerPrefix
                dependsOn(":$frontendService:$removeGroup")
                finalizedBy(":$backendService:$removeGroup")
            }

            val removeAll by registering {
                group = dockerPrefix
                dependsOn(":$nginxService:$removeGroup")
                finalizedBy(removeBackAndFront)
            }

            val compose by registering(Executor::class) {
                dockerComposeUpRebuild(); finalizedBy(dockerPs)
            }

            val composeDev by registering(Executor::class) {
                dependsOn(":$backendService:assemble")
                dockerComposeUpRebuildDev()
                finalizedBy(dockerPs)
            }

            val prune by registering(Executor::class) {
                command = "$dockerPrefix system prune -fa"; finalizedBy(dockerPs);
                group = dockerPrefix
            }
            val buildComposeDev by registering { dependsOn(build); finalizedBy(composeDev); group = "init" }

            val composeNginx by registering(Executor::class) { dockerComposeUpRebuild(nginxService) }
            val composeBack by registering(Executor::class) { dockerComposeUpRebuild(backendService) }
            val composeFront by registering(Executor::class) { dockerComposeUpRebuild(frontendService) }

            val recomposeAll by registering { dependsOn(removeAll); finalizedBy(compose); group = dockerPrefix }

            val recomposeAllDev by registering { dependsOn(removeAll); finalizedBy(composeDev); group = dockerPrefix }
        }
    }
}

