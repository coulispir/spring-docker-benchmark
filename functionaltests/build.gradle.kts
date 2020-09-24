val spockVersion: String by project
val doBuildDocs: Boolean by extra

dependencies {
  implementation("org.spockframework:spock-core:$spockVersion")
  implementation("io.jsonwebtoken:jjwt:0.9.1")
  implementation("org.codehaus.groovy.modules.http-builder:http-builder:0.7.1")
}

tasks.test {
  enabled = false
}

tasks.register<Test>("functionalTest") {
  dependsOn(project(":app").tasks.getByName("checkHealth"))
  finalizedBy(project(":app").tasks.getByName("dockerStop"))
  shouldRunAfter(project(":app").tasks.test)

  description = "Runs the functional tests."
  group = LifecycleBasePlugin.VERIFICATION_GROUP

  if (!doBuildDocs) {
    exclude("**/StaticDocsSpec.class")
  }
}
