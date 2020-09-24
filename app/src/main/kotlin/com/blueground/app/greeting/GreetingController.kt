package com.blueground.app.greeting

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController(value = "greeting")
class GreetingController {

  @GetMapping
  fun getGreeting(): String {
    return "OK"
  }
}

