package com.example.unleeg8.View.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unleeg8.Model.Post
import com.example.unleeg8.R
import com.example.unleeg8.databinding.UserPostBinding
import com.squareup.picasso.Picasso


class PostAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val postListItems = mutableListOf<Post>()
    private val TAG = "PostAdapter"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostAdapterItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as PostAdapterItemViewHolder).onBind(postListItems[position])

    }

    override fun getItemCount(): Int {
        return postListItems.size
    }

    @SuppressLint("NotifyDataSetChange")

    fun setItems(postListItems: List<Post>) {
        this.postListItems.clear()
        this.postListItems.addAll(postListItems)
        notifyDataSetChanged()
    }

    inner class PostAdapterItemViewHolder(parent: ViewGroup):RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.user_post, parent, false)
    ){
        private val binding = UserPostBinding.bind(itemView)

        fun onBind (Post: Post){
            binding.tvtitulo.text = Post.posttitle
            binding.tvcontent.text = Post.postdescription
            Picasso.get().load(Post.postimage).into(binding.postpicture)


        }

    }


}