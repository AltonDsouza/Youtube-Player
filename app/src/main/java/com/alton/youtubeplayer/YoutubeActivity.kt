package com.alton.youtubeplayer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val YOUTUBE_VIDEO_ID = "wvPmzvNS3ho"
const val YOUTUBE_PLAYLIST = "PLXIHNpsOtth2xNI6oNG4JlHfpDa_gwUP1"

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private val TAG = "YoutubeActivity"
    private val DIALOG_REQUEST_CODE = 1

    val playerView by lazy {  YouTubePlayerView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_youtube)
//        val layout = findViewById<ConstraintLayout>(R.id.activity_youtube)
        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
        setContentView(layout)

        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.addView(playerView)
        playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?, youtubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        Log.d(TAG, "OnInitializationSuccess : provider is ${provider?.javaClass}")
        Log.d(TAG, "OnInitializationSuccess : provider is ${youtubePlayer?.javaClass}")

        youtubePlayer?.setPlaybackEventListener(playbackEventListener)
        youtubePlayer?.setPlayerStateChangeListener(stateChangedListener)
        if(!wasRestored){
//            youtubePlayer?.cueVideo(YOUTUBE_VIDEO_ID)
            youtubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)//Since you want the video loaded before playing
        }else{
            youtubePlayer?.play()
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youtubeInitializationResult: YouTubeInitializationResult?
    ) {
        if (youtubeInitializationResult?.isUserRecoverableError == true) {
            youtubeInitializationResult.getErrorDialog(this, DIALOG_REQUEST_CODE).show()
        } else {
            val errorMessage =
                "There was an error initializing the YoutubePlayer ($youtubeInitializationResult)"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private val playbackEventListener = object : YouTubePlayer.PlaybackEventListener{
        override fun onSeekTo(p0: Int) {
        }

        override fun onBuffering(p0: Boolean) {
        }

        override fun onPlaying() {
            Toast.makeText(this@YoutubeActivity, "Good, Video is playing", Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@YoutubeActivity, "Video has stopped", Toast.LENGTH_SHORT).show()
        }

        override fun onPaused() {
            Toast.makeText(this@YoutubeActivity, "Video has been paused", Toast.LENGTH_SHORT).show()
        }
    }

    private val stateChangedListener = object : YouTubePlayer.PlayerStateChangeListener{
        override fun onAdStarted() {
        }

        override fun onLoading() {
        }

        override fun onVideoStarted() {
            Toast.makeText(this@YoutubeActivity, "Video has started", Toast.LENGTH_SHORT).show()
        }

        override fun onLoaded(p0: String?) {
        }

        override fun onVideoEnded() {
            Toast.makeText(this@YoutubeActivity, "You've completed another video", Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult called with response code $resultCode and request code $requestCode")
        if(requestCode == DIALOG_REQUEST_CODE){
            playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)
        }
    }
}
