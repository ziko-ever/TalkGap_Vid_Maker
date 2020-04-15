package com.xheghun.vidit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.talkgap.R
import com.xheghun.vidit.adapter.TabAdapter
import com.xheghun.vidit.classes.Animate
import com.xheghun.vidit.fragments.CameraFilterFragment
import kotlinx.android.synthetic.main.activity_selfie_video_edit.*

class SelfieVideoEditActivity : AppCompatActivity() {

    private lateinit var filterLayout: ViewGroup
    private lateinit var filterTab: TabLayout
    private lateinit var filterViewPager: XheghunViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selfie_video_edit)

        filterTab = findViewById(R.id.filter_tab)
        filterLayout = findViewById(R.id.filter_layout)
        filterViewPager = findViewById(R.id.filter_viewpager)

        initFilters()

        quick_effects_btn.setOnClickListener {
            startActivity(Intent(this,VideoEffectsActivity::class.java))
        }

        sound_btn.setOnClickListener {
            startActivity(Intent(this, AddMusicActivity::class.java))
        }

        cover_btn.setOnClickListener {
            startActivity(Intent(this,CoverImageSelectActivity::class.java))
        }

        close_btn.setOnClickListener {
            finish()
        }

        volume_btn.setOnClickListener {
            Animate.slideInUp(adjust_volume_layout,this)
            Animate.slideOutDown(tools_layout,this)
            Animate.slideOutRight(filter_btn,this)
            back_btn.visibility = View.VISIBLE
            close_btn.visibility = View.GONE
        }

        back_btn.setOnClickListener {
            if (adjust_volume_layout.visibility == View.VISIBLE) {
                Animate.slideOutDown(adjust_volume_layout, this)
            } else if (filterLayout.visibility == View.VISIBLE) {
                Animate.slideOutDown(filterLayout,this)
            }
            Animate.slideInRight(filter_btn,this)
            Animate.slideInUp(tools_layout,this)
            back_btn.visibility = View.GONE
            close_btn.visibility = View.VISIBLE
        }

        filter_btn.setOnClickListener {
            Animate.slideInUp(filterLayout,this)
            Animate.slideOutDown(tools_layout,this)
            Animate.slideOutRight(filter_btn,this)
            back_btn.visibility = View.VISIBLE
            close_btn.visibility = View.GONE
        }

        forward_btn.setOnClickListener {
            startActivity(Intent(this,UploadVideoActivity::class.java))
        }

    }

    private fun initFilters() {
        val adapter = TabAdapter(supportFragmentManager,0)
        adapter.addFragment(CameraFilterFragment(" TG- "),"Smart")
        adapter.addFragment(CameraFilterFragment("E- "),"Style-Leo")
        adapter.addFragment(CameraFilterFragment("K- "),"Creative")
        adapter.notifyDataSetChanged()

        filterViewPager.offscreenPageLimit = 3
        filterViewPager.adapter = adapter
        filterTab.setupWithViewPager(filterViewPager)
    }
}
