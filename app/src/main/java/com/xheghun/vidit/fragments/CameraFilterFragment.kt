package com.xheghun.vidit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.talkgap.R
import com.xheghun.vidit.adapter.CamFilterRecyclerAdapter

/**
 * A simple [Fragment] subclass.
 */
class CameraFilterFragment(val title: String) : Fragment() {
    private lateinit var mView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_camera_filter, container, false)
        val recyclerView: RecyclerView = mView.findViewById(R.id.cam_filter_recyclerview)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL


        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = CamFilterRecyclerAdapter(title,context)

        return mView
    }


}
