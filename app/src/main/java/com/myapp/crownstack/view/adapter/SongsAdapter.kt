package com.myapp.crownstack.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.myapp.crownstack.R
import com.myapp.crownstack.callback.ItemClickCallback
import com.myapp.crownstack.databinding.ItemSongBinding
import com.myapp.crownstack.model.SongModel
import com.myapp.crownstack.utils.BasicUtils

class SongsAdapter(var songs: List<SongModel>?, var callback: ItemClickCallback,val context:Context) :
    RecyclerView.Adapter<SongsAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding: ItemSongBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_song, parent, false
        )
        //in xml each variable in binding has setter getter itself and that setter takes the same variable as a parameter
        binding.setCallback(callback)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songs?.size ?: 0
    }

    fun updateList(songs: List<SongModel>?) {
        this.songs = songs
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.binding.song = songs?.get(position)
        Glide.with(context)
            .load(songs!![position].artworkUrl100).transform(RoundedCorners(20))
            .into(holder.binding.songImage)
        holder.binding.executePendingBindings()
    }

    //binding.root returns view which is inflating
    class ArticleViewHolder(var binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root)
}

