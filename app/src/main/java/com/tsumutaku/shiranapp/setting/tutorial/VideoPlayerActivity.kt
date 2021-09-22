package com.tsumutaku.shiranapp.setting.tutorial

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.MediaController
import android.widget.VideoView
import com.tsumutaku.shiranapp.R

class VideoPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "　遊びかた"

        var mediaControls: MediaController? = null
        val videoView: VideoView =findViewById(R.id.videoview)

        if (mediaControls == null) {
            // creating an object of media controller class
            mediaControls = MediaController(this)
            // set the anchor view for the video view
            mediaControls.setAnchorView(videoView)
        }

        videoView.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer) {
                mp.start()
                mp.setOnVideoSizeChangedListener(object : MediaPlayer.OnVideoSizeChangedListener {
                    override fun onVideoSizeChanged(mp: MediaPlayer, arg1: Int, arg2: Int) {
                        //progressbar2.visibility = android.widget.ProgressBar.GONE
                    }
                })
            }
        })

        videoView.setMediaController(mediaControls)
        //val value = intent.getStringExtra("selectedName")
        //val friendUid = intent.getStringExtra("friendUid")
        //val documentName = intent.getStringExtra("date")
        //val convertedUri = Uri.parse(value)

        //videoView.setVideoURI(convertedUri)
        videoView.setVideoPath("android.resource://" + this.getPackageName() + "/" + R.raw.introduce);
        videoView.start()

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}