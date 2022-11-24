package com.example.unleeg8.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unleeg8.Domain.PostRepository
import com.example.unleeg8.Model.Post

class PostViewModel : ViewModel() {

    private val repository : PostRepository
    private val _allPosts = MutableLiveData<List<Post>>()
    val allPosts : LiveData<List<Post>> = _allPosts


    init {

        repository = PostRepository().getInstance()
        repository.loadPosts(_allPosts)

    }

}
