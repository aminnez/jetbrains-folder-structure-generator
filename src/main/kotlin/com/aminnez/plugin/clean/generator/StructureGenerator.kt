// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.

package com.aminnez.plugin.clean.generator

import com.aminnez.plugin.clean.ui.Notifier
import com.fasterxml.jackson.annotation.JsonProperty
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

/**
 * Represents a node in the project structure
 * @property type type of the node (d for directory, f for file)
 * @property ext extension of the file (null if directory)
 * @property children map of children nodes (null if file)
 */
data class Node(
    val type: String,
    val ext: String? = null,
    @JsonProperty("children") val children: Map<String, Node>? = null
)

/**
 * Interface for handling project structure generation
 */
interface StructureGenerator {
    /**
     * Generates the project structure based on the given structure map
     * @param project the project to generate the structure for
     * @param folder the starting folder to generate the structure from
     * @param structureMap the map of node names to their corresponding nodes
     * @param overwrite whether to overwrite existing files or not
     */
    fun generate(
        project: Project,
        folder: VirtualFile,
        structureMap: Map<String, Node>,
        overwrite: Boolean
    )
}

/**
 * Default implementation of structure generation
 */
class DefaultStructureGenerator : StructureGenerator {
    override fun generate(
        project: Project,
        folder: VirtualFile,
        structureMap: Map<String, Node>,
        overwrite: Boolean
    ) {
        structureMap.forEach { (name, node) ->
            try {
                when (node.type) {
                    "d" -> createDirectory(project, folder, name, node, overwrite)
                    "f" -> createFile(folder, name, node.ext ?: "txt")
                    else -> throw IllegalArgumentException("Invalid node type: ${node.type}")
                }
            } catch (e: Exception) {
                if (!overwrite) {
                    Notifier.warning(project, "Failed to create item [${folder.name}]: ${e.message}")
                }
            }
        }
    }

    /**
     * Creates a directory with the given name and generates its structure recursively
     * @param project the project to generate the structure for
     * @param parentFolder the parent folder to create the directory in
     * @param name the name of the directory
     * @param node the node representing the directory
     * @param overwrite whether to overwrite existing files or not
     */
    private fun createDirectory(
        project: Project,
        parentFolder: VirtualFile,
        name: String,
        node: Node,
        overwrite: Boolean
    ) {
        val existingFolder = parentFolder.findChild(name)

        if (existingFolder != null && node.children != null) {
            generate(project, existingFolder, node.children, overwrite)
        } else {
            val newFolder = parentFolder.createChildDirectory(parentFolder, name)
            node.children?.let {
                generate(project, newFolder, it, overwrite)
            }
        }
    }

    /**
     * Creates a file with the given name and extension
     * @param parentFolder the parent folder to create the file in
     * @param name the name of the file
     * @param extension the extension of the file
     */
    private fun createFile(parentFolder: VirtualFile, name: String, extension: String) {
        parentFolder.createChildData(parentFolder, "$name.$extension")
    }
}