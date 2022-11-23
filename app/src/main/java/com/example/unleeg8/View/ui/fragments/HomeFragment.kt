package com.example.unleeg8.View.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unleeg8.R
import com.example.unleeg8.View.adapter.PostAdapter
import com.example.unleeg8.ViewModel.PostViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "Registro"
    val viewModel by lazy { ViewModelProvider(this).get(PostViewModel::class.java) }
    private lateinit var recyclerpost: RecyclerView
    lateinit var adapter: PostAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        auth = Firebase.auth
        val view: View = inflater.inflate(R.layout.activity_userposts_feed, container,false)
        val user = Firebase.auth.currentUser
        val nombre_usuario = user?.displayName.toString()

        if (user != null) {

        } else {

        }

        recyclerpost = view.findViewById(R.id.recyclerView)
        adapter= PostAdapter()
        recyclerpost.layoutManager= LinearLayoutManager(context)
        recyclerpost.adapter=adapter
        observeData()
        return view
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    fun observeData() {

        viewModel.allPosts.observe(viewLifecycleOwner, Observer {

            adapter.updatePostList(it)
            Log.d(TAG,"statusString : ${it.size}")

        })

    }
}