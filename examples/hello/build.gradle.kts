plugins { id("online.colaba.docker") version "0.1.7" }

repositories { jcenter(); mavenCentral() }

defaultTasks("tasks", "deploy")

