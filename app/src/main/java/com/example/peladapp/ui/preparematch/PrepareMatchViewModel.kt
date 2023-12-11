package com.example.peladapp.ui.preparematch

// PrepareMatchViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peladapp.model.Match
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class PrepareMatchViewModel : ViewModel() {

    private val _matchCreated = MutableLiveData<Boolean>()
    val matchCreated: LiveData<Boolean>
        get() = _matchCreated

    private val db = Firebase.firestore

    fun createMatch(numberOfPlayers: Int, playersPerTeam: Int) {
        val code = generateUniqueCode()
        val match = Match(code, numberOfPlayers, playersPerTeam, false)

        db.collection("matches").document(code)
            .set(match)
            .addOnSuccessListener {
                _matchCreated.value = true
            }
            .addOnFailureListener {
                _matchCreated.value = false
            }
    }

    private fun generateUniqueCode(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val random = Random()
        val codeLength = 6

        var code: String
        do {
            code = (1..codeLength)
                .map { chars[random.nextInt(chars.length)] }
                .joinToString("")
        } while (codeExists(code))

        return code
    }

    private fun codeExists(code: String): Boolean {
        return db.collection("matches").document(code).get().isComplete
    }
}
