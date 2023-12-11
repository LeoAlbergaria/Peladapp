package com.example.peladapp.model

import java.io.Serializable

data class Player(
    val name: String? = null,
    val email: String = "",
    var statusCode: Int = 3,
) : Serializable
