package com.myapp.crownstack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.crownstack.model.SongList
import com.myapp.crownstack.repo.SongListRepo
import com.myapp.crownstack.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SongListViewModel:ViewModel() {
    var songListRepoliveData = MutableLiveData<Resource<SongList>>()
    private val songListRepo : SongListRepo = SongListRepo()

    fun getAllSongs(): LiveData<Resource<SongList>> {
        songListRepoliveData.value = Resource.Loading<SongList>(null)
        CoroutineScope(Dispatchers.IO).launch {
            songListRepo.getSongList().let {
                songListRepoliveData.postValue(it)
            }
        }
        return songListRepoliveData
    }
}