package com.example.horrorthemedsocialmedia.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.horrorthemedsocialmedia.model.UserModel
import com.example.horrorthemedsocialmedia.utils.SharedPref
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage
import java.util.UUID

class AuthViewModel : ViewModel() {

    // Guardar instancia de auth y la BBDD, tambien guardamos la referencia de la BBDD
    val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("users")

    // Guardar imagen y añadir un identificador
    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("users/${UUID.randomUUID()}.jpg")


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
    fun login(email: String, password: String, context: Context){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)

                    getData(auth.currentUser!!.uid, context)

                }else{
                    _error.postValue("Algo ha ido mal...")
                }
            }
    }

    private fun getData(uid: String, context: Context) {

        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(UserModel::class.java)
                SharedPref.storeData(
                    userData!!.name,
                    userData!!.email,
                    userData!!.bio,
                    userData!!.username,
                    userData!!.imageUrl,
                    context
                )
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    // Funcion para registrarse e iniciar sesion
    fun register(
        email:String,
        password:String,
        name:String,
        bio:String,
        username:String,
        imageUri:Uri,
        context: Context
    ){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)
                    saveImage(email, password, name, bio, username, imageUri, auth.currentUser?.uid, context)
                }else{
                    _error.postValue("Algo ha ido mal...")
                }
            }
    }

    private fun saveImage(email: String, password: String, name: String, bio: String, username: String, imageUri: Uri, uid: String?, context: Context){
        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.addOnSuccessListener{
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(email, password, name, bio, username, it.toString(), uid, context)
            }
        }
    }

    private fun saveData(
        email: String,
        password: String,
        name: String,
        bio: String,
        username: String,
        toString: String,
        uid :String?,
        context: Context
    ){
        val userData = UserModel(email, password, name, bio, username, toString)

        userRef.child(uid!!).setValue(userData)
            .addOnSuccessListener {
                SharedPref.storeData(name,email,bio,username,toString, context)
            }.addOnFailureListener {
                _error.postValue("Algo ha ido mal...")
            }
    }

    // FUNCION PARA CERRAR SESIONES
    fun logout(){
        auth.signOut()
        _firebaseUser.postValue(null)
    }
}