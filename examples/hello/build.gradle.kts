plugins { id("online.colaba.docker") version "0.2.2" }

repositories { jcenter(); mavenCentral() }

defaultTasks("tasks", "deploy")

tasks{
    val remove by registering(Docker::class) { exec = "rm -f ${project.name}" }
}
