package com.example.peladapp.ui.match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peladapp.model.Player

/**
 * ViewModel for managing match-related data and logic.
 *
 * This ViewModel handles the management of player lists, teams, and goal counts for a match.
 */
class MatchViewModel : ViewModel() {

    private val _playersList = MutableLiveData<List<Player>>()
    val playersList: LiveData<List<Player>>
        get() = _playersList

    private val _team1Players = MutableLiveData<List<Player>>()
    val team1Players: LiveData<List<Player>>
        get() = _team1Players

    private val _team2Players = MutableLiveData<List<Player>>()
    val team2Players: LiveData<List<Player>>
        get() = _team2Players

    private val _team1Goals = MutableLiveData<Int>()
    val team1Goals: LiveData<Int>
        get() = _team1Goals

    private val _team2Goals = MutableLiveData<Int>()
    val team2Goals: LiveData<Int>
        get() = _team2Goals

    /**
     * Initializes goal counts to zero.
     */
    init {
        _team1Goals.value = 0
        _team2Goals.value = 0
    }

    /**
     * Sets the initial list of players for the match.
     *
     * @param playersList The list of players for the match.
     */
    fun setPlayersList(playersList: ArrayList<Player>) {
        _playersList.postValue(playersList)
    }

    /**
     * Adds a player to Team 1 and updates the player lists accordingly.
     *
     * @param player The player to be added to Team 1.
     */
    fun addPlayerToTeam1(player: Player) {
        val currentList = _team1Players.value.orEmpty().toMutableList()
        val allPlayersList = _playersList.value.orEmpty().toMutableList()

        allPlayersList.remove(player)
        currentList.add(player)

        _team1Players.postValue(currentList)
        _playersList.postValue(allPlayersList)
    }

    /**
     * Adds a player to Team 2 and updates the player lists accordingly.
     *
     * @param player The player to be added to Team 2.
     */
    fun addPlayerToTeam2(player: Player) {
        val currentList = _team2Players.value.orEmpty().toMutableList()
        val allPlayersList = _playersList.value.orEmpty().toMutableList()

        allPlayersList.remove(player)
        currentList.add(player)

        _team2Players.postValue(currentList)
        _playersList.postValue(allPlayersList)
    }

    /**
     * Increments the goal count for Team 1.
     */
    fun addGoalToTeam1() {
        val currentGoals = _team1Goals.value ?: 0
        _team1Goals.postValue(currentGoals + 1)
    }

    /**
     * Increments the goal count for Team 2.
     */
    fun addGoalToTeam2() {
        val currentGoals = _team2Goals.value ?: 0
        _team2Goals.postValue(currentGoals + 1)
    }

    /**
     * Resets the match by moving all players back to the general players list
     * and setting goal counts to zero for both teams.
     */
    fun resetMatch() {
        val team1Players = _team1Players.value.orEmpty()
        val team2Players = _team2Players.value.orEmpty()

        val allPlayers = _playersList.value.orEmpty() + team1Players + team2Players

        _playersList.value = allPlayers
        _team1Players.value = emptyList()
        _team2Players.value = emptyList()

        _team1Goals.value = 0
        _team2Goals.value = 0
    }
}
