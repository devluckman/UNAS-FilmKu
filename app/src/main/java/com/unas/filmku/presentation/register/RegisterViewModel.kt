package com.unas.filmku.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.unas.filmku.domain.model.UserData
import com.unas.filmku.domain.request.RequestRegister

class RegisterViewModel : ViewModel() {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    fun doRegister(
        requestRegister: RequestRegister
    ) {
        var isValid = true

        val firstName = requestRegister.firstName
        val lastName = requestRegister.lastName
        val email: String = requestRegister.email
        val password: String = requestRegister.password
        val confirmPass: String = requestRegister.confirmPass

        val fullName: String = String.format(
            "%s %s",
            firstName,
            lastName
        )

        if (firstName.isEmpty()) {
            isValid = false
            // TODO Action First name blank 
        }

        if (lastName.isEmpty()) {
            isValid = false
            // TODO Action Last name blank 
        }

        if (email.isEmpty()) {
            isValid = false
            // TODO Action Email  blank 
        }

        if (password.isEmpty()) {
            isValid = false
            // TODO Action Password blank 
        }

        if (confirmPass.isEmpty()) {
            isValid = false
            // TODO Action Confirm blank 
        }

        if (confirmPass != password) {
            isValid = false
            // TODO Action Password not match 
        }

        if (isValid) {
            registerToFirebase(email, password, fullName)
        }
    }


    private val _successRegister = MutableLiveData<Boolean?>(null)
    val successRegister : LiveData<Boolean?> = _successRegister


    private fun registerToFirebase(email: String, password: String, fullName: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { auth ->
                // Action after register success
                // Save data user to Firestore

                val uid = auth.user?.uid ?: "uid"
                val data = UserData(
                    email = email,
                    name = fullName
                )
                // Implementation Write Data to FireStore
                Firebase.firestore.collection("user")
                    .document(uid)
                    .set(data)
                    .addOnSuccessListener {

                        // Action Register and Save Data Success
                        _successRegister.value = true
                    }


            }.addOnFailureListener { data ->
                data.printStackTrace()
                _successRegister.value = false
            }
    }
}