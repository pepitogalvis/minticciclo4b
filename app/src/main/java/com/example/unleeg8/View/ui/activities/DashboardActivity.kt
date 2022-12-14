package com.example.unleeg8.View.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.unleeg8.R
import com.example.unleeg8.View.ui.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "Principal"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        val nombre_usuario = user?.displayName.toString()
        val bottomNav : BottomNavigationView = findViewById(R.id.bottomNav)
        val boton_salida: Button = findViewById(R.id.logout)
        val toolbar = getSupportActionBar()
        if (user != null) {
            Log.d(TAG, "signInWithEmail:success $nombre_usuario")
        } else {
            Log.d(TAG, "signInWithEmail:failed")
            updateUI(user)
        }
        boton_salida.setOnClickListener{
            signOut()
        }

        loadFragment(HomeFragment.newInstance())

        bottomNav.setOnItemReselectedListener { item ->
            var fragment: Fragment
            when (item.itemId) {
                R.id.nav_home-> {
                    toolbar?.title = "Inicio"
                    fragment = HomeFragment()
                    loadFragment(fragment)
                    true
                }
                R.id.nav_profile -> {
                    toolbar?.title = "Perfil"
                    fragment = ProfileFragment()
                    loadFragment(fragment)
                    true
                }
                R.id.nav_addblogs -> {
                    toolbar?.title = "Nueva Entrada"
                    fragment = PostFragment()
                    loadFragment(fragment)
                    true
                }
                R.id.nav_users -> {
                    toolbar?.title = "Mapas"
                    fragment = ShipFragment()
                    loadFragment(fragment)
                    true
                }

            }
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
    private fun signOut() {
        // [START auth_sign_out]
        Firebase.auth.signOut()
        val intent = Intent(this, login::class.java)
        Toast.makeText(this, "Cierre de Sesi??n Exitoso", Toast.LENGTH_SHORT)
            .show()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        // [END auth_sign_out]
    }
}