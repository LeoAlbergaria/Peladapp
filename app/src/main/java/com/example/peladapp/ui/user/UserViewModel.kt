package com.example.peladapp.ui.user

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

/**
 * ViewModel for managing user-related data and operations.
 *
 * This ViewModel provides functionality related to retrieving and updating user information.
 */
class UserViewModel : ViewModel() {

    /**
     * Retrieves the current user's display name.
     *
     * @return The display name of the current user, or null if the user is not authenticated.
     */
    fun getUserName(): String? {
        return Firebase.auth.currentUser?.displayName
    }

    /**
     * Changes the display name of the current user.
     *
     * @param username The new display name for the user.
     * @param completion Callback function indicating the success or failure of the operation.
     *                   The function parameter is true if the operation is successful, false otherwise.
     */
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
