package com.example.peladapp.ui.match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peladapp.model.Player

class MatchViewModel : ViewModel() {
    private val _playersList = MutableLiveData<List<Player>>()
    val playersList: LiveData<List<Player>>
        get() = _playersList

    fun setPlayersList(playersList: ArrayList<Player>) {
        _playersList.postValue(playersList)
    }
}