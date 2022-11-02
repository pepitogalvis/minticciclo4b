package com.example.unleeg8.View.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.unleeg8.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "Perfil"
    private lateinit var databaseReference: DatabaseReference
    private var mDatabase: FirebaseDatabase? = null
    //private var mAuth: FirebaseAuth? = null
    private lateinit var storageReference : StorageReference
    private lateinit var imageUri : Uri
    private var improfile: ImageView?= null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mDatabase = FirebaseDatabase.getInstance()
        //databasereference = mDatabase!!.reference!!.child("Users")
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        //mAuth = FirebaseAuth.getInstance()
        auth = FirebaseAuth.getInstance()
        auth = Firebase.auth
        val view: View = inflater.inflate(R.layout.fragment_profile, container,false)
        val user = Firebase.auth.currentUser
        val uid = auth.currentUser?.uid
        val mUserReference = databaseReference!!.child(user!!.uid)
        val nombre_usuario = user?.displayName.toString()
        val email_usuario = user?.email.toString()
        val texto_bienvenida: TextView = view.findViewById(R.id.tv_profile)
        val texto_raza: TextView = view.findViewById(R.id.tv_raza)
        val texto_telefono: TextView = view.findViewById(R.id.tv_telefono)
        val texto_email: TextView = view.findViewById(R.id.tv_email)
        val boton_guardar: Button = view.findViewById(R.id.btn_save)
        val boton_elegir: Button = view.findViewById(R.id.btn_elegir)

        improfile = view.findViewById(R.id.profile)


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

        boton_guardar.setOnClickListener{

            if (imageUri != null && !imageUri.equals(Uri.EMPTY)) {
                uploadprofilePic()
            } else {
                Toast.makeText(getActivity(), "No hay foto para subir", Toast.LENGTH_SHORT)
            }


        }

        boton_elegir.setOnClickListener{

            selectImage()

        }


        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    private fun uploadprofilePic(){
        //imageUri = Uri.parse("android.resource://com.example.unleeg8/${R.drawable.westieimg}")
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {
                Log.d(TAG, "Foto subida con exito")
                Toast.makeText(getActivity(), "Foto subida con exito", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Log.d(TAG, "Falló la subida")
                Toast.makeText(getActivity(), "Fallo la subida de la foto", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private fun selectImage (){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)



        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            improfile?.setImageURI(imageUri)
            Toast.makeText(getActivity(),"Foto cargada con exito",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(getActivity(),"No se pudo cargar la foto",Toast.LENGTH_SHORT).show()
        }
    }




    companion object {
        fun newInstance() = ProfileFragment()
    }


}