package com.example.unleeg8.View.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.unleeg8.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.sign

class login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "MyActivity"
    lateinit var signInEmail: String
    lateinit var signInPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        val email_login: TextView = findViewById(R.id.login_email)
        val contrasena_login: TextView = findViewById(R.id.login_password)
        val boton_registro: Button = findViewById(R.id.registro)
        val boton_inicio: Button = findViewById(R.id.btn_login)

        boton_inicio.setOnClickListener {
            signInEmail = email_login.text.toString().trim()
            signInPassword = contrasena_login.text.toString().trim()
            signIn(signInEmail,signInPassword)
            }
        boton_registro.setOnClickListener { iniciar_registro() }
    }

    private fun iniciar_registro() {
        /* Toast.makeText(this, "auch me clicaste",
             Toast.LENGTH_SHORT).show()*/
        val intent = Intent(this, Registro::class.java)
        intent.setAction(Intent.ACTION_VIEW)
        startActivity(intent)
    }

    private fun signIn(email: String, password: String) {

        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "Autenticacion Correcta",Toast.LENGTH_LONG).show()
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Por favor verifique sus datos de inicio de sesión.",
                        Toast.LENGTH_LONG).show()
                    val user = auth.currentUser
                    updateUIError(user)
                }
            }
        // [END sign_in_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this, Home::class.java))
    }

    private fun updateUIError(user: FirebaseUser?) {
        startActivity(Intent(this, login::class.java))
    }


}