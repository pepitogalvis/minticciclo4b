package com.example.unleeg8.View.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.unleeg8.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "Registro"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        auth = Firebase.auth
        val view: View = inflater.inflate(R.layout.fragment_home, container,false)
        val user = Firebase.auth.currentUser
        val nombre_usuario = user?.displayName.toString()
        val texto_bienvenida: TextView = view.findViewById(R.id.tv_home)

        if (user != null) {
            Log.d(TAG, "Nombre de usuario: $nombre_usuario")
            texto_bienvenida.text = "$nombre_usuario"

        } else {

        }

        return view
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}