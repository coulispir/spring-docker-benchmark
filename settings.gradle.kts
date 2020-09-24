pluginManagement {

  resolutionStrategy {
    eachPlugin {
      val props = gradle.rootProject.properties

      if (requested.id.namespace?.startsWith("org.jetbrains.kotlin") == true) {
        useVersion(props["kotlinVersion"] as String)
      }

      if (requested.id.id == "org.springframework.boot") {
        useVersion(props["springBootVersion"] as String)
      }
    }
  }
}
rootProject.name = "boilerplate"
include("app")
include("functionaltests")
