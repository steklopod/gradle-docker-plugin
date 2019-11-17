package online.colaba

import online.colaba.Docker.Companion.detachFlag
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering

const val dockerPrefix = "docker"

class Docker : Plugin<Project> {
    companion object{
        const val detachFlag = "--detach"
        const val recreateFlags = "--build --force-recreate $detachFlag"
    }

    override fun apply(project: Project): Unit = project.run {
        description = "Docker needed tasks"

        val name = project.name
        val dockerGroupName = "$dockerPrefix-$name"

        registerExecutorTask()

        tasks {
            val stop by registering(Executor::class) {
                command ="$dockerPrefix $stopGroup $name"; group = dockerGroupName
            }
            val containers by registering(Executor::class) {
                containers(); group = dockerGroupName
            }
            val remove by registering(DockerRemove::class) {
                containers()
                remove = name; group = dockerGroupName
                containers()
            }
            val deploy by registering(Executor::class) {
                containers()
                dockerComposeUpRebuild(); group = dockerGroupName
                containers()
            }
            val deployDev by registering(Executor::class) {
                containers()
                dockerComposeUpRebuildDev(); group = dockerGroupName
                containers()
            }

            val redeploy by registering(Executor::class)  {
                containers()
                dependsOn(remove); finalizedBy(deploy); group = dockerGroupName
                containers()
            }
            val redeployDev by registering(Executor::class)  {
                containers()
                dependsOn(remove); finalizedBy(deployDev); group = dockerGroupName
                containers()
            }
        }
    }
}


fun Executor.containers() { command = "$dockerPrefix ps" }

fun Executor.dockerRemove(dockerComposeCommand: String) { command = "$dockerPrefix-compose $dockerComposeCommand"; group = dockerPrefix }

fun Executor.dockerCompose(dockerComposeCommand: String) { command = "$dockerPrefix-compose $dockerComposeCommand"; group = dockerPrefix }

fun Executor.dockerComposeUp() { dockerCompose("up $detachFlag") }
fun Executor.dockerComposeUpDev(fileName: String? = "$dockerPrefix-compose.dev.yml") { dockerCompose("-f $fileName up $detachFlag") }

fun Executor.dockerComposeUpRebuild(command: String? = "") { dockerCompose("up ${Docker.recreateFlags} $command") }

fun Executor.dockerComposeUpRebuildDev(fileName: String? = "$dockerPrefix-compose.dev.yml") { dockerCompose("-f $fileName up ${Docker.recreateFlags}") }
