package com.uigitdev.backgroundvideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uigitdev.backgroundvideo.util.BackgroundVideo

class MainActivity : AppCompatActivity() {
    private lateinit var backgroundVideo: BackgroundVideo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        //Init background video view
        backgroundVideo = findViewById(R.id.backgroundVideo)

        //Video settings
        backgroundVideo.enableLooping()
        backgroundVideo.disableVolume()
        backgroundVideo.enableVideoFilter()

        //Video path from raw folder
        backgroundVideo.path("android.resource://${packageName}/${R.raw.video}")

        //Video view start
        backgroundVideo.apply()
    }
}