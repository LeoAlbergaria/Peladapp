package com.example.peladapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class MainViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    val loginResult: MutableLiveData<Boolean> = MutableLiveData()

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    loginResult.postValue(true)
                } else {
                    loginResult.postValue(false)
                }
            }
    }
}
