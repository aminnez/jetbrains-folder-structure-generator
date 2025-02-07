// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.

package com.aminnez.plugin.clean.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JLabel

/**
 * Dialog to ask the user whether to overwrite an existing directory.
 */
class OverwriteDialog(
    project: Project?,
    private val folderName: String
) : DialogWrapper(project) {

    init {
        title = "Overwrite Existing Directory?"
        init()
    }

    /**
     * Creates the center panel of the dialog.
     *
     * @return the created panel.
     */
    override fun createCenterPanel(): JComponent {
        val dialogPanel = JPanel()
        val message = """
            This directory ($folderName) already exists. Overwrite?
            
            Just uncreated directories will be created.
            No file will be overwritten.
        """.trimIndent()
        val messageLabel = JLabel(message)
        dialogPanel.add(messageLabel)
        return dialogPanel
    }
}