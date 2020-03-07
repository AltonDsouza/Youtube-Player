package com.alton.youtubeplayer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.activity_standalone.*
import kotlin.IllegalArgumentException

class StandAloneActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standalone)

        btnPlayVideo.setOnClickListener(this)
        btnPlaylist.setOnClickListener(this)

        //Method 1
//        btnPlayVideo.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View?) {
//                TODO("Not yet implemented")
//            }
//        })


        //Method 2
//        val listener = View.OnClickListener{v ->
//        }
//        btnPlaylist.setOnClickListener(listener)
//        btnPlayVideo.setOnClickListener(listener)

        //Method 3
//        btnPlayVideo.setOnClickListener(View.OnClickListener {
//        })
    }

    override fun onClick(v: View?) {
        val intent = when (v?.id) {
            R.id.btnPlayVideo -> YouTubeStandalonePlayer.createVideoIntent(
                this, getString(R.string.GOOGLE_API_KEY), YOUTUBE_VIDEO_ID, 0 , true, true
            )
            R.id.btnPlaylist -> YouTubeStandalonePlayer.createPlaylistIntent(
                this, getString(R.string.GOOGLE_API_KEY), YOUTUBE_PLAYLIST, 0, 0, true, true
            )
            else -> throw IllegalArgumentException("Undefined Button clicked")
        }

        startActivity(intent)
    }
}