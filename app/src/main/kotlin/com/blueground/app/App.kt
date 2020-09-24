package com.blueground.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import java.util.Locale
import java.util.TimeZone

@SpringBootApplication
@ComponentScan("com.blueground")
class App

fun main(args: Array<String>) {
  Locale.setDefault(Locale.US)
  TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
  runApplication<App>(*args)
}
