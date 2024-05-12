package com.cbpajz.kameranz
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.OrientationEventListener
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class PlayerActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var fullScreenHelper: FullScreenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        fullScreenHelper = FullScreenHelper(this)

        val url = intent.getStringExtra("url")

        videoView = findViewById(R.id.videoView)
        videoView.setVideoURI(Uri.parse(url))
        videoView.start()

        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }

        val orientationEventListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                if (isLandscape) {
                    fullScreenHelper.enterFullScreen()
                } else {
                    fullScreenHelper.exitFullScreen()
                }
            }
        }

        if (orientationEventListener.canDetectOrientation()) {
            orientationEventListener.enable()
        }
    }
}
