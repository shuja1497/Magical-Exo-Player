package com.example.magicalexoplayer

import android.annotation.SuppressLint
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util

class DetailsVideoActivity : AppCompatActivity() {

    private var player: SimpleExoPlayer? = null
    private var playerView: PlayerView? = null

    private var playbackPosition: Long = 0
    private var currentWindow: Int = 0
    private var playWhenReady = true
    private val BANDWIDTH_METER = DefaultBandwidthMeter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_video)

        playerView = findViewById(R.id.video_view)

    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private fun initializePlayer() {

        val hlsUrl = intent.extras.getString("HLS_URL")
        val playBackPosition = intent.extras.getLong("PLAYBACK_POSITION")
        val currentWindowIndex = intent.extras.getInt("CURRENT_WINDOW_INDEX")


        if (player == null) {
            // a factory to create an AdaptiveVideoTrackSelection
            val adaptiveTrackSelectionFactory = AdaptiveTrackSelection.Factory(BANDWIDTH_METER)

            player = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(this),
                DefaultTrackSelector(adaptiveTrackSelectionFactory), DefaultLoadControl()
            )
            playerView?.player = player
            player?.playWhenReady = playWhenReady
            player?.seekTo(currentWindowIndex, playBackPosition)
        }
        val mediaSource = buildMediaSource(Uri.parse(hlsUrl))
        player?.prepare(mediaSource, false, true)
    }

    private fun releasePlayer() {
        if (player != null) {
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            playWhenReady = player!!.playWhenReady
            player?.release()
            player = null
        }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {

        val manifestDataSourceFactory = DefaultHttpDataSourceFactory("ua")


        return HlsMediaSource.Factory(manifestDataSourceFactory).createMediaSource(uri)

    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        playerView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
}
