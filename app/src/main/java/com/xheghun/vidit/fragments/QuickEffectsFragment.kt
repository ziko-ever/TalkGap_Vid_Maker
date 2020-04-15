package com.xheghun.vidit.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.talkgap.R
import com.xheghun.vidit.adapter.QuickEffectAdapter
import kotlinx.android.synthetic.main.fragment_quick_effects.view.*
import kotlinx.android.synthetic.main.quick_effects_layout.*

class QuickEffectsFragment : Fragment() {
    private lateinit var mView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_quick_effects, container, false)

        val quickLayoutManager = LinearLayoutManager(context)
        quickLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mView.quick_effects_rc.layoutManager = quickLayoutManager
        mView.quick_effects_rc.adapter = QuickEffectAdapter("quick effects")

        return mView
    }

}
