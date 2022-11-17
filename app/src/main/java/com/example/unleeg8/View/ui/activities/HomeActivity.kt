package com.example.unleeg8.View.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.unleeg8.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "Registro"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        val nombre_usuario = user?.displayName.toString()
        val texto_bienvenida: TextView = findViewById(R.id.msg_bienvenida)
        val boton_salida: Button = findViewById(R.id.salida)

        if (user != null) {
            texto_bienvenida.text = "Bienvenido $nombre_usuario"
        } else {
            updateUI(user)
            Toast.makeText(this, "Por favor inicie sesión",Toast.LENGTH_SHORT).show()

        }

        boton_salida.setOnClickListener{
            signOut()
        }

    }

    private fun signOut() {
        // [START auth_sign_out]
        Firebase.auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

        // [END auth_sign_out]
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
