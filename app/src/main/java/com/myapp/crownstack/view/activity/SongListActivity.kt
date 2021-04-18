package com.myapp.crownstack.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.crownstack.R
import com.myapp.crownstack.callback.ItemClickCallback
import com.myapp.crownstack.databinding.ActivitySonglistBinding
import com.myapp.crownstack.model.SongList
import com.myapp.crownstack.model.SongModel
import com.myapp.crownstack.utils.*
import com.myapp.crownstack.view.adapter.SongsAdapter
import com.myapp.crownstack.viewmodel.SongListViewModel
import kotlinx.android.synthetic.main.activity_songlist.*
import kotlinx.android.synthetic.main.include_progress_bar.*

class SongListActivity : AppCompatActivity() {
    private val internetConnectivityManager = BasicUtils(this)
    private lateinit var activitySonglistBinding: ActivitySonglistBinding
    private lateinit var songListViewModel: SongListViewModel
    lateinit var adapter: SongsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songListViewModel = ViewModelProviders.of(this).get(SongListViewModel::class.java)
        activitySonglistBinding = DataBindingUtil.setContentView<ActivitySonglistBinding>(
            this,
            R.layout.activity_songlist
        )
        getAllSongs()
    }

    private fun notifyUi(response: Resource<SongList>?) {
        when (response) {
            is Resource.Loading -> progressBar.show()
            is Resource.Success -> {
                progressBar.hide()
                showList(response.data)
            }
            is Resource.Error -> {
                progressBar.hide()
                response.error?.let {
                    if (response.code == 401) {
                        parent_layout.snackbar("Invalid credentials")
                    } else {
                        parent_layout.snackbar(it)
                    }
                }
            }
            is Resource.NetworkError -> {
                progressBar.hide()
                parent_layout.snackbar(resources.getString(R.string.error_server_error))
            }
        }
    }

    private fun showList(data: SongList) {
        adapter = SongsAdapter(data.results, callBack,this)
        //setting recycler view adapter
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)

    }

    private fun getAllSongs() {
        if (internetConnectivityManager.isNetworkAvailable()) {
            activitySonglistBinding.also {
                it.let {
                    songListViewModel.getAllSongs().observe(this, Observer {
                        notifyUi(it)
                    })
                }
            }
        }
    }
    var callBack = object : ItemClickCallback {
        override fun onItemClicked(songModel: SongModel) {
            val intent = Intent(this@SongListActivity, SongDetailActivity::class.java)
            intent.putExtra("SongModel",songModel)
            startActivity(intent)
        }
    }
}