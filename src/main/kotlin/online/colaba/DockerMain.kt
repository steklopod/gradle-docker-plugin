package online.colaba

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering

class DockerMain : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        description = "Docker needed tasks for root multi-project"
        tasks {
            val containers by registering(Executor::class)  { containers() }

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
                dockerComposeUpRebuild(); finalizedBy(containers)
            }

            val composeDev by registering(Executor::class) {
                dependsOn(":$backendService:assemble")
                dockerComposeUpRebuildDev()
                finalizedBy(containers)
            }

            val prune by registering(Executor::class) { command ="$dockerPrefix system prune -fa"; finalizedBy(containers) ; group =
                dockerPrefix
            }

            val composeNginx by registering(Executor::class) { dockerComposeUpRebuild(nginxService) }
            val composeBack by registering(Executor::class) { dockerComposeUpRebuild(backendService) }
            val composeFront by registering(Executor::class) { dockerComposeUpRebuild(frontendService) }

            val recomposeAll by registering { dependsOn(removeAll); finalizedBy(compose); group = dockerPrefix }

            val recomposeAllDev by registering { dependsOn(removeAll); finalizedBy(composeDev); group = dockerPrefix }
        }
    }
}

