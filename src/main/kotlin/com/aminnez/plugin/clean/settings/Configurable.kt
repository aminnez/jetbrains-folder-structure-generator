// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.2

package com.aminnez.plugin.clean.settings

import com.aminnez.plugin.clean.ui.SettingsPanel
import com.aminnez.plugin.clean.utils.JsonUtils
import com.intellij.openapi.options.Configurable
import org.jetbrains.annotations.Nullable
import java.util.*
import javax.swing.JComponent

/**
 * Clean Architecture settings configurable.
 *
 * @author Amin Nezampour
 * @since 2.0.2
 */
internal class SettingsConfigurable : Configurable {

    private lateinit var settingsPanel: SettingsPanel

    /**
     * Returns the display name of the configurable.
     *
     * @return the display name.
     */
    override fun getDisplayName(): String = "Folder Structure Generator"

    /**
     * Returns the preferred focused component.
     *
     * @return the preferred focused component.
     */
    override fun getPreferredFocusedComponent(): JComponent {
        return settingsPanel.preferredFocusedComponent
    }

    /**
     * Creates the component of the configurable.
     *
     * @return the created component.
     */
    @Nullable
    override fun createComponent(): JComponent? {
        settingsPanel = SettingsPanel()
        return settingsPanel.panel
    }

    /**
     * Checks whether the configurable is modified.
     *
     * @return true if the configurable is modified, false otherwise.
     */
    override fun isModified(): Boolean {
        val state: SettingsState.State =
            Objects.requireNonNull(SettingsState.instance.state)
        
        // Check if the selected configuration has changed
        if (settingsPanel.selectedConfiguration != state.selectedConfiguration) {
            return true
        }
        
        // Check if the configurations map has changed
        val panelConfigs = settingsPanel.allConfigurations
        if (panelConfigs.size != state.configurations.size) {
            return true
        }
        
        // Check if any configuration content has changed
        for ((key, value) in panelConfigs) {
            if (state.configurations[key] != value) {
                return true
            }
        }
        
        // Check if any configuration has been removed
        for (key in state.configurations.keys) {
            if (!panelConfigs.containsKey(key)) {
                return true
            }
        }
        
        return false
    }

    /**
     * Applies the changes of the configurable.
     */
    override fun apply() {
        val currentJson = settingsPanel.jsonText
        if (JsonUtils.isJsonValid(currentJson)) {
            val settings = SettingsState.instance
            val state = settings.state
            
            // Update the configurations map
            state.configurations.clear()
            state.configurations.putAll(settingsPanel.allConfigurations)
            
            // Update the selected configuration
            state.selectedConfiguration = settingsPanel.selectedConfiguration
        }
    }

    /**
     * Resets the configurable to its default state.
     */
    override fun reset() {
        val state: SettingsState.State =
            Objects.requireNonNull(SettingsState.instance.state)
        
        // Load the selected configuration's JSON
        settingsPanel.jsonText = state.jsonString
    }
}