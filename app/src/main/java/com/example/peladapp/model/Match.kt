package com.example.peladapp.model

data class Match(
    val code: String = "",
    val numberOfPlayers: Int = 0,
    val playersPerTeam: Int = 0,
    val hasStarted: Boolean = false
)