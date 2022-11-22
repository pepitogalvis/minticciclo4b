package com.example.unleeg8.Domain

import androidx.lifecycle.MutableLiveData
import com.example.unleeg8.Model.Post
import com.google.firebase.database.*

class PostRepository {

    private  val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Posts")

    @Volatile private var INSTANCE : PostRepository ?= null

    fun getInstance() : PostRepository {
        return INSTANCE ?: synchronized(this) {

            val instance = PostRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadPosts(postList: MutableLiveData<List<Post>>){

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                try {

                    val _postList: List<Post> = snapshot.children.map { dataSnapshot ->

                        dataSnapshot.getValue(Post::class.java)!!

                    }

                    postList.postValue(_postList)

                }catch (e : Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}
