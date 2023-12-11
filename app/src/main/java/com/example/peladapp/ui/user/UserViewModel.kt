package com.example.peladapp.ui.user

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class UserViewModel : ViewModel() {
    fun changeUserName(username: String, completion: (Boolean) -> Unit) {
        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = username
        }

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                completion(task.isSuccessful)
            }
    }
}