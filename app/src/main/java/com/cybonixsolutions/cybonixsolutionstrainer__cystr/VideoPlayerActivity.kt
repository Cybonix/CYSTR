package com.cybonixsolutions.cybonixsolutionstrainer__cystr

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import android.net.Uri
import android.view.View
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
        @Suppress("DEPRECATION")
        currentVideo = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selected_video", Video::class.java)
        } else {
            intent.getParcelableExtra("selected_video")
        }
        
        currentVideo?.let {
            initializeVideoPlayer(it)
        } ?: run {
            Toast.makeText(this, getString(R.string.no_video_selected), Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    /**
     * Initialize the video player and associated controls.
     * @param video The video to be played.
     */
    private fun initializeVideoPlayer(video: Video) {
        videoPlayer = findViewById(R.id.videoView)

        try {
            // Set up media controller for standard playback controls
            val mediaController = MediaController(this)
            mediaController.setAnchorView(videoPlayer)
            videoPlayer?.setMediaController(mediaController)
            
            // Set the video for the player
            videoPlayer?.setVideoURI(Uri.parse(video.videoUrl))
            videoPlayer?.requestFocus()
            
            // Set up loading listener
            videoPlayer?.setOnPreparedListener { mp ->
                // Hide custom controls initially if using MediaController
                Toast.makeText(this, "Video ready to play", Toast.LENGTH_SHORT).show()
                mp.start() // Auto-start playback
            }
            
            // Set up error listener
            videoPlayer?.setOnErrorListener { _, what, extra ->
                Toast.makeText(
                    this,
                    "Error during playback. Error code: $what, $extra",
                    Toast.LENGTH_LONG
                ).show()
                true // Error handled
            }

            // Set listeners for the custom player controls
            findViewById<Button>(R.id.playButton).setOnClickListener { onPlayButtonPress() }
            findViewById<Button>(R.id.pauseButton).setOnClickListener { onPauseButtonPress() }
            findViewById<Button>(R.id.rewindButton).setOnClickListener { onRewindButtonPress() }
            findViewById<Button>(R.id.fastForwardButton).setOnClickListener { onFastForwardButtonPress() }

            videoPlayer?.setOnCompletionListener { onVideoEnd() }
            
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
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

    /** Handle video completion, showing a replay button. */
    private fun onVideoEnd() {
        // Show a Toast message for replay option
        Toast.makeText(this, getString(R.string.video_ended), Toast.LENGTH_SHORT).show()
        
        // Add an option to replay the video
        findViewById<Button>(R.id.playButton).apply {
            text = getString(R.string.replay)
            setOnClickListener { 
                videoPlayer?.seekTo(0)
                videoPlayer?.start()
                text = getString(R.string.play)
                setOnClickListener { onPlayButtonPress() }
            }
        }
    }
}
