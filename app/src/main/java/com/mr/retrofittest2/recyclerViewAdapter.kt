package com.mr.retrofittest2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewAdapter(private val photosItems: List<PhotosItem?>?, private val clickListener:(PhotosItem)->Unit): RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val gridItem = layoutInflater.inflate(R.layout.grid_item_view,parent,false)
        return MyViewHolder(gridItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        photosItems?.get(position)?.let { holder.bind(it as PhotosItem,clickListener) }
    }

    override fun getItemCount(): Int = photosItems?.size!!
}

class MyViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {

    fun bind(photosItem: PhotosItem, clickListener: (PhotosItem) -> Unit) {
        val imgView: ImageView =view.findViewById(R.id.imageView)
        val imgUrl=photosItem.src?.large
        Glide.with(imgView.context).load(imgUrl).into(imgView)
        view.setOnClickListener {
            clickListener(photosItem)
        }

    }
}

