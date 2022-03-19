package com.orcchg.stockshock.infra.plugins.androidjar

import org.codehaus.groovy.runtime.ResourceGroovyMethods.newReader
import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import java.io.File
import java.util.Properties

/** 
 * @author Maxim Alov
 */
open class AndroidJarPluginExtension(private val project: Project) {

    /**
     * Looks for the location of installed Android SDK
     * @param sdk Android Target Version
     * @return location, where is the Android SDK of the version specified
     */
    fun find(sdk: Int): ConfigurableFileCollection {
        return project.files("${findSdkLocation()}/platforms/android-$sdk/android.jar")
    }

    private fun findSdkLocation(): File {
        val localPropertiesFile = File(project.rootDir, "local.properties")
        return if (localPropertiesFile.exists()) {
            getLocalPropertiesAndroidPathFile(localPropertiesFile)
        } else {
            val topLevelLocalPropertiesFile = File("${project.rootDir}/../", "local.properties")
            if (topLevelLocalPropertiesFile.exists()) {
                getLocalPropertiesAndroidPathFile(topLevelLocalPropertiesFile)
            } else {
                getSystemAndroidPathFile()
            }
        }.also { project.logger.info("path to sdk: $it") }
    }

    private fun getLocalPropertiesAndroidPathFile(localPropertiesFile: File): File {
        project.logger.info("load sdk path from properties: $localPropertiesFile")
        val localProperties = Properties()
        localProperties.load(newReader(localPropertiesFile))

        val sdkDirProp = localProperties.getProperty("sdk.dir")
        val androidDirProp = localProperties.getProperty("android.dir")

        return when {
            sdkDirProp != null -> File(sdkDirProp)
            androidDirProp != null -> File(project.rootDir, androidDirProp)
            else -> throw RuntimeException(
                """
                    Trying to resolve path to sdk from $localPropertiesFile, but the property sdk.dir was not set.
                    Please set the sdk.dir properly or delete that file.
                    The latter is not recommended, because it might cause issues with the Android Studio.
                """.trimIndent()
            )
        }
    }

    private fun getSystemAndroidPathFile(): File {
        val androidHomeEnvVar = System.getenv("ANDROID_HOME")
        val androidHomeProperty = System.getProperty("android.home")
        if (project.logger.isInfoEnabled) {
            project.logger.info("Loading sdk path from system. env=$androidHomeEnvVar, prop=$androidHomeProperty")
            project.logger.warn("Environment variables:")
            System.getenv().map { (key, value) -> project.logger.warn("$key=$value") }
        }

        return when {
            androidHomeEnvVar != null -> File(androidHomeEnvVar)
            androidHomeProperty != null -> File(androidHomeProperty)
            else -> throw RuntimeException(
                """
                    Unable to find path to Android SDK:
                    Environment variable ANDROID_HOME = $androidHomeEnvVar
                    System property android.home = $androidHomeProperty
                    Try to add the following export: ANDROID_HOME=/your/path/to/android/sdk
                    --OR--
                    Try to add to file ~/.gradle/gradle.properties: android.home=/your/path/to/android/sdk
                """.trimIndent()
            )
        }
    }
}
