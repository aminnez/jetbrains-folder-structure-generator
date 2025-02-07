// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.

package com.aminnez.plugin.clean.generator

import com.aminnez.plugin.clean.ui.Notifier
import com.aminnez.plugin.clean.ui.OverwriteDialog
import com.aminnez.plugin.clean.utils.JsonUtils
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

/**
 * Generates project structure based on the given root name and structure json string.
 *
 * This class handles:
 * 1. Getting the project and the selected VirtualFile from the data context
 * 2. Determining the base folder
 * 3. Handling potential folder overwrite
 * 4. Generating the structure
 */
class ProjectStructureGenerator {
    companion object {

        /**
         * Primary method to generate project structure
         */
        fun generate(
            dataContext: DataContext,
            rootName: String?,
            childrenJsonString: String?
        ) {
            val project = getProjectFromDataContext(dataContext) ?: return
            val selectedFile = getSelectedFileFromDataContext(dataContext) ?: return

            // Determine the base folder
            val folder = if (selectedFile.isDirectory) selectedFile else selectedFile.parent

            // Handle potential folder overwrite
            val (updatedFolder, shouldProceed) = handleFolderCreation(project, folder, rootName)
            if (!shouldProceed) return

            // Generate structure
            WriteCommandAction.runWriteCommandAction(project) {
                try {
                    updatedFolder?.refresh(false, true)

                    val structureMap = JsonUtils.parseStructureJson(childrenJsonString)
                    if (structureMap.isNotEmpty()) {
                        val generator = DefaultStructureGenerator()
                        generator.generate(
                            project,
                            updatedFolder ?: folder,
                            structureMap,
                            updatedFolder != folder
                        )
                    }
                } catch (e: Exception) {
                    Notifier.error(project, "Failed to generate structure: ${e.message}")
                }
            }
        }

        /**
         * Gets the project from the data context
         */
        private fun getProjectFromDataContext(dataContext: DataContext): Project? =
            CommonDataKeys.PROJECT.getData(dataContext)

        /**
         * Gets the selected file from the data context
         */
        private fun getSelectedFileFromDataContext(dataContext: DataContext): VirtualFile? =
            PlatformDataKeys.VIRTUAL_FILE.getData(dataContext)

        /**
         * Handles folder creation or selection with potential overwrite
         * @return Pair of (selected/created folder, whether to proceed)
         */
        private fun handleFolderCreation(
            project: Project,
            initialFolder: VirtualFile,
            rootName: String?
        ): Pair<VirtualFile?, Boolean> {
            // If the root name is empty, return the initial folder
            if (rootName.isNullOrBlank()) return Pair(initialFolder, true)

            // Try to find an existing folder with the given name
            var existingChild = initialFolder.findChild(rootName)
            if (existingChild != null) {
                // If it exists, prompt the user to overwrite
                val overwriteDialog = OverwriteDialog(project, rootName)
                if (!overwriteDialog.showAndGet()) {
                    // If the user doesn't want to overwrite, return null and false
                    return Pair(null, false)
                }
            } else {
                // If it doesn't exist, create it
                WriteCommandAction.runWriteCommandAction(project) {
                    existingChild = initialFolder.createChildDirectory(initialFolder, rootName)
                }
            }
            // Return the existing or created folder and whether to proceed
            return Pair(
                existingChild,
                true
            )
        }

    }
}