package com.uigitdev.backgroundvideo.util

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.uigitdev.backgroundvideo.R

class BackgroundVideo(context: Context, attributeSet: AttributeSet) :
    RelativeLayout(context, attributeSet) {
    private var _view: View
    private lateinit var videoView: VideoView
    private lateinit var progressBar: ProgressBar
    private lateinit var videoFilter: FrameLayout
    private var isDisableVolume = false
    private var isLooping = false
    private var isEnableVideoFilter = false
    private var path: String = ""

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        _view = inflater.inflate(R.layout.ui_background_video, this, true)
        initType()
    }

    private fun initType() {
        videoView = _view.findViewById(R.id.item_video)
        progressBar = _view.findViewById(R.id.item_progress)
        videoFilter = _view.findViewById(R.id.item_video_filter)
    }

    fun disableVolume() {
        this.isDisableVolume = true
    }

    fun enableLooping() {
        this.isLooping = true
    }

    fun enableVideoFilter() {
        this.isEnableVideoFilter = true
    }

    fun path(path: String) {
        this.path = path
    }

    private fun videoSetting(it: MediaPlayer) {
        //Video looping
        it.isLooping = isLooping
        //Video volume disable
        if (isDisableVolume) {
            it.setVolume(0f, 0f)
        }
        //Video filter enable
        if (isEnableVideoFilter) {
            videoFilter.visibility = View.VISIBLE
        }
    }

    private fun videoScale(it: MediaPlayer) {
        val videoRatio = it.videoWidth / it.videoHeight.toFloat()
        val screenRatio = videoView.width / videoView.height.toFloat()
        val scaleX = videoRatio / screenRatio
        if (scaleX >= 1f) {
            videoView.scaleX = scaleX
        } else {
            videoView.scaleY = 1f / scaleX
        }
    }

    fun apply() {
        if (path.isNotEmpty()) {
            //Video path
            val uri: Uri = Uri.parse(path)
            videoView.setVideoURI(uri)

            //Progress visible
            progressBar.visibility = View.VISIBLE

            videoView.setOnPreparedListener {
                //Progress hide
                progressBar.visibility = View.GONE
                //Video Settings
                videoSetting(it)
                //Video resize
                videoScale(it)
                //Start video
                it.start()
            }
        } else {
            Toast.makeText(context, "Path is empty", Toast.LENGTH_SHORT).show()
        }
    }
}