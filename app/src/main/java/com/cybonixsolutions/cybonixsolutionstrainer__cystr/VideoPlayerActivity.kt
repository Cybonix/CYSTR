package com.cybonixsolutions.cybonixsolutionstrainer__cystr

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.VideoView
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

class VideoPlayerActivity : AppCompatActivity() {

    // Constants should be placed inside companion object in Kotlin.
    companion object {
        private const val SKIP_AMOUNT_MS = 10_000  // 10 seconds
    }

    private var videoPlayer: VideoView? = null
    private var currentVideo: Video? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        // Get the video from the intent or finish the activity if no video was passed.
        currentVideo = intent.getParcelableExtra("selected_video")
        currentVideo?.let {
            initializeVideoPlayer(it)
        } ?: run {
            Toast.makeText(this, "Error: No video selected!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    /**
     * Initialize the video player and associated controls.
     * @param video The video to be played.
     */
    private fun initializeVideoPlayer(video: Video) {
        videoPlayer = findViewById(R.id.videoView)

        // Set the video for the player
        videoPlayer?.setVideoURI(Uri.parse(video.videoUrl))
        videoPlayer?.requestFocus()

        // Set listeners for the player controls.
        findViewById<Button>(R.id.playButton).setOnClickListener { onPlayButtonPress() }
        findViewById<Button>(R.id.pauseButton).setOnClickListener { onPauseButtonPress() }
        findViewById<Button>(R.id.rewindButton).setOnClickListener { onRewindButtonPress() }
        findViewById<Button>(R.id.fastForwardButton).setOnClickListener { onFastForwardButtonPress() }

        videoPlayer?.setOnCompletionListener { onVideoEnd() }
    }

    /** Play the video if it isn't already playing. */
    private fun onPlayButtonPress() {
        if (videoPlayer?.isPlaying == false) {
            videoPlayer?.start()
        }
    }

    /** Pause the video if it's playing. */
    private fun onPauseButtonPress() {
        if (videoPlayer?.isPlaying == true) {
            videoPlayer?.pause()
        }
    }

    /** Rewind the video by a fixed duration. */
    private fun onRewindButtonPress() {
        videoPlayer?.let { player ->
            val newPosition = (player.currentPosition - SKIP_AMOUNT_MS).coerceAtLeast(0)
            player.seekTo(newPosition)
        }
    }

    /** Fast forward the video by a fixed duration. */
    private fun onFastForwardButtonPress() {
        videoPlayer?.let { player ->
            val newPosition = (player.currentPosition + SKIP_AMOUNT_MS).coerceAtMost(player.duration)
            player.seekTo(newPosition)
        }
    }

    /** Handle video completion, implement replay or other functionalities here. */
    private fun onVideoEnd() {
        // TODO: Implement replay or other functionalities here.
    }
}
