package com.xheghun.vidit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.talkgap.R
import com.xheghun.vidit.adapter.CamEffectsAdapter

/**
 * A simple [Fragment] subclass.
 */

class CamEffectsFragment(val count: Int) : Fragment() {

    private lateinit var mView: View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_cam_effects, container, false)
        val layoutManager = GridLayoutManager(context,5)

        recyclerView = mView.findViewById(R.id.cam_effects_recylerview)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = CamEffectsAdapter(context,count)
        return mView
    }


}
