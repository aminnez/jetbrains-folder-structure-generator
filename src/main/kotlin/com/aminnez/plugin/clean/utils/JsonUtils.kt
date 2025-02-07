// Copyright (c) Amin-Nezampour (aminnez.com) 2-2025.

package com.aminnez.plugin.clean.utils


import com.aminnez.plugin.clean.generator.Node
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

/**
 * Utility class for parsing and validating JSON structures.
 */
object JsonUtils {

    /**
     * Parses the given JSON string into a map of node names to their corresponding nodes.
     * Throws [EmptySettingsException] if the JSON string is null or empty.
     */
    @Throws(EmptySettingsException::class)
    fun parseStructureJson(
        jsonString: String?
    ): Map<String, Node> {
        if (jsonString.isNullOrBlank()) {
            throw EmptySettingsException()
        }
        val objectMapper = ObjectMapper().registerKotlinModule()
        return objectMapper.readValue(jsonString)
    }

    /**
     * Validates the given JSON string. Returns true if the JSON is valid, false otherwise.
     */
    fun isJsonValid(jsonString: String?): Boolean {
        return try {
            // Attempt to parse the JSON
            parseStructureJson(jsonString)
            true
        } catch (e: Exception) {
            false
        }
    }
}