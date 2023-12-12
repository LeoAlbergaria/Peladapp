package com.example.peladapp.ui.signup

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

/**
 * ViewModel for handling user signup in the SignUpActivity.
 *
 * This ViewModel interacts with Firebase Authentication to sign up a new user
 * and update the user profile with a display name (username).
 */
class SignUpViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Signs up a new user with the provided email, password, and username.
     *
     * @param email The email address of the user.
     * @param password The password for the user.
     * @param username The display name (username) for the user.
     * @param callback A callback function invoked upon completion with a success flag and an optional error message.
     */
    fun signUpUser(email: String, password: String, username: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                // Change the username after successful signup
                changeUserName(username)
                callback.invoke(true, null)
            } else {
                callback.invoke(false, it.exception?.message)
            }
        }
    }

    /**
     * Updates the display name (username) for the currently signed-in user.
     *
     * @param username The new display name for the user.
     */
    private fun changeUserName(username: String) {
        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = username
        }

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener {}
    }
}
