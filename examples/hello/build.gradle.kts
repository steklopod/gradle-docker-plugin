plugins { id("online.colaba.docker") version "1.1.2" }

repositories { mavenCentral() }

defaultTasks("tasks", "deploy")


/*
    val remove by registering(Docker::class) {
        exec = "rm -f ${project.name}"
    }
*/

}
