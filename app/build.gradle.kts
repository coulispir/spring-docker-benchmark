@file:Suppress("UNCHECKED_CAST")

val appVersion: String by project
val kotlinVersion: String by project
val doBuildDocs: Boolean by extra
val doRelease: Boolean by extra

plugins {
  id("org.jetbrains.kotlin.jvm")
  id("org.jetbrains.kotlin.plugin.spring")
  id("org.springframework.boot")
}

apply(plugin = "io.spring.dependency-management")
apply(from = rootProject.file("gradle/docker.gradle.kts"))

group = "com.blueground"
version = appVersion
springBoot {
  buildInfo {
    properties {
      artifact = "boilerplate"
      version = appVersion
      group = "com.blueground"
      name = "Boilerplate application"
      time = null // ignore build time to gain performance. Otherwise the task will never be up-to-date
    }
  }
}

dependencies {

  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
  implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.7")

  implementation("org.springframework.boot:spring-boot-starter-web")

  implementation("commons-io:commons-io:2.6")
  implementation("ch.qos.logback:logback-core:1.2.3")
  implementation("ch.qos.logback.contrib:logback-json-classic:0.1.5")
  implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions {
    jvmTarget = "1.8"
    freeCompilerArgs = listOf("-Xjsr305=strict")
  }
}
