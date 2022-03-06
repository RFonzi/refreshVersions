package de.fayard.refreshVersions.core.internal

import de.fayard.refreshVersions.core.internal.TomlLine.Kind.*
import java.io.File
import de.fayard.refreshVersions.core.Version as MavenVersion

internal class TomlUpdater(val fileContent: String, val dependenciesUpdates: List<DependencyWithVersionCandidates>) {
    private val toml = VersionCatalogs.parseToml(fileContent)

    fun updateNewVersions(actual: File) {
        if (fileContent.isBlank()) return

        toml.sections.forEach { (section, lines) ->
            toml[section] = updateNewVersions(lines)
        }
        actual.writeText(toml.toString())
    }

    fun cleanupComments(actual: File) {
        if (fileContent.isBlank()) return

        toml.sections.forEach { (section, lines) ->
            toml[section] = lines.filter { it.kind != Delete }
        }
        actual.writeText(toml.toString())
    }

    private fun updateNewVersions(lines: List<TomlLine>): List<TomlLine> {
        return lines.flatMap { line ->
            val noop = listOf(line)
            when (line.kind) {
                Ignore, LibsUnderscore, LibsVersionRef, PluginVersionRef -> noop
                Delete -> emptyList()
                Version -> {
                    linesForUpdate(line, findLineReferencing(line))
                }
                Libs, Plugin -> {
                    val update = dependenciesUpdates.firstOrNull { dc ->
                        dc.moduleId.name == line.name && dc.moduleId.group == line.group
                    }
                    linesForUpdate(line, update)
                }
            }
        }
    }

    private fun findLineReferencing(version: TomlLine): DependencyWithVersionCandidates? {
        val libOrPlugin = toml.sections.values.flatten().firstOrNull { line ->
            line.versionRef == version.key
        } ?: return null

        return dependenciesUpdates.firstOrNull { (moduleId) ->
            (moduleId.name == libOrPlugin.name) && (moduleId.group == libOrPlugin.group)
        }
    }

    private fun linesForUpdate(line: TomlLine, update: DependencyWithVersionCandidates?): List<TomlLine> {
        val result = mutableListOf(line)
        val versions = update?.versionsCandidates ?: return result
        val version = line.version

        val isObject = line.unparsedValue.endsWith("}")

        fun suffix(v: MavenVersion) = when {
            isObject -> """ = "${v.value}" }"""
            line.section == TomlSection.Versions -> """ = "${v.value}""""
            else -> """:${v.value}""""
        }

        val nbSpaces = line.text.indexOf(version ?: "oops") -
                if (isObject) 17 else 14
        val space = List(Math.max(0, nbSpaces)) { " " }.joinToString("")

        versions.mapTo(result) { v: MavenVersion ->
            TomlLine(line.section, "##${space}# available${suffix(v)}")
        }
        return result
    }
}


internal fun TomlUpdater(file: File, dependenciesUpdates: List<DependencyWithVersionCandidates>): TomlUpdater {
    val text: String = if (file.canRead()) file.readText() else ""
    return TomlUpdater(text, dependenciesUpdates)
}
