// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.

package com.aminnez.plugin.clean.ui

import com.aminnez.plugin.clean.settings.SettingsState
import com.aminnez.plugin.clean.utils.JsonUtils
import com.intellij.openapi.observable.util.whenTextChanged
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextArea
import com.intellij.util.ui.FormBuilder
import org.jetbrains.annotations.NotNull
import java.awt.FlowLayout
import javax.swing.*


class SettingsPanel {
    /**
     * The panel containing all the settings UI components.
     */
    lateinit var panel: JPanel

    /**
     * The text area containing the structures JSON.
     */
    private val jsonTextArea = JBTextArea()

    /**
     * Dropdown for selecting configuration
     */
    private val configComboBox = ComboBox<String>()

    /**
     * Current configurations
     */
    private val configurations: MutableMap<String, String> = mutableMapOf()

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
                // Update the current configuration in the map
                val selectedConfig = configComboBox.item
                if (selectedConfig != null) {
                    configurations[selectedConfig] = jsonText
                }
            } else {
                jsonTextArea.border =
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createMatteBorder(2, 2, 2, 2, JBColor.RED),
                                BorderFactory.createEmptyBorder(10, 10, 10, 10)
                        )
            }
        }

        // Load configurations from settings
        val settings = SettingsState.instance.state
        configurations.putAll(settings.configurations)

        // Setup configuration dropdown
        configComboBox.model = DefaultComboBoxModel(configurations.keys.toTypedArray())
        configComboBox.selectedItem = settings.selectedConfiguration
        configComboBox.addActionListener {
            val selectedConfig = configComboBox.selectedItem as String
            jsonTextArea.text = configurations[selectedConfig] ?: ""
        }

        // Create buttons for configuration management
        val addButton = JButton("Add")
        addButton.addActionListener {
            val configName = JTextField()
            val message = arrayOf<Any>("Configuration Name:", configName)
            val option = JOptionPane.showConfirmDialog(
                panel,
                message,
                "Add New Configuration",
                JOptionPane.OK_CANCEL_OPTION
            )
            if (option == JOptionPane.OK_OPTION) {
                val newName = configName.text
                if (newName.isNotBlank() && !configurations.containsKey(newName)) {
                    configurations[newName] = "{}"
                    updateConfigComboBox(newName)
                }
            }
        }

        val deleteButton = JButton("Delete")
        deleteButton.addActionListener {
            val selectedConfig = configComboBox.selectedItem as String
            if (configurations.size > 1) {
                val option = JOptionPane.showConfirmDialog(
                    panel,
                    "Are you sure you want to delete the configuration '$selectedConfig'?",
                    "Delete Configuration",
                    JOptionPane.YES_NO_OPTION
                )
                if (option == JOptionPane.YES_OPTION) {
                    configurations.remove(selectedConfig)
                    updateConfigComboBox(configurations.keys.first())
                }
            } else {
                JOptionPane.showMessageDialog(
                    panel,
                    "Cannot delete the last configuration.",
                    "Delete Configuration",
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }

        // Create a panel for the dropdown and buttons
        val configPanel = JPanel(FlowLayout(FlowLayout.LEFT))
        configPanel.add(JBLabel("Configuration:"))
        configPanel.add(configComboBox)
        configPanel.add(addButton)
        configPanel.add(deleteButton)

        // Create the settings panel
        val panelBuilder =
                FormBuilder.createFormBuilder()
                        .addComponent(configPanel)
                        .addLabeledComponent(JBLabel("Structure:"), jsonTextArea, 1, false)

        // Add some vertical space to the panel
        panelBuilder.addComponentFillVertically(JPanel(), 0)

        // Create the panel
        panel = panelBuilder.panel
    }

    /**
     * Update the combo box with the current configurations
     */
    private fun updateConfigComboBox(selectedItem: String) {
        configComboBox.model = DefaultComboBoxModel(configurations.keys.toTypedArray())
        configComboBox.selectedItem = selectedItem
        jsonTextArea.text = configurations[selectedItem] ?: ""
    }

    /**
     * The preferred component to focus when the settings panel is shown.
     */
    @get:NotNull
    val preferredFocusedComponent: JComponent
        get() = configComboBox

    /**
     * The JSON text in the text area.
     */
    @get:NotNull
    var jsonText: String
        get() = jsonTextArea.text
        set(newText) {
            jsonTextArea.text = newText
        }

    /**
     * Get all configurations
     */
    val allConfigurations: Map<String, String>
        get() = configurations

    /**
     * Get the selected configuration name
     */
    val selectedConfiguration: String
        get() = configComboBox.selectedItem as String
}