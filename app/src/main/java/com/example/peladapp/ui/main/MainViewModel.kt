package com.example.peladapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * ViewModel for handling authentication and login in the MainActivity.
 *
 * This ViewModel interacts with Firebase Authentication to perform user login.
 * It provides a [loginResult] MutableLiveData to notify the result of the login operation.
 */
class MainViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    /**
     * LiveData representing the result of the login operation.
     * - `true` if the login is successful.
     * - `false` if the login fails.
     */
    val loginResult: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Attempts to log in a user with the provided [email] and [password].
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     */
    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Notify the ViewModel observers that login is successful
                    loginResult.postValue(true)
                } else {
                    // Notify the ViewModel observers that login failed
                    loginResult.postValue(false)
                }
            }
    }
}
