val jacocoVersion: String by project

apply(plugin = "jacoco")

val jacocoExcludes = listOf(
  "**/App.class",
  "**/AppKt.class",
  "**/*ErrorCode.class",
  "**/*ValidationGroup**",
  "**/foundation/**"
  )

tasks.withType<JacocoBase>().configureEach {
  version = jacocoVersion
}

tasks.withType<JacocoReport> {
  reports {
    html.destination = file("$buildDir/reports/jacoco/html")
  }
  afterEvaluate {
    classDirectories.setFrom(files(classDirectories.files.map {
      fileTree(it) {
        setExcludes(jacocoExcludes)
      }
    }))
  }
}

tasks.withType<JacocoCoverageVerification> {
  violationRules {
    rule {
      limit {
        counter = "INSTRUCTION"
        minimum = "0.90".toBigDecimal()
      }
    }
    rule {
      limit {
        counter = "BRANCH"
        minimum = "0.80".toBigDecimal()
      }
    }
  }
  afterEvaluate {
    classDirectories.setFrom(files(classDirectories.files.map {
      fileTree(it) {
        setExcludes(jacocoExcludes)
      }
    }))
  }
}
