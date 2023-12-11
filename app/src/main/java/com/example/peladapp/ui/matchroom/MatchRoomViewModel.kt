package com.example.peladapp.ui.matchroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peladapp.model.Match
import com.example.peladapp.model.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MatchRoomViewModel : ViewModel() {

    private val _selectedMatch = MutableLiveData<Match?>()
    val selectedMatch: LiveData<Match?>
        get() = _selectedMatch

    private val _allPlayersList = MutableLiveData<List<Player>>()
    val allPlayersList: LiveData<List<Player>>
        get() = _allPlayersList

    private val _confirmedList = MutableLiveData<List<Player>>()
    val confirmedList: LiveData<List<Player>>
        get() = _confirmedList

    private val _waitingList = MutableLiveData<List<Player>>()
    val waitingList: LiveData<List<Player>>
        get() = _waitingList

    private val _outList = MutableLiveData<List<Player>>()
    val outList: LiveData<List<Player>>
        get() = _outList

    private val db = FirebaseFirestore.getInstance()

    fun fetchData(match: Match?) {
        _selectedMatch.value = match

        if (match != null) {
            fetchPlayersForMatch(match.code)
        }
    }

    fun updatePlayer(status: Int) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val email = it.email ?: ""
            val playerName = it.displayName ?: ""

            val existingPlayer = _allPlayersList.value?.find { player ->
                player.email == email
            }

            if (existingPlayer != null) {
                existingPlayer.statusCode = status
            } else {
                val player = Player(playerName, email, status)
                val currentList = _allPlayersList.value.orEmpty().toMutableList()
                currentList.add(player)
                _allPlayersList.value = currentList
            }
        }
        val playerList = _allPlayersList.value
        playerList?.let {
            updatePlayerLists(it)
        }

        _allPlayersList.value?.forEach { player ->
            sendPlayerToDatabase(player)
        }
    }

    private fun fetchPlayersForMatch(matchCode: String) {
        db.collection("matches").document(matchCode).collection("players")
            .get()
            .addOnSuccessListener { result ->
                val players = result.documents.mapNotNull { document ->
                    document.toObject(Player::class.java)
                }

                updatePlayerLists(players)
            }
            .addOnFailureListener {
                // Handle failure
            }
    }

    private fun sendPlayerToDatabase(player: Player) {
        val matchCode = _selectedMatch.value?.code
        matchCode?.let {
            db.collection("matches").document(matchCode)
                .collection("players").document(player.email)
                .set(player)
                .addOnSuccessListener {
                    // Handle success
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }
    }

    private fun updatePlayerLists(players: List<Player>) {
        _allPlayersList.value = players
        _confirmedList.value = players.filter { it.statusCode == 1 }
        _waitingList.value = players.filter { it.statusCode == 2 }
        _outList.value = players.filter { it.statusCode == 3 }

        val maxConfirmedPlayers = _selectedMatch.value?.numberOfPlayers ?: 0

        val confirmedPlayers = _confirmedList.value.orEmpty()
        val waitingPlayers = _waitingList.value.orEmpty()

        // Ensure that the confirmed list has at least the minimum required players
        if (confirmedPlayers.size < maxConfirmedPlayers) {
            val playersToMoveToConfirmedList = waitingPlayers.take(maxConfirmedPlayers - confirmedPlayers.size)
            _confirmedList.value = confirmedPlayers + playersToMoveToConfirmedList
            _waitingList.value = waitingPlayers - playersToMoveToConfirmedList
        }
    }
}