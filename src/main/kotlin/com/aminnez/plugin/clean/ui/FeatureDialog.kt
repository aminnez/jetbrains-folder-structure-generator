// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.

package com.aminnez.plugin.clean.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBLabel
import java.awt.GridLayout
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

/**
 * Creates a [DialogWrapper] to get the feature name for a specific configuration
 */
class FeatureDialog(
    project: Project?,
    private val configurationName: String
) : DialogWrapper(project) {

    // Initialize components first
    private val nameTextField = JTextField()

    init {
        title = "Create $configurationName Structure"
        init() // Initialize the dialog after component setup
    }

    /**
     * @return feature name
     */
    val featureName: String
        get() = nameTextField.text

    override fun createCenterPanel(): JComponent {
        // Create a new panel to hold our components
        val dialogPanel = JPanel(GridLayout(2, 1, 5, 5))
        
        // Add feature name label and field
        dialogPanel.add(JBLabel("Feature name:"))
        dialogPanel.add(nameTextField)
        
        return dialogPanel
    }

    /**
     * Sets focus on the text field
     */
    override fun getPreferredFocusedComponent(): JComponent {
        return nameTextField
    }
}