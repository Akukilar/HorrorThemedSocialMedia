package com.example.horrorthemedsocialmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel : ViewModel() {

    // Guardar instancia de auth y la BBDD, tambien guardamos la referencia de la BBDD
    val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("users")

    //
    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // Al incio Almacenamos el usuario actual en la realtime database nada
    init {
        _firebaseUser.value = auth.currentUser
    }

    // Funcion para iniciar sesion
    fun login(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)
                }else{
                    _error.postValue("Algo ha ido mal...")
                }
            }
    }

    // Funcion para registrarse e iniciar sesion
    fun register(email:String, password:String, name:String, bio:String, user:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)
                }else{
                    _error.postValue("Algo ha ido mal...")
                }
            }
    }
}