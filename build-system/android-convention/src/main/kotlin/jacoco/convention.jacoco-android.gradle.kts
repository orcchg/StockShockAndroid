import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.BaseVariant
import com.orcchg.stockshock.infra.plugins.utility.withVersionCatalogs

/**
 * Convention plugin, which configures JaCoCo for android subprojects.
 */

plugins {
    id("convention.jacoco")
}

description = "Plugin, which configures JaCoCo for android subprojects"

project.afterEvaluate {
    withVersionCatalogs {
        configure<BaseExtension> {
            val androidExtension = this
            jacoco {
                jacocoVersion = versions.jacoco.get()
            }

            val variants: Collection<BaseVariant> = if (isAndroidLibraryProject()) {
                (androidExtension as LibraryExtension).libraryVariants
            } else if (isAndroidApplicationProject() || isAndroidDynamicFeatureProject()) {
                (androidExtension as AppExtension).applicationVariants
            } else {
                emptyList()
            }

            variants.forEach { variant ->
                val productFlavorName = variant.flavorName
                val buildType = variant.buildType
                val buildTypeName = buildType.name
                val sourceName: String
                val sourcePath: String

                if (productFlavorName.isNullOrBlank()) {
                    sourceName = buildTypeName
                    sourcePath = buildTypeName
                } else {
                    sourceName = "${productFlavorName}${buildTypeName.capitalize()}"
                    sourcePath = "${productFlavorName}/${buildTypeName}"
                }

                val jacocoTestReportTaskName = "jacocoTestReport${sourceName.capitalize()}"
                val combinedTestReportTaskName = "combinedTestReport${sourceName.capitalize()}"
                val jvmTestTaskName = "test${sourceName.capitalize()}UnitTest"
                val instrumentationTestTaskName = "create${sourceName.capitalize()}CoverageReport"

                addJacocoTask(
                    combined = false,
                    sourceName = sourceName,
                    sourcePath = sourcePath,
                    buildTypeName = buildTypeName,
                    productFlavorName = productFlavorName,
                    taskName = jacocoTestReportTaskName,
                    jvmTestTaskName = jvmTestTaskName,
                    instrumentationTestTaskName = instrumentationTestTaskName
                )

                if (buildType.isTestCoverageEnabled) {
                    addJacocoTask(
                        combined = true,
                        sourceName = sourceName,
                        sourcePath = sourcePath,
                        buildTypeName = buildTypeName,
                        productFlavorName = productFlavorName,
                        taskName = combinedTestReportTaskName,
                        jvmTestTaskName = jvmTestTaskName,
                        instrumentationTestTaskName = instrumentationTestTaskName
                    )
                }
            } // variants
        }
    }
}

fun isAndroidApplicationProject(): Boolean =
    project.plugins.hasPlugin("com.android.application")

fun isAndroidLibraryProject(): Boolean =
    project.plugins.hasPlugin("com.android.library")

fun isAndroidDynamicFeatureProject(): Boolean =
    project.plugins.hasPlugin("com.android.dynamic-feature")

fun isKotlinAndroidProject(): Boolean =
    project.plugins.hasPlugin("org.jetbrains.kotlin.android")

fun isKotlinMultiplatformProject(): Boolean =
    project.plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")

fun addJacocoTask(
    combined: Boolean,
    sourceName: String,
    sourcePath: String,
    buildTypeName: String,
    productFlavorName: String,
    taskName: String,
    jvmTestTaskName: String,
    instrumentationTestTaskName: String
) {
    val destinationDir =
        if (combined) {
            "$buildDir/reports/jacocoCombined"
        } else {
            "$buildDir/reports/jacoco"
        }

    tasks.register<JacocoReport>(taskName).configure {
        group = "Reporting"
        description = "Generate Jacoco coverage reports after running $sourceName tests."

        if (combined) {
            dependsOn(jvmTestTaskName, instrumentationTestTaskName)
        } else {
            dependsOn(jvmTestTaskName)
        }

        reports {
            with(xml) {
                required.set(true)
                outputLocation.set(file("$destinationDir/${sourceName}/jacoco.xml"))
            }
            with(csv) {
                required.set(true)
                outputLocation.set(file("$destinationDir/${sourceName}/jacoco.csv"))
            }
            with(html) {
                required.set(true)
                outputLocation.set(file("$destinationDir/${sourceName}"))
            }
        } // reports

        val classpaths = mutableListOf(
            "$buildDir/intermediates/javac/$sourceName/classes",
            "$buildDir/tmp/kotlin-classes/$sourceName",
            "**/intermediates/classes/${sourcePath}/**",
            "**/intermediates/javac/${sourceName}/*/classes/**", // Android Gradle Plugin 3.2.x support.
            "**/intermediates/javac/${sourceName}/classes/**" // Android Gradle Plugin 3.4 and 3.5 support.
        )

        if (isKotlinAndroidProject() || isKotlinMultiplatformProject()) {
            classpaths.add("**/tmp/kotlin-classes/${sourcePath}/**")
            if (productFlavorName.isNotBlank()) {
                classpaths.add("**/tmp/kotlin-classes/${productFlavorName}${buildTypeName.capitalize()}/**")
            }
        }

        classDirectories.from(
            project.fileTree(
                "dir" to buildDir,
                "includes" to classpaths,
                "excludes" to getExcludes()
            )
        )

        val coverageSourceDirs = mutableListOf(
            "$projectDir/src/main/kotlin",
            "$projectDir/src/main/java",
            "$projectDir/src/$buildTypeName/kotlin",
            "$projectDir/src/$buildTypeName/java"
        )

        if (productFlavorName.isNotBlank()) {
            with(coverageSourceDirs) {
                add("$projectDir/src/$productFlavorName/kotlin")
                add("$projectDir/src/$productFlavorName/java")
            }
        }

        sourceDirectories.from(project.files(coverageSourceDirs))
        additionalSourceDirs.from(project.files(coverageSourceDirs))
        executionData.from(project.files("$buildDir/jacoco/$jvmTestTaskName.exec"))

        if (combined) {
            val instrumentationTestCoverageDirs =
                project.fileTree("$buildDir/outputs/code_coverage")
                    .matching { include("**/*.ec") }

            val allCodeCoverageFiles = instrumentationTestCoverageDirs.files + executionData.files
            executionData.setFrom(allCodeCoverageFiles)
        }
    } // configure task
}

fun getExcludes(): List<String> =
    listOf(
        "jdk.internal.*",
        """**/R.class""",
        """**/R2.class""", // ButterKnife Gradle Plugin.
        """**/R$*.class""",
        """**/R2$*.class""", // ButterKnife Gradle Plugin.
        """**/*$$*""",
        "**/*\$ViewInjector*.*", // Older ButterKnife Versions.
        "**/*\$ViewBinder*.*", // Older ButterKnife Versions.
        """**/*_ViewBinding*.*""", // Newer ButterKnife Versions.
        """**/BuildConfig.*""",
        """**/Manifest*.*""",
        "**/*\$Lambda$*.*", // Jacoco can not handle several "$" in class name.
        """**/*Dagger*.*""", // Dagger auto-generated code.
        """**/*MembersInjector*.*""", // Dagger auto-generated code.
        """**/*_Provide*Factory*.*""", // Dagger auto-generated code.
        """**/*_Factory*.*""", // Dagger auto-generated code.
        "**/*\$JsonObjectMapper.*", // LoganSquare auto-generated code.
        "**/*\$inlined$*.*", // Kotlin specific, Jacoco can not handle several "$" in class name.
        "**/*\$Icepick.*", // Icepick auto-generated code.
        "**/*\$StateSaver.*", // android-state auto-generated code.
        """**/*AutoValue_*.*""", // AutoValue auto-generated code.
        """android/databinding/**/*.class""",
        """**/android/databinding/*Binding.class""",
        """**/android/databinding/*""",
        """**/androidx/databinding/*""",
        """**/BR.*""",
        """**/*Test*.*""",
        """android/**/*.*""",
        """**/*MapperImpl*.*""",
        """**/*Component*.*""",
        """**/*BR*.*""",
        """**/*Companion*.*""",
        """**/*Module*.*""",
        """**/*Hilt*.*""",
        """**/*_MembersInjector.class""",
        """**/*Extensions*.*""",
        "**/*\$Result.*",
        "**/*\$Result$*.*",
        """**/*JsonAdapter.*""",
    )
