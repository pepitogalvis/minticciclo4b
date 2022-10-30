package com.example.unleeg8.View.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.unleeg8.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

class Home : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "Registro"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        val user_email = user?.email.toString()
        val nombre_usuario = user?.displayName.toString()
        val texto_bienvenida: TextView = findViewById(R.id.msg_bienvenida)
        val boton_salida: Button = findViewById(R.id.salida)

        if (user != null) {
            texto_bienvenida.text = "Bienvenido $nombre_usuario"
            Log.d(TAG, "Usuario Logueado con exito: $user_email")
            Log.d(TAG, "Usuario Logueado con exito: $nombre_usuario")
        } else {
            Log.d(TAG, "No hay usuario registrado")

        }

        boton_salida.setOnClickListener{
            signOut()
        }

    }

    private fun signOut() {
        // [START auth_sign_out]
        Firebase.auth.signOut()
        val intent = Intent(this, login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

        // [END auth_sign_out]
    }

}
