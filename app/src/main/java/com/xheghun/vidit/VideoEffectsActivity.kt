package com.xheghun.vidit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.talkgap.R
import com.xheghun.vidit.adapter.TabAdapter
import com.xheghun.vidit.fragments.QuickEffectsFragment
import com.xheghun.vidit.fragments.TimerEffectsFragment
import kotlinx.android.synthetic.main.activity_video_effects.*

class VideoEffectsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_effects)

        cancel_btn.setOnClickListener {
            finish()
        }
        save_btn.setOnClickListener {
            finish()
        }

       /* quick_effects_cancel_btn.setOnClickListener {
            if (quick_effects_layout.visibility == View.VISIBLE) {
                Animate.slideOutDown(quick_effects_layout,this)
            }
        }

        timer_effects_cancel_btn.setOnClickListener {
            if (timer_effects_layout.visibility == View.VISIBLE) {
                Animate.slideOutDown(timer_effects_layout,this)
            }
        }*/
      /*  show_quick_btn.setOnClickListener {
            if(timer_effects_layout.visibility == View.VISIBLE){
                Animate.slideOutDown(timer_effects_layout,this)

            }

            if(quick_effects_layout.visibility == View.VISIBLE) {
                Animate.slideOutDown(quick_effects_layout,this)
            } else {
                Animate.slideInUp(quick_effects_layout, this)
            }
        }

        show_timer_btn.setOnClickListener {
            if (quick_effects_layout.visibility == View.VISIBLE) {
                Animate.slideOutDown(quick_effects_layout, this)
            }

            if (timer_effects_layout.visibility == View.VISIBLE) {
                Animate.slideOutDown(timer_effects_layout,this)
            } else {
                Animate.slideInUp(timer_effects_layout, this)
            }
        }*/


        val adapter = TabAdapter(supportFragmentManager,0)
        adapter.addFragment(QuickEffectsFragment(),"Quick Effects")
        adapter.addFragment(TimerEffectsFragment(),"Timer Effects")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

}
