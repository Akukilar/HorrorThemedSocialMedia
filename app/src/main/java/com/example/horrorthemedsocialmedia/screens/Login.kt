package com.example.horrorthemedsocialmedia.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.horrorthemedsocialmedia.navigation.Routes
import com.example.horrorthemedsocialmedia.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavHostController){

    val authViewModel:AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState()

    LaunchedEffect(firebaseUser){
        if (firebaseUser!=null){
            navController.navigate(Routes.BottomNav.routes) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

 // USER INPUT --> https://developer.android.com/jetpack/compose/text/user-input
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}

    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "Inicio de sesion", style = TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        ))

        Box(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it},
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it},
            label = { Text(text = "Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(modifier = Modifier.height(24.dp))

        ElevatedButton(onClick = {
            authViewModel.login(email,password,context)

        }, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Iniciar Sesion",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp),
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }

        TextButton(onClick = {
            navController.navigate(Routes.Register.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }

        }, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Nuevo usuario? Crear cuenta",
                style = TextStyle(
                    fontSize = 18.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginView(){
   // Login()
}