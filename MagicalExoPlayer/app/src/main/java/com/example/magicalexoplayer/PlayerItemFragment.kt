package com.example.magicalexoplayer

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.android.synthetic.main.fragment_player_item_list.*
import java.util.*


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [PlayerItemFragment.OnListFragmentInteractionListener] interface.
 */
class PlayerItemFragment : Fragment() {

    private var columnCount = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_player_item_list, container, false)


        val listOfHlsVideos = resources.getStringArray(R.array.hls_videos_list).toList()

        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        val adapter = MyPlayerItemRecyclerViewAdapter(context!!, listOfHlsVideos)
        recyclerView.hasFixedSize()
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override
            fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

//                if(recyclerView?.layoutManager == linearLayoutManager) {
//                    adapter.startPLayerView(linearLayoutManager.findFirstVisibleItemPosition())
//                }

            }
        })


        return view
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            PlayerItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
