package com.orcchg.stockshock.infra.plugins.androidjar

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.orcchg.stockshock.infra.plugins.utility.withVersionCatalogs

/**
 * Plugin, which adds the ability to look for the location where Android SDK is installed
 * 
 * @author Maxim Alov
 */
class AndroidJarPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.withVersionCatalogs {
            target.extensions.add(
                "androidJar",
                AndroidJarPluginExtension(target).find(versions.androidSdk.targetSdkVersion.get().toInt())
            )
        }
    }
}
