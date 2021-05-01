plugins { id("online.colaba.docker") version "1.2.30" }

repositories { mavenCentral() }

defaultTasks("tasks", "deploy")


/*
    val remove by registering(Docker::class) {
        exec = "rm -f ${project.name}"
    }
*/

}
