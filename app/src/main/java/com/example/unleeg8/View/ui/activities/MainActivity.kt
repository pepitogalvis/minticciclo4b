package com.example.unleeg8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonEnviar: Button = findViewById(R.id.btnEnviar)
        botonEnviar.setOnClickListener {
            /*Toast.makeText(this, "auch me clicaste",
            Toast.LENGTH_LONG).show()*/
            val texto: TextView = findViewById(R.id.textHellow)
            texto.text=aleatorio(100).toString()
        }

    }

    fun aleatorio (max:Int):Int{

        return (1..max).random()
    }
}