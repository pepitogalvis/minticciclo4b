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
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "Perfil"
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        auth = Firebase.auth
        val view: View = inflater.inflate(R.layout.fragment_profile, container,false)
        val user = Firebase.auth.currentUser
        val mUserReference = mDatabaseReference!!.child(user!!.uid)
        val nombre_usuario = user?.displayName.toString()
        val email_usuario = user?.email.toString()
        val texto_bienvenida: TextView = view.findViewById(R.id.tv_profile)
        val texto_raza: TextView = view.findViewById(R.id.tv_raza)
        val texto_telefono: TextView = view.findViewById(R.id.tv_telefono)
        val texto_email: TextView = view.findViewById(R.id.tv_email)


        if (user != null) {
            Log.d(TAG, "Nombre de usuario: $nombre_usuario")
            texto_bienvenida.text = "$nombre_usuario"
            texto_email.text=email_usuario
            mUserReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    texto_raza!!.text = snapshot.child("breed").value as String
                    texto_telefono!!.text = snapshot.child("phone").value as String
                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })
        } else {

        }
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }


}