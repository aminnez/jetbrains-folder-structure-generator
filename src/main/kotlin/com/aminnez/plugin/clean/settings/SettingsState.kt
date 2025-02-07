// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.2

package com.aminnez.plugin.clean.settings

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.NotNull

/**
 * Holds the plugin settings
 */
@Service
@State(name = "com.aminnez.plugin.clean.AppSettings", storages = [Storage("CleanArchitecturePluginSettings.xml")])
internal class SettingsState : PersistentStateComponent<SettingsState.State> {

    /**
     * Data class to hold the settings
     */
    internal class State {
        /**
         * The JSON string representing the project structure
         */
        @NonNls
        var jsonString: String = "{\n" +
                "  \"domain\": {\n" +
                "    \"type\": \"d\",\n" +
                "    \"children\": {\n" +
                "      \"repository\": {\n" +
                "        \"type\": \"d\"\n" +
                "      },\n" +
                "      \"entities\": {\n" +
                "        \"type\": \"d\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"data\": {\n" +
                "    \"type\": \"d\",\n" +
                "    \"children\": {\n" +
                "      \"datasources\": {\n" +
                "        \"type\": \"d\"\n" +
                "      },\n" +
                "      \"dtoes\": {\n" +
                "        \"type\": \"d\"\n" +
                "      },\n" +
                "      \"repositories\": {\n" +
                "        \"type\": \"d\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"presentation\": {\n" +
                "    \"type\": \"d\",\n" +
                "    \"children\": {\n" +
                "      \"pages\": {\n" +
                "        \"type\": \"d\"\n" +
                "      },\n" +
                "      \"components\": {\n" +
                "        \"type\": \"d\"\n" +
                "      },\n" +
                "      \"providers\": {\n" +
                "        \"type\": \"d\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}"
    }

    private var settingsState = State()

    /**
     * Returns the current settings
     */
    override fun getState(): State {
        return settingsState
    }

    /**
     * Loads the settings from the state
     */
    override fun loadState(@NotNull state: State) {
        settingsState = state
    }

    /**
     * Returns the instance of the settings
     */
    companion object {
        val instance: SettingsState
            get() = ApplicationManager.getApplication()
                .getService(SettingsState::class.java)
    }
}
