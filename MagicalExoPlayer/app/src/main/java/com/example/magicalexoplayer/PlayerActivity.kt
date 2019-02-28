package com.example.magicalexoplayer

import android.annotation.SuppressLint
import android.app.FragmentTransaction
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory


class PlayerActivity : AppCompatActivity(), PlayerItemFragment.OnListFragmentInteractionListener {

    // bandwidth meter to measure and estimate bandwidth
    private val BANDWIDTH_METER = DefaultBandwidthMeter()
    private val TAG = "PLAYER_ACTIVITY"

    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition : Long = 0
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val fragmentManager = supportFragmentManager;
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container, PlayerItemFragment.newInstance(1))
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.commit()

//        playerView = findViewById(R.id.video_view)
    }

    override fun onListFragmentInteraction(playerView: PlayerView) {
        Log.d("QWERTYIOP", "444444")

        Toast.makeText(this, playerView.player.contentPosition.toString(), Toast.LENGTH_LONG).show()

    }

//    private fun initializePlayer() {
//        if (player == null) {
//            // a factory to create an AdaptiveVideoTrackSelection
//            val adaptiveTrackSelectionFactory =
//                AdaptiveTrackSelection.Factory(BANDWIDTH_METER)
//            // let the factory create a player instance with default components
//            player = ExoPlayerFactory.newSimpleInstance(
//                DefaultRenderersFactory(this),
//                DefaultTrackSelector(),
//                DefaultLoadControl()
//            )
//
//            playerView.player = player
//            player?.playWhenReady = playWhenReady
//            player?.seekTo(currentWindow, playbackPosition)
//        }
//        val mediaSource = buildMediaSource(Uri.parse(getString(R.string.media_url_hls)))
//        player?.prepare(mediaSource, true, false)
//    }
//
//    private fun releasePlayer() {
//        if (player != null) {
//            playbackPosition = player!!.currentPosition
//            currentWindow = player!!.currentWindowIndex
//            playWhenReady = player!!.playWhenReady
//            player!!.release()
//            player = null
//        }
//    }
//
//    private fun buildMediaSource(uri: Uri): MediaSource {
//
//        val manifestDataSourceFactory : DataSource.Factory = DefaultHttpDataSourceFactory("ua")
//        return HlsMediaSource.Factory(manifestDataSourceFactory)
//            .setAllowChunklessPreparation(true)
//            .createMediaSource(uri)
//    }
//
//    @SuppressLint("InlinedApi")
//    private fun hideSystemUi() {
//        playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
//                or View.SYSTEM_UI_FLAG_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
//    }
//
//    public override fun onStart() {
//        super.onStart()
//        if (Util.SDK_INT > 23) {
//            initializePlayer()
//        }
//    }
//
//    public override fun onResume() {
//        super.onResume()
//        hideSystemUi()
//        if (Util.SDK_INT <= 23 || player == null) {
//            initializePlayer()
//        }
//    }
//
//    public override fun onPause() {
//        super.onPause()
//        if (Util.SDK_INT <= 23) {
//            releasePlayer()
//        }
//    }
//
//    public override fun onStop() {
//        super.onStop()
//        if (Util.SDK_INT > 23) {
//            releasePlayer()
//        }
//    }
}
