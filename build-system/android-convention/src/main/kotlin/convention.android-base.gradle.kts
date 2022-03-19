import com.android.build.gradle.BaseExtension
import com.orcchg.stockshock.infra.plugins.utility.withVersionCatalogs

plugins {
    id("org.gradle.android.cache-fix")
}

withVersionCatalogs {
    configure<BaseExtension> {
        sourceSets {
            named("main").configure { java.srcDir("src/main/kotlin") }
            named("androidTest").configure { java.srcDir("src/androidTest/kotlin") }
            named("test").configure { java.srcDir("src/test/kotlin") }
        }

        compileSdkVersion(versions.androidSdk.compileSdkVersion.get().toInt())

        compileOptions {
            sourceCompatibility = JavaVersion.valueOf(versions.javaVersion.get())
            targetCompatibility = JavaVersion.valueOf(versions.javaVersion.get())
        }

        defaultConfig {
            minSdk = versions.androidSdk.minSdkVersion.get().toInt()
            targetSdk = versions.androidSdk.targetSdkVersion.get().toInt()

            versionCode = 1
            versionName = "1.1.0"
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            testApplicationId = "com.orcchg.yandexcontest.test"
        }

        lintOptions {
            isAbortOnError = false
            isWarningsAsErrors = true
            textReport = true
            isQuiet = true
        }

        @Suppress("UnstableApiUsage")
        with(buildFeatures) {
            aidl = false
            compose = false
            buildConfig = false
            prefab = false
            renderScript = false
            resValues = false
            shaders = false
            viewBinding = false
        }
    }
}
