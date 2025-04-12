// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.2

package com.aminnez.plugin.clean.settings

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
         * Map of configuration name to JSON string representing the project structure
         */
        @NonNls
        var configurations: MutableMap<String, String> = mutableMapOf(
            "Clean Architecture" to "{\n" +
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
                "}",
            "MVC" to "{\n" +
                "  \"model\": {\n" +
                "    \"type\": \"d\"\n" +
                "  },\n" +
                "  \"view\": {\n" +
                "    \"type\": \"d\"\n" +
                "  },\n" +
                "  \"controller\": {\n" +
                "    \"type\": \"d\"\n" +
                "  }\n" +
                "}"
        )

        /**
         * Currently selected configuration name
         */
        @NonNls
        var selectedConfiguration: String = "Clean Architecture"

        /**
         * For backward compatibility with older versions
         */
        @get:NonNls
        var jsonString: String
            get() = configurations[selectedConfiguration] ?: ""
            set(value) {
                configurations[selectedConfiguration] = value
            }
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
