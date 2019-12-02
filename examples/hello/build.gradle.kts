plugins { id("online.colaba.docker") version "0.2.4" }

repositories { jcenter(); mavenCentral() }

defaultTasks("tasks", "deploy")

tasks{
    withType<Wrapper> { gradleVersion = "6.0" }

/*
    val remove by registering(Docker::class) {
        exec = "rm -f ${project.name}"
    }
*/

}
