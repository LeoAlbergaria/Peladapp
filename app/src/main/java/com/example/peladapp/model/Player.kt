package com.example.peladapp.model

import java.io.Serializable

/**
 * Data class representing a player.
 *
 * @property name The name of the player (nullable).
 * @property email The email address of the player.
 * @property statusCode The status code representing the player's status in a match.
 *                       - 1: Confirmed
 *                       - 2: Waiting
 *                       - 3: Out
 */
data class Player(
    val name: String? = null,
    val email: String = "",
    var statusCode: Int = 3,
) : Serializable

