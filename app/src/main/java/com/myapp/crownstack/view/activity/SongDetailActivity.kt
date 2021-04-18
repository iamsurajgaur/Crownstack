package com.myapp.crownstack.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.myapp.crownstack.R
import com.myapp.crownstack.databinding.ActivitySongdescBinding
import com.myapp.crownstack.model.SongModel
import kotlinx.android.synthetic.main.activity_songdesc.*

class SongDetailActivity : AppCompatActivity() {

    private var song_detail:SongModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activitySongDetailBinding:ActivitySongdescBinding =
            DataBindingUtil.setContentView<ActivitySongdescBinding>(this,R.layout.activity_songdesc)
        song_detail = intent.getSerializableExtra("SongModel") as SongModel?
        song_detail.apply {
            activitySongDetailBinding.song = this
        }
        Glide.with(this)
            .load(song_detail!!.artworkUrl100).transform(RoundedCorners(20))
            .into(song_image)
    }
}