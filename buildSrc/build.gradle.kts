plugins {
  `kotlin-dsl`
}

repositories {
  jcenter()
}

kotlinDslPluginOptions {
  experimentalWarning.set(false)
}

dependencies {
  implementation("khttp:khttp:0.1.0")
}
