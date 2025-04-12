// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.

package com.aminnez.plugin.clean.action

import com.aminnez.plugin.clean.generator.ProjectStructureGenerator
import com.aminnez.plugin.clean.settings.SettingsState
import com.aminnez.plugin.clean.ui.FeatureDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * Action for a specific folder structure configuration.
 * When triggered, it shows a dialog to get the feature name and then generates the structure.
 */
class ConfigurationAction(private val configurationName: String) : AnAction(configurationName) {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val settings = SettingsState.instance
        val configJson = settings.state.configurations[configurationName] ?: return

        // Show dialog to get feature name
        val dialog = FeatureDialog(project, configurationName)

        if (dialog.showAndGet()) {
            // Generate the structure
            ProjectStructureGenerator.generate(
                e.dataContext,
                rootName = dialog.featureName,
                childrenJsonString = configJson
            )
        }
    }
}
