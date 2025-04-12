// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.

package com.aminnez.plugin.clean.action

import com.aminnez.plugin.clean.settings.SettingsState
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * Action group that provides a submenu of available folder structure configurations.
 */
class FolderStructureActionGroup : ActionGroup() {

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        // If event is null, return an empty array
        if (e == null) return emptyArray()

        val settings = SettingsState.instance
        val configurations = settings.state.configurations

        // Create an action for each configuration
        return configurations.keys.map { configName ->
            ConfigurationAction(configName)
        }.toTypedArray()
    }


    /**
     * Always update the group when shown
     */
    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = true
    }
}
