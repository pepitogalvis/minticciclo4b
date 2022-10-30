package com.example.unleeg8.View.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unleeg8.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Recuperar : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "Recuperar"
    lateinit var correo_recuperacion: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery)
        val btnrecuperar: Button = findViewById(R.id.boton_recuperar)
        val etrecuperacion: EditText = findViewById(R.id.contrasena_recuperar)

        btnrecuperar.setOnClickListener{
            correo_recuperacion = etrecuperacion.text.toString().trim()
            if(correo_recuperacion.isNotEmpty()){
                sendPasswordReset(correo_recuperacion)
            } else {
                Toast.makeText(this, "Por favor ingrese su correo", Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun sendPasswordReset(email: String) {
        // [START send_password_reset]

        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo de reinicio enviado a $correo_recuperacion", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "Email sent.")
                    updateui()
                } else {
                    Toast.makeText(this, "Correo $correo_recuperacion no existe en la base de datos", Toast.LENGTH_LONG).show()
                    updateui()
                }
            }
        // [END send_password_reset]
    }

    private fun updateui() {
        val intent = Intent(this, login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

    }
}