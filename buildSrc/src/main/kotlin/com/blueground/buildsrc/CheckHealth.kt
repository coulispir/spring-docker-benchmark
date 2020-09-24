package com.blueground.buildsrc

import khttp.responses.Response
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.impldep.org.apache.http.HttpStatus

open class CheckHealth : DefaultTask() {

  @TaskAction
  fun checkHealth() {
    val retries = 100
    val interval = 2
    val url = "http://localhost:8080/actuator/health"

    for (n in retries downTo 1) {

      var response: Response? = null

      try {
        response = khttp.get(url, timeout = 2.0)
      } catch (e: Exception) {
        // just catch the exception
      } finally {
        val statusCode = getStatusCode(response)
        if (statusCode == HttpStatus.SC_OK) {
          logger.warn("Service started!")
          return
        } else {
          val msg = getStatusMessage(statusCode)
          logger.warn("$msg. $n retries remaining. Check again in $interval seconds")
        }
      }

      // wait for 'interval' seconds before next retry
      Thread.sleep(interval * 1000L)
    }

    val totalSeconds = retries * interval
    throw GradleException("Service failed to start in $totalSeconds seconds")
  }

  private fun getStatusCode(response: Response?): Int = response?.statusCode?.let { it } ?: run { 0 }

  private fun getStatusMessage(statusCode: Int): String = if (statusCode == 0) "Service is not responding" else "Service responds with [$statusCode]"
}
