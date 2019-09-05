import kotlin.String

/**
 * Generated by https://github.com/jmfayard/buildSrcVersions
 *
 * Update this file with
 *   `$ ./gradlew buildSrcVersions`
 */
object Libs {
  const val guava: String = "com.google.guava:guava:" + Versions.guava

  /**
   * http://code.google.com/p/google-guice/
   */
  const val guice: String = "com.google.inject:guice:" + Versions.guice

  const val io_vertx_vertx_plugin_gradle_plugin: String =
      "io.vertx.vertx-plugin:io.vertx.vertx-plugin.gradle.plugin:" +
      Versions.io_vertx_vertx_plugin_gradle_plugin

  const val vertx_core: String = "io.vertx:vertx-core"

  const val vertx_stack_depchain: String = "io.vertx:vertx-stack-depchain:" +
      Versions.vertx_stack_depchain
}
