// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.

package com.aminnez.plugin.clean.utils

/**
 * Thrown when the settings are empty. This exception is used to indicate that the settings are empty
 * and should be handled by the caller.
 */
class EmptySettingsException : Exception("The settings are empty. Please fill in the settings and try again.")