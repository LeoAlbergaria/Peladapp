package com.example.peladapp.ui.preparematch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peladapp.model.Match
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

/**
 * ViewModel for preparing and creating a match in the PrepareMatchFragment.
 *
 * This ViewModel interacts with Firebase Firestore to create and join matches,
 * as well as generate unique match codes.
 */
class PrepareMatchViewModel : ViewModel() {

    private val _matchCreated = MutableLiveData<Match?>()
    val matchCreated: LiveData<Match?>
        get() = _matchCreated

    private val db = Firebase.firestore

    /**
     * Creates a new match with the specified parameters.
     *
     * @param numberOfPlayers The total number of players in the match.
     * @param playersPerTeam The number of players per team in the match.
     */
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

    /**
     * Attempts to join an existing match with the specified code.
     *
     * @param code The code of the existing match.
     */
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

    /**
     * Generates a unique match code that does not exist in the Firestore database.
     *
     * @return The generated unique match code.
     */
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

    /**
     * Checks if a match with the specified code already exists in the Firestore database.
     *
     * @param code The match code to check.
     * @return `true` if the match code exists, `false` otherwise.
     */
    private fun codeExists(code: String): Boolean {
        return db.collection("matches").document(code).get().isComplete
    }
}
