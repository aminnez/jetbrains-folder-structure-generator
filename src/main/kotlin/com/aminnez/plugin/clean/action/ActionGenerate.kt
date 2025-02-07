// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.

package com.aminnez.plugin.clean.action

import com.aminnez.plugin.clean.settings.SettingsState
import com.aminnez.plugin.clean.generator.ProjectStructureGenerator
import com.intellij.openapi.actionSystem.*
import com.aminnez.plugin.clean.ui.FeatureDialog

/**
 * Generate project structure based on user's input in the feature dialog and the structure json
 * string from the settings.
 */
class ActionGenerate : AnAction() {

    override fun actionPerformed(actionEvent: AnActionEvent) {
        val settings = SettingsState.instance
        val dialog = FeatureDialog(actionEvent.project)

        if (dialog.showAndGet()) {
            ProjectStructureGenerator.generate(
                actionEvent.dataContext,
                rootName = dialog.featureName,
                childrenJsonString = settings.state.jsonString,
            )
        }
    }

}