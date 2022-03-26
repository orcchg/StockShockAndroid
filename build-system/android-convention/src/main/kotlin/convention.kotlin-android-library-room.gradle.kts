import com.orcchg.stockshock.infra.plugins.utility.withVersionCatalogs

plugins {
    id("convention.kotlin-android-library")
    id("org.jetbrains.kotlin.kapt")
}

android {
    defaultConfig {
        room {
            schemaLocationDir.set(file("$projectDir/schemas"))
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }
}

withVersionCatalogs {
    dependencies {
        implementation(androidx.room)
        kapt(androidx.room.compiler)
    }
}