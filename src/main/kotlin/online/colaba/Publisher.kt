package online.colaba

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.delegateClosureOf
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.hidetake.groovy.ssh.Ssh
import org.hidetake.groovy.ssh.core.Remote
import org.hidetake.groovy.ssh.core.RunHandler
import org.hidetake.groovy.ssh.core.Service
import org.hidetake.groovy.ssh.session.SessionHandler
import java.io.File

const val publishTaskName = "publish"

open class Publisher : DefaultTask() {
    init {
        group = publishTaskName
        description = "Publish by FTP your distribution with SSH commands"
    }
    companion object {
        private const val defaultHost = "5.63.155.194"
        private const val defaultUser = "root"
        private val defaultRsaPath = "$userHomePath/.ssh/id_rsa".normalizeForWindows()

        private fun connect(
                targetHost: String? = defaultHost, sshUser: String? = defaultUser, idRsaPath: String? = defaultRsaPath
        ) = Remote(sshUser).apply { host = targetHost; user = sshUser; identity = File(idRsaPath) }

      }

    @get:Input
    var host = defaultHost
    @get:Input
    var user = defaultUser
    @get:Input
    var idRsaPath = defaultRsaPath
    @get:Input
    var backendJar = "${project.rootDir}/backend/${project.buildDir}/libs/".normalizeForWindows()
    @get:Input
    var commands = "echo CURRENT_FOLDER \$PWD"
//    var commands = "touch 666.txt"

    @TaskAction
    fun run() {
        val projectName = project.name
        println("\uD83D\uDCE6 [backend] *.jar distribution location: $backendJar")
        println("> project name: $projectName")

        val server = connect(targetHost = host, sshUser = user, idRsaPath = idRsaPath)
        val ssh = Ssh.newService()
        ssh.runSessions {
            session(server) {
                execute(commands)
                execute("ls -a")
//                put("$libsFolder/libs/1.txt", projName)
            }
        }


    }
    private fun Service.runSessions(action: RunHandler.() -> Unit) = run(delegateClosureOf(action))
    private fun RunHandler.session(vararg remotes: Remote, action: SessionHandler.() -> Unit) = session(*remotes, delegateClosureOf(action))

    private fun SessionHandler.put(from: Any, into: Any) = put(hashMapOf("from" to from, "into" to into))
}

fun Project.registerPublisherTask() = tasks.register<Publisher>(publishTaskName)

val Project.publish: TaskProvider<Publisher>
    get() = tasks.named<Publisher>(publishTaskName)
