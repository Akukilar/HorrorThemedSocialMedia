package com.example.horrorthemedsocialmedia.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.horrorthemedsocialmedia.R
import com.example.horrorthemedsocialmedia.navigation.Routes
import com.example.horrorthemedsocialmedia.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(navHostController: NavHostController){

    // USER INPUT --> https://developer.android.com/jetpack/compose/text/user-input
    var username by remember { mutableStateOf("")}
    var name by remember { mutableStateOf("")}
    var bio by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}
    var imageUri by remember { mutableStateOf<Uri?>(null)}


    // Llamada a viewmodel
    val authViewModel : AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)


    //  Pedir permisos en ANDROID --> https://developer.android.com/training/permissions/requesting?hl=es-419

    // Pedir permiso para lectura de imagenes y del almacenamiento
    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        Manifest.permission.READ_MEDIA_IMAGES
    } else Manifest.permission.READ_EXTERNAL_STORAGE

    val context = LocalContext.current

    // Guardar la imagen
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
        uri : Uri? -> imageUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()){

        isGranter : Boolean ->
        if(isGranter){

        }else{

        }
    }

    LaunchedEffect(firebaseUser){
        if (firebaseUser!=null){
            navHostController.navigate(Routes.BottomNav.routes) {
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Registrate", style = TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
            )
        )

        // FOTO en el REGISTRO
        Image(
            if (imageUri == null){
                painterResource(id = R.drawable.man)
            } else {
                rememberAsyncImagePainter(model = imageUri)
            },
            contentDescription = "persona",
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {

                    // Pedir permisos para acceder a las imagenes
                    val isGranter = ContextCompat.checkSelfPermission(
                        context, permissionToRequest
                    ) == PackageManager.PERMISSION_GRANTED

                    if (isGranter) {
                        launcher.launch("image/*")
                    } else {
                        permissionLauncher.launch(permissionToRequest)
                    }

                }, contentScale = ContentScale.Crop)

        Box(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it},
            label = { Text(text = "Nombre de usuario") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it},
            label = { Text(text = "Nombre") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it},
            label = { Text(text = "Bio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it},
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

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
            if (name.isEmpty() || email.isEmpty() || bio.isEmpty() || password.isEmpty() || imageUri == null) {
                Toast.makeText(context, "Por favor, rellena todos los datos", Toast.LENGTH_SHORT).show()
            } else {
                authViewModel.register(email, password, name, bio, username, imageUri!!, context)
            }

        }, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Registrarse",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp),
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }



        TextButton(onClick = {
            navHostController.navigate(Routes.Login.routes){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }

        }, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Tienes cuenta? Inicia Sesion",
                style = TextStyle(
                    fontSize = 18.sp
                )
            )
        }
    }
}

// Preview de la UI (abrir ventana de diseño)
@Preview(showBackground = true)
@Composable
fun RegisterView(){
   //Register()
}