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

    private val _matchCreated = MutableLiveData<Match?>()
    val matchCreated: LiveData<Match?>
        get() = _matchCreated


    private val db = Firebase.firestore

    fun createMatch(numberOfPlayers: Int, playersPerTeam: Int) {
        val code = generateUniqueCode()
        val match = Match(code, numberOfPlayers, playersPerTeam, false)

        db.collection("matches").document(code)
            .set(match)
            .addOnSuccessListener {
                _matchCreated.value = match
            }
            .addOnFailureListener {
                _matchCreated.value = null
            }
    }

    fun enterExistingMatch(code: String) {
        db.collection("matches").document(code).get()
            .addOnSuccessListener { documentSnapshot ->
                val match = documentSnapshot.toObject(Match::class.java)
                if (match != null && !match.hasStarted) {
                    _matchCreated.value = match
                } else {
                    _matchCreated.value = null
                }
            }
            .addOnFailureListener {
                _matchCreated.value = null
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
