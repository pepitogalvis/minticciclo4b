package com.example.unleeg8.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unleeg8.Domain.PostRepository
import com.example.unleeg8.Model.Post

class PostViewModel : ViewModel() {

    private val repository = PostRepository()

    private val _newsPostLiveData = MutableLiveData<List<Post>>()
    val postFeedLiveData: LiveData<List<Post>> = _newsPostLiveData

    fun fetchPostsFeed(){
        repository.fetchPostsFeed(_newsPostLiveData)
    }


    }


