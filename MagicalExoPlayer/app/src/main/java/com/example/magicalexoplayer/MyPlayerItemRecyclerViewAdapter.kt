package com.example.magicalexoplayer

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

import kotlinx.android.synthetic.main.fragment_player_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MyPlayerItemRecyclerViewAdapter(
    private val context: Context,
    private val mValues: List<String>)
    : RecyclerView.Adapter<MyPlayerItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_player_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindVideos(mValues[position])
    }

    var currentPosition = 0

    fun startPLayerView(position: Int){

        currentPosition = position

    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView), View.OnClickListener {

        init {
            mView.setOnClickListener(this)
        }
        val playerView : PlayerView = mView.findViewById(R.id.video_view)
        private val BANDWIDTH_METER = DefaultBandwidthMeter()
        private var player: SimpleExoPlayer? = null


        fun bindVideos(hslUrl : String){
            initializePlayer(hslUrl)
        }

        override fun onClick(v: View?) {

            Toast.makeText(context, playerView.player.contentPosition.toString(), Toast.LENGTH_LONG).show()
            Toast.makeText(context, mValues[adapterPosition], Toast.LENGTH_LONG).show()
        }

        private fun initializePlayer(url: String) {
        if (player == null) {
            // a factory to create an AdaptiveVideoTrackSelection
            val adaptiveTrackSelectionFactory =
                AdaptiveTrackSelection.Factory(BANDWIDTH_METER)
            // let the factory create a player instance with default components
            player = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(context),
                DefaultTrackSelector(adaptiveTrackSelectionFactory),
                DefaultLoadControl()
            )

            playerView.player = player
            player?.playWhenReady = true
            player?.seekTo(0,1000)
        }
        val mediaSource = buildMediaSource(Uri.parse(url))
        player?.prepare(mediaSource, true, false)
    }

        private fun buildMediaSource(uri: Uri): MediaSource {

        val manifestDataSourceFactory : DataSource.Factory = DefaultHttpDataSourceFactory("ua")
        return HlsMediaSource.Factory(manifestDataSourceFactory)
            .setAllowChunklessPreparation(true)
            .createMediaSource(uri)
        }
    }
}
