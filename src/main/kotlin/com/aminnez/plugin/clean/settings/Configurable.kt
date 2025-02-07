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
    override fun createComponent(): JComponent {
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
        return settingsPanel.jsonText != state.jsonString
    }

    /**
     * Applies the changes of the configurable.
     */
    override fun apply() {
        if (JsonUtils.isJsonValid(settingsPanel.jsonText)) {
            val settings = SettingsState.instance
            settings.state.jsonString = settingsPanel.jsonText
        }
    }

    /**
     * Resets the configurable to its default state.
     */
    override fun reset() {
        val state: SettingsState.State =
            Objects.requireNonNull(SettingsState.instance.state)
        settingsPanel.jsonText = state.jsonString
    }
}