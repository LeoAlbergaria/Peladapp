package com.example.peladapp.model

import java.io.Serializable

/**
 * Data class representing a match.
 *
 * @property code The unique code associated with the match.
 * @property numberOfPlayers The total number of players in the match.
 * @property playersPerTeam The number of players per team in the match.
 * @property hasStarted Indicates whether the match has started or not.
 */
data class Match(
    val code: String = "",
    val numberOfPlayers: Int = 0,
    val playersPerTeam: Int = 0,
    val hasStarted: Boolean = false
) : Serializable
