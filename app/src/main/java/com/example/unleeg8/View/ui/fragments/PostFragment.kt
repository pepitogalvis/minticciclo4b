package com.example.unleeg8.View.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.unleeg8.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream


class PostFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private val TAG = "Post"
    private lateinit var image: ImageView
    private lateinit var des: EditText
    private lateinit var title: EditText
    private lateinit var imageUri : Uri
    private lateinit var upload : Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var edititle: String
    private lateinit var editdes: String
    private lateinit var editimage: String
    private lateinit var name: String
    private lateinit var uid: String
    private lateinit var email: String
    private lateinit var dp: String
    private lateinit var storageReference1 : StorageReference
    private lateinit var bitmap : Bitmap
    lateinit var timestamp: String
    lateinit var filepathname: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance()
        val view: View = inflater.inflate(R.layout.fragment_post, container, false)
        // Inflate the layout for this fragment
        title = view.findViewById(R.id.posttitle)
        val user = Firebase.auth.currentUser
        des = view.findViewById(R.id.postcontent)
        image = view.findViewById(R.id.imagep)
        upload = view.findViewById(R.id.btn_upload)
        val intent = requireActivity().intent

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val query = databaseReference!!.child(user!!.uid)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                name=user?.displayName.toString()
                uid=user?.uid.toString()
                email=user?.email.toString()
                dp = snapshot.child("image").value as String

            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        // After click on image we will be selecting an image
        image.setOnClickListener { selectImage() }

        upload.setOnClickListener{
            val titulo = "" + title.text.toString().trim()
            val description = "" + des.text.toString().trim()

            if (titulo.isEmpty()){
                title.error = "El titulo no puede estar vacio"
                Toast.makeText(context, "El titulo no puede estar vacio", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (description.isEmpty()){
                des.error = "La descripcion no puede estar vacia"
                Toast.makeText(context, "La descripcion no puede estar vacia", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(imageUri == null){
                Toast.makeText(context, "La descripcion no puede estar vacia", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else {
                uploadData(titulo, description)
            }

        }

        return view
    }

    private fun selectImage (){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data!!
            image?.setImageURI(imageUri)
            Toast.makeText(getActivity(),"Foto cargada con exito",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(getActivity(),"No se pudo cargar la foto",Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadData (titulo : String, description: String){

        timestamp = System.currentTimeMillis().toString()
        filepathname = "Posts/" + "post" + timestamp
        bitmap = (image.getDrawable() as BitmapDrawable).getBitmap()
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val data = byteArrayOutputStream.toByteArray()
        storageReference1 = FirebaseStorage.getInstance().getReference().child(filepathname)
        storageReference1.putBytes(data).addOnSuccessListener {
            val resultado = it.metadata!!.reference!!.downloadUrl;
            resultado.addOnSuccessListener {
                var downloadUri = it.toString()
                // if task is successful the update the data into firebase
                val hashMap = HashMap<Any, String>()
                hashMap.put("userid",uid)
                hashMap.put("username", name)
                hashMap.put("useremail",email)
                hashMap.put("userprofilepicture",dp)
                hashMap.put("posttitle",titulo)
                hashMap.put("postdescription",description)
                hashMap.put("postimage",downloadUri)
                hashMap.put("posttime",timestamp)
                hashMap.put("postlikes","0")
                hashMap.put("postcomments","0")

                databaseReference = FirebaseDatabase.getInstance().getReference("Posts")
                databaseReference.child(timestamp).setValue(hashMap)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Publicación exitosa", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Publicación Exitosa")
                        title.setText("")
                        des.setText("")
                        image.setImageURI(null)


                    }.addOnFailureListener{
                        Toast.makeText(context, "Falló la publicacion", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Publicación Fallida")
                    }

            }
        }




    }


}