val doRelease: Boolean by extra
val dockerImageTag: String by project.extra

tasks.register<com.blueground.buildsrc.CheckHealth>("checkHealth") {
  dependsOn("dockerStart")
  description = "Checks health status."
  group = ApplicationPlugin.APPLICATION_GROUP
}

tasks.register("dockerStart", Exec::class) {
  doFirst {
    val tag = project.extra["dockerImageTag"] as String
    environment("BOILERPLATE_SRC_VERSION", tag)
  }
  dependsOn("dockerBuildImage")
  description = "Runs a docker container."
  group = ApplicationPlugin.APPLICATION_GROUP
  commandLine = if (project.gradle.startParameter.taskNames.contains("dockerStart")) {
    listOf("docker-compose", "up")
  } else {
    listOf("docker-compose", "up", "-d")
  }
}

tasks.register("dockerStop", Exec::class) {
  description = "Stops and removes a docker container."
  group = ApplicationPlugin.APPLICATION_GROUP
  commandLine = listOf("docker-compose", "down")
}

tasks.register("dockerBuildImage", Exec::class) {
  doFirst {
    val tag = project.extra["dockerImageTag"] as String
    environment("BOILERPLATE_SRC_VERSION", tag)
  }
  dependsOn(project(":app").tasks.getByName("build"))
  description = "Builds a docker image and tags with 'latest' or with the commit hash in case of a release"
  group = ApplicationPlugin.APPLICATION_GROUP
  commandLine = listOf("docker-compose", "build", "boilerplate")
}
