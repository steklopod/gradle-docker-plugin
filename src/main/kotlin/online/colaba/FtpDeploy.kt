package online.colaba

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering

const val ftpPrefix = "ftp"

class FtpDeploy : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        description = "FTP deploy needed ssh-tasks"

        registerPublisherTask()

        publish{
            commands = "echo DEFULT_PUBLISH_COMMAND"
        }

        tasks {
            val ssh by registering(Publisher::class) {
                commands = "echo SSH_COMMAND"
                group = ftpPrefix
            }
        }
    }
}
