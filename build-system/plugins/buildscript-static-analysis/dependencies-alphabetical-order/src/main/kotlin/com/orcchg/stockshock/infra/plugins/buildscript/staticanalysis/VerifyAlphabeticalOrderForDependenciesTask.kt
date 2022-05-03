package com.orcchg.stockshock.infra.plugins.buildscript.staticanalysis

import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskExecutionException
import org.gradle.kotlin.dsl.withType

open class VerifyAlphabeticalOrderForDependenciesTask : DefaultTask() {

    private val excludedDependenciesGroups = listOf("org.jetbrains.kotlin")

    init {
        group = "Verification"
        description = "Verifies that dependencies are listed in alphabetical order for each configuration"
    }

    @TaskAction
    fun verifyDependenciesAlphabeticalOrder() {
        val moduleDependencies = mutableMapOf<String, MutableList<String>>()
        val projectDependencies = mutableMapOf<String, MutableList<String>>()

        project.configurations.configureEach {
            val configuration = this
            dependencies.withType<ExternalModuleDependency>()
                .matching { it.group !in excludedDependenciesGroups }
                .all {
                    moduleDependencies.putIfAbsent(configuration.name, mutableListOf())
                    moduleDependencies[configuration.name]!!.add(dependencyKey())
                }
            dependencies.withType<ProjectDependency>().configureEach {
                projectDependencies.putIfAbsent(configuration.name, mutableListOf())
                projectDependencies[configuration.name]!!.add(dependencyKey())
            }
        } // configurations

        verifyDependenciesSorted(moduleDependencies)
        verifyDependenciesSorted(projectDependencies)
    } // task action

    private fun Dependency.dependencyKey(): String = "$group.$name"

    private fun isArraySorted(array: List<String>): SortReport {
        val duplicated = mutableListOf<String>()
        val unsorted = mutableListOf<String>()
        for (i in 0 until array.size - 1) {
            if (array[i] == array[i + 1]) { // duplicate entry
                duplicated.add("---> '${array[i]}'  is duplicated")
            }
            if (array[i] > array[i + 1]) { // lexicographical compare
                unsorted.add("--->  '${array[i + 1]}'  is followed by  '${array[i]}'")
            }
        }
        return SortReport(duplicated = duplicated, unsorted = unsorted)
    }

    private fun verifyDependenciesSorted(map: Map<String, List<String>>) {
        var success = true
        map.entries.forEach { (configurationName, dependencies) ->
            val sortingCheck = isArraySorted(dependencies)
            if (sortingCheck.duplicated.isNotEmpty()) {
                logger.error("In project $project\n: Configuration '$configurationName' contains duplicated dependencies: ${sortingCheck.duplicated.joinToString(prefix = "\n\t", separator = "\n\t", postfix = "\n")}")
                success = false
            }
            if (sortingCheck.unsorted.isNotEmpty()) {
                logger.error("In project $project\n: Configuration '$configurationName' contains unsorted dependencies: ${sortingCheck.unsorted.joinToString(prefix = "\n\t", separator = "\n\t", postfix = "\n")}")
                success = false
            }
        }

        if (!success) {
            throw TaskExecutionException(this, RuntimeException("Violations in the order of dependencies"))
        }
    }

    private data class SortReport(
        val duplicated: List<String>,
        val unsorted: List<String>
    )
}
