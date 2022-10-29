package com.example.unleeg8.View.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unleeg8.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class Registro : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "MyActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_signup)
        val boton_registrarse: Button = findViewById(R.id.registro)
        val correo_electronico: TextView = findViewById(R.id.registro_email)
        val contrasena: TextView = findViewById(R.id.registro_password)
        val nombre_mascota: TextView = findViewById(R.id.pet_name)
        boton_registrarse.setOnClickListener {

            createAccount(correo_electronico.text.toString().trim(),contrasena.text.toString().trim(),nombre_mascota.text.toString().trim())}
    }


    private fun createAccount(email: String, password: String, name: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    sendEmailVerification()
                    val user = auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }
                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "User profile updated.")
                            }
                        }
                    Toast.makeText(this, "Usuario Creado Satisfactoriamente",Toast.LENGTH_SHORT).show()
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, login::class.java)
        intent.setAction(Intent.ACTION_VIEW)
        startActivity(intent)
    }


    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                Toast.makeText(this, "Correo de confirmacion enviado a ${user.email.toString()}",Toast.LENGTH_LONG).show()
            }
        // [END send_email_verification]
    }


}