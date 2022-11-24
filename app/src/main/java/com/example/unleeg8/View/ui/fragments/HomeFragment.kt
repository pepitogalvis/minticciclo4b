package com.example.unleeg8.View.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unleeg8.Model.Post
import com.example.unleeg8.R
import com.example.unleeg8.View.adapter.PostAdapter
import com.example.unleeg8.ViewModel.PostViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "Registro"
    private lateinit var viewModel : PostViewModel
    private lateinit var useRecyclerView: RecyclerView
    lateinit var adapter : PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        auth = Firebase.auth
        val view: View = inflater.inflate(R.layout.activity_userposts, container,false)
        val user = Firebase.auth.currentUser
        val nombre_usuario = user?.displayName.toString()


        if (user != null) {
            Log.d(TAG, "Nombre de usuario: $nombre_usuario")

        } else {

        }

        useRecyclerView = view.findViewById(R.id.recyclerView)
        useRecyclerView.layoutManager = LinearLayoutManager(context)
        useRecyclerView.setHasFixedSize(true)
        adapter = PostAdapter()
        useRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        viewModel.allPosts.observe(viewLifecycleOwner, Observer {

            adapter.updatePostList(it)
        })



        return view
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}