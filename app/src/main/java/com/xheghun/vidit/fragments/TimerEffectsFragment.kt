package com.xheghun.vidit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.talkgap.R
import com.xheghun.vidit.adapter.QuickEffectAdapter
import kotlinx.android.synthetic.main.fragment_timer_effects.view.*
import kotlinx.android.synthetic.main.timer_effects_layout.*

/**
 * A simple [Fragment] subclass.
 */
class TimerEffectsFragment : Fragment() {
    private lateinit var mView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_timer_effects, container, false)
        val timerLayoutManager = LinearLayoutManager(context)
        timerLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mView.timer_effects_rc.layoutManager = timerLayoutManager
        mView.timer_effects_rc.adapter = QuickEffectAdapter("timer")
        return mView
    }


}
