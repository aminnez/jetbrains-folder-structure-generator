// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.

package com.aminnez.plugin.clean.ui

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project

/**
 * Notification factory for the Clean Architecture generator.
 *
 * @author Amin Nezampour
 * @since 2
 */
interface Notifier {
    companion object {
        /**
         * Creates a warning notification in the specified [project] context with the given [content].
         *
         * @param project the project context to show the notification in
         * @param content the content of the notification
         */
        fun warning(project: Project?, content: String) = notify(
            NotificationType.WARNING,
            project,
            content
        )

        /**
         * Creates an error notification in the specified [project] context with the given [content].
         *
         * @param project the project context to show the notification in
         * @param content the content of the notification
         */
        fun error(project: Project?, content: String) = notify(
            NotificationType.ERROR,
            project,
            content
        )

        /**
         * Creates a notification in the specified [project] context with the given [type] and [content].
         *
         * @param type the type of the notification
         * @param project the project context to show the notification in
         * @param content the content of the notification
         */
        private fun notify(
            type: NotificationType,
            project: Project?,
            content: String
        ) = Notifications.Bus.notify(
            Notification(
                "CleanArchitecture",
                "CLEAN ARCHITECTURE GENERATOR $type",
                content,
                type
            ), project
        )
    }
}