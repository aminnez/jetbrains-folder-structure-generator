// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.

package com.aminnez.plugin.clean.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

/**
 * Creates a [DialogWrapper] to get the feature name
 */
class FeatureDialog(project: Project?) :
    DialogWrapper(project) {

    /**
     * @return feature name
     */
    val featureName: String?
        get() = nameTextField.text

    override fun createCenterPanel(): JComponent {
        return contentPanel
    }

    /**
     * Sets focus on the text field
     */
    override fun getPreferredFocusedComponent(): JComponent {
        return nameTextField
    }

    init {
        init()
        title = "Structured Folder Generator"
    }

    private lateinit var contentPanel: JPanel
    private lateinit var nameTextField: JTextField

}