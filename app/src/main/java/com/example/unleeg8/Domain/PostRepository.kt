package com.example.unleeg8.Domain


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unleeg8.Model.Post
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class PostRepository {

    private val database = Firebase.database
    private val postFeedReference = database.getReference("Posts")

    fun fetchPostsFeed(liveData: MutableLiveData<List<Post>>) {
        postFeedReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val postsFeedItems: List<Post> = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue((Post::class.java))!!
                }

                liveData.postValue(postsFeedItems)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }



}
