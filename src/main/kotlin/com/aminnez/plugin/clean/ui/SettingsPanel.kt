// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.

package com.aminnez.plugin.clean.ui

import com.aminnez.plugin.clean.utils.JsonUtils
import com.intellij.openapi.observable.util.whenTextChanged
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextArea
import com.intellij.util.ui.FormBuilder
import org.jetbrains.annotations.NotNull
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JPanel


class SettingsPanel {
    /**
     * The panel containing all the settings UI components.
     */
    val panel: JPanel

    /**
     * The text area containing the structures JSON.
     */
    private val jsonTextArea = JBTextArea()

    init {
        // Set the initial text area settings
        jsonTextArea.columns = 50
        jsonTextArea.rows = 20
        jsonTextArea.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

        // Update the text area's border color based on the validity of the JSON
        jsonTextArea.whenTextChanged {
            if (JsonUtils.isJsonValid(jsonText)) {
                jsonTextArea.border =
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createMatteBorder(2, 2, 2, 2, JBColor.GREEN),
                                BorderFactory.createEmptyBorder(10, 10, 10, 10)
                        )
            } else {
                jsonTextArea.border =
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createMatteBorder(2, 2, 2, 2, JBColor.RED),
                                BorderFactory.createEmptyBorder(10, 10, 10, 10)
                        )
            }
        }

        // Create the settings panel
        val panelBuilder =
                FormBuilder.createFormBuilder()
                        .addLabeledComponent(JBLabel("Structures:"), jsonTextArea, 1, false)

        // Add some vertical space to the panel
        panelBuilder.addComponentFillVertically(JPanel(), 0)

        // Create the panel
        panel = panelBuilder.panel
    }

    /**
     * The preferred component to focus when the settings panel is shown.
     */
    @get:NotNull
    val preferredFocusedComponent: JComponent
        get() = jsonTextArea

    /**
     * The JSON text in the text area.
     */
    @get:NotNull
    var jsonText: String
        get() = jsonTextArea.text
        set(newText) {
            jsonTextArea.text = newText
        }
}