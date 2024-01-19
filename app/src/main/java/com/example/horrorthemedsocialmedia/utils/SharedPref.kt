package com.example.horrorthemedsocialmedia.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPref {

    fun storeData(
        name:String,
        email:String,
        bio:String,
        username:String,
        imageUrl:String,
        context: Context,
    ) {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name",name)
        editor.putString("email",email)
        editor.putString("bio",bio)
        editor.putString("username",username)
        editor.putString("imageUrl",imageUrl)
        editor.apply()
    }
}