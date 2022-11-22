package com.example.unleeg8.View.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unleeg8.Model.Post
import com.example.unleeg8.R
import com.squareup.picasso.Picasso


class PostAdapter(private val postList: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_post,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = postList[position]

        Picasso.get().load(currentitem.postimage).into(holder.postimageIV)
        holder.posttitleTV.text = currentitem.posttitle
        holder.postdescriptionTV.text = currentitem.postdescription
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val posttitleTV : TextView = itemView.findViewById(R.id.tvtitulo)
        val postimageIV : ImageView = itemView.findViewById(R.id.postpicture)
        val postdescriptionTV : TextView = itemView.findViewById(R.id.tvcontent)

    }
}