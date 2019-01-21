import kotlin.String

/**
 * Generated by https://github.com/jmfayard/buildSrcVersions
 *
 * Update this file with
 *   `$ ./gradlew buildSrcVersions` */
object Libs {
    /**
     * https://github.com/square/okhttp */
    const val okhttp: String = "com.squareup.okhttp3:okhttp:" + Versions.okhttp

    /**
     * https://github.com/square/okio/ */
    const val okio: String = "com.squareup.okio:okio:" + Versions.okio

    const val io_vertx_vertx_plugin_gradle_plugin: String =
            "io.vertx.vertx-plugin:io.vertx.vertx-plugin.gradle.plugin:" +
            Versions.io_vertx_vertx_plugin_gradle_plugin

    const val vertx_core: String = "io.vertx:vertx-core"

    const val vertx_stack_depchain: String = "io.vertx:vertx-stack-depchain:" +
            Versions.vertx_stack_depchain

    const val vertx_web: String = "io.vertx:vertx-web"

    /**
     * https://github.com/zTrap/Android-Iconics-Kt */
    const val ru_ztrap_iconics_core_ktx: String = "ru.ztrap.iconics:core-ktx:" +
            Versions.ru_ztrap_iconics_core_ktx
}
