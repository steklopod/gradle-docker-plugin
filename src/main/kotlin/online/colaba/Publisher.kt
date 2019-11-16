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

open class Publisher : DefaultTask() {
    companion object {
        private const val defaultHost = "5.63.155.194"
        private const val defaultUser = "root"

        const val publishTaskName = "publish"
        val defaultRsaPath = "$userHomePath/.ssh/id_rsa".normalizeForWindows()

        fun defaultServer(
                targetHost: String? = defaultHost, sshUser: String? = defaultUser, idRsaPath: String? = defaultRsaPath
        ) = Remote(sshUser).apply { host = targetHost; user = sshUser; identity = File(idRsaPath) }

        fun Service.runSessions(action: RunHandler.() -> Unit) = run(delegateClosureOf(action))
        fun RunHandler.session(vararg remotes: Remote, action: SessionHandler.() -> Unit) = session(*remotes, delegateClosureOf(action))

        fun SessionHandler.put(from: Any, into: Any) = put(hashMapOf("from" to from, "into" to into))
    }

    init {
        group = publishTaskName; description = "Publish by ssh"
    }

    @get:Input
    var host = defaultHost
    @get:Input
    var user = defaultUser
    @get:Input
    var idRsaPath = defaultRsaPath
    @get:Input
    var backendJar = "${project.rootDir}/backend/${project.buildDir}/libs/".normalizeForWindows()

    @TaskAction
    fun run() {
        val projectName = project.name
        println("\uD83D\uDCE6 [backend] *.jar distribution location: $backendJar")
        println("> project name: $projectName")

        val server = defaultServer(targetHost = host, sshUser = user, idRsaPath = idRsaPath)
        val ssh = Ssh.newService()
        ssh.runSessions {
            session(server) {
                // TODO
                execute("echo CURRENT_FOLDER \$PWD")
                execute("touch 666.txt")
                execute("ls")
//                put("$libsFolder/libs/1.txt", projName)
            }
        }


    }

}

fun Project.registerPublisherTask() = tasks.register<Publisher>(Publisher.publishTaskName)

val Project.publish: TaskProvider<Publisher>
    get() = tasks.named<Publisher>(Publisher.publishTaskName)
