allprojects {
  repositories {
    jcenter()
    mavenCentral()
  }

  extra["doRelease"] = project.gradle.startParameter.taskNames.contains("release")
  extra["doBuildDocs"] = project.gradle.startParameter.taskNames.contains("asciidoctor") || extra.get("doRelease") == true
}

subprojects {
  apply(plugin = "java")
  apply(plugin = "groovy")
  apply(plugin = "codenarc")

  if (name == "app") {
    project.extra["dockerImageTag"] = "latest"
  }
}

gradle.buildFinished {
  // delete root build directory which is created by default in multi-module projects
  delete(rootProject.buildDir)
}
