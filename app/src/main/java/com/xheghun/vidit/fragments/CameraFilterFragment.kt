package com.xheghun.vidit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.talkgap.R
import com.xheghun.vidit.adapter.CamFilterAdapter

/**
 * A simple [Fragment] subclass.
 */
 class CameraFilterFragment(val title: String) : Fragment() {
    private lateinit var mView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: CamFilterAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_camera_filter, container, false)
        recyclerView = mView.findViewById(R.id.cam_filter_recyclerview)

        layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        recyclerView.layoutManager = layoutManager
        adapter = CamFilterAdapter(context,title)
        recyclerView.adapter = adapter
        return mView
    }

}
