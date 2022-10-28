package com.example.unleeg8.View.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.unleeg8.R

class login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val boton_registro: Button = findViewById(R.id.registro)
        boton_registro.setOnClickListener { iniciar_registro() }
    }

    private fun iniciar_registro() {
        /* Toast.makeText(this, "auch me clicaste",
             Toast.LENGTH_SHORT).show()*/
        val intent = Intent(this, Registro::class.java)
        intent.setAction(Intent.ACTION_VIEW)
        startActivity(intent)
    }


}