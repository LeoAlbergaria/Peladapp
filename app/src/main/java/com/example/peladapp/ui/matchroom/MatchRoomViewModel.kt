package com.example.peladapp.ui.matchroom

import androidx.lifecycle.ViewModel

class MatchRoomViewModel : ViewModel() {

    val confirmedList: List<String> = listOf(
        "Leo",
        "Vic",
        "Leo",
        "Vic",
        "Leo",
        "Vic",
        "Leo",
        "Vic",
        "Leo",
        "Vic",
        "Leo",
        "Vic",
    )

    val waitingList: List<String> = listOf(
        "Leo",
        "Vic",
        "Leo",
        "Vic",
        "Leo",
        "Vic",
        "Leo",
        "Vic",
        "Leo",
        "Vic",
        "Leo",
        "Vic",
    )
    val outList: List<String> = listOf(
        "Leo",
        "Vic",
    )
}