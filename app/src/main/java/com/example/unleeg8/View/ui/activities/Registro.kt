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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Registro : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "MyActivity"
    lateinit var correo_electronico: String
    lateinit var telefono: String
    lateinit var contrasena: String
    lateinit var contrasena_confirmacion: String
    lateinit var nombre_mascota: String
    lateinit var raza: String
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var etraza: EditText ?= null
    private var ettelefono: EditText ?= null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        auth = Firebase.auth

        val boton_registrarse: Button = findViewById(R.id.boton_registro)
        val etcorreo_electronico: EditText = findViewById(R.id.registro_email)
        val etcontrasena: EditText = findViewById(R.id.registro_password)
        val etcontrasenaconfirmacion: EditText = findViewById(R.id.registro_password_confirmacion)
        val etnombre_mascota: EditText = findViewById(R.id.registro_mascota)

        initialisedb()

        boton_registrarse.setOnClickListener {

            correo_electronico=etcorreo_electronico.text.toString().trim()
            contrasena=etcontrasena.text.toString().trim()
            nombre_mascota=etnombre_mascota.text.toString().trim()
            contrasena_confirmacion=etcontrasenaconfirmacion.text.toString().trim()

            if (correo_electronico.isNotEmpty() && contrasena.isNotEmpty() && nombre_mascota.isNotEmpty()
                && contrasena_confirmacion.isNotEmpty() && contrasena.equals(contrasena_confirmacion)) {
                createAccount(
                    correo_electronico,
                    contrasena,
                    nombre_mascota
                )
            } else {
                Toast.makeText(this, "Por favor verifique la información", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun initialisedb() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        auth = FirebaseAuth.getInstance()
        etraza = findViewById(R.id.registro_raza)
        ettelefono = findViewById(R.id.registro_telefono)
    }



    private fun createAccount(email: String, password: String, name: String) {
        // [START create_user_with_email]
        raza = etraza?.text.toString()
        telefono = ettelefono?.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    sendEmailVerification()
                    val user = auth.currentUser
                    val userId = auth!!.currentUser!!.uid
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }
                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "User profile updated.")
                            }
                        }
                    val currentUserDb = mDatabaseReference!!.child(userId)
                    currentUserDb.child("breed").setValue(raza)
                    currentUserDb.child("phone").setValue(telefono)
                    Toast.makeText(this, "Usuario Creado Satisfactoriamente",Toast.LENGTH_SHORT).show()
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Fallo la creación del usuario por favor valide la información",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }


    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Correo de confirmacion enviado a ${user.email.toString()}",Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(this, "No se puedo enviar el correo de confirmacion para ${user.email.toString()}",Toast.LENGTH_LONG).show()
                }

            }
        // [END send_email_verification]
    }


}