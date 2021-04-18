package com.myapp.crownstack.callback

import com.myapp.crownstack.model.SongModel

interface ItemClickCallback {
    fun onItemClicked(songModel: SongModel)
}