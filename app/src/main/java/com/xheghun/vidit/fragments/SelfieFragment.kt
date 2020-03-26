package com.xheghun.vidit.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Rational
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.tabs.TabLayout
import com.talkgap.R
import com.xheghun.vidit.MusicSelectActivity
import com.xheghun.vidit.XheghunViewPager
import com.xheghun.vidit.adapter.TabAdapter
import com.xheghun.vidit.classes.Animate

/**
 * A simple [Fragment] subclass.
 */
class SelfieFragment : Fragment() {

    private val ADD_MUSIC_REQUEST_CODE = 9492
    private var lensFacing = CameraX.LensFacing.BACK
    private var imageCapture: ImageCapture? = null
    private lateinit var mView: View
    private var flashOn: Boolean = false
    private lateinit var filterLayout: ViewGroup
    private lateinit var filterTab: TabLayout
    private lateinit var filterViewPager: XheghunViewPager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for context!! fragment
        mView = inflater.inflate(R.layout.fragment_selfie, container, false)
        filterTab = mView.findViewById(R.id.filter_tab)
        filterLayout = mView.findViewById(R.id.filter_layout)
        filterViewPager = mView.findViewById(R.id.filter_viewpager)
        val backBtn = mView.findViewById<ImageButton>(R.id.back_btn)
        val snapLayout = mView.findViewById<ViewGroup>(R.id.snap_layout)
        val addMusicBtn: ViewGroup = mView.findViewById(R.id.add_music_btn)
        val camActionsLayout: ViewGroup = mView.findViewById(R.id.cam_actions_layout)
        val snapButton: ImageButton = mView.findViewById(R.id.snap_btn)
        val flashToggle: ImageButton = mView.findViewById(R.id.flash_toggle_btn)
        val switchCamButton: ImageButton = mView.findViewById(R.id.switch_cam_btn)
        val close = mView.findViewById<ImageButton>(R.id.close_btn)
        val timerBtn: ImageButton = mView.findViewById(R.id.timer_btn)
        val speedBtn : ImageButton = mView.findViewById(R.id.speed_btn)
        val filterBtn: ImageButton = mView.findViewById(R.id.filter_btn)

        initFilters()

        snapButton.setOnClickListener {
            showToast("Snap Clicked")
        }

        flashToggle.setOnClickListener {
            if (flashOn) {
                showToast("flash off")
            } else {
                showToast("flash on")
            }
            flashOn = !flashOn
        }

        switchCamButton.setOnClickListener { showToast("switching lens") }

        close.setOnClickListener {
            showToast("Close")
        }

        addMusicBtn.setOnClickListener {
            startActivityForResult(Intent(context,MusicSelectActivity::class.java),ADD_MUSIC_REQUEST_CODE)
        }

        backBtn.setOnClickListener {
            Animate.slideInLeft(close,context)
            Animate.slideInRight(camActionsLayout,context)
            Animate.slideUp(snapLayout,context)
            backBtn.visibility = View.GONE
            if (filterLayout.visibility == View.VISIBLE) {
                Animate.slideDown(filterLayout,context)
            }
        }

        filterBtn.setOnClickListener {
            if (filterLayout.visibility == View.INVISIBLE || filterLayout.visibility == View.GONE) {
                backBtn.visibility = View.VISIBLE
                Animate.slideUp(filterLayout,context)
                Animate.slideOutLeft(close,context)
                Animate.slideOutRight(camActionsLayout,context)
                Animate.slideDown(snapLayout,context)
            } else {
                Animate.slideDown(filterLayout,context)
            }


            showToast("filter clicked")
        }

        speedBtn.setOnClickListener { showToast("speed clicked") }

        timerBtn.setOnClickListener { showToast("timer clicked") }


        bindCamera()

        return mView
    }

    private fun bindCamera() {
        CameraX.unbindAll()
        // The view that displays the preview
        val textureView: TextureView = mView.findViewById(R.id.texture_view_selfie)

        val aspectRatio = Rational(textureView.width, textureView.height)
        val screen = Size(textureView.width, textureView.height)

        // Preview config for the camera
        val previewConfig = PreviewConfig.Builder()
                .setLensFacing(lensFacing)
                .setTargetAspectRatio(aspectRatio)
                .setTargetResolution(screen)
                .build()

        val preview = Preview(previewConfig)

        // Handles the output data of the camera
        preview.setOnPreviewOutputUpdateListener { previewOutput ->
            // Displays the camera image in our preview view
            textureView.surfaceTexture = previewOutput.surfaceTexture
        }


        // Image capture config which controls the Flash and Lens
        val imageCaptureConfig = ImageCaptureConfig.Builder()
                .setTargetRotation(activity!!.windowManager.defaultDisplay.rotation)
                .setLensFacing(lensFacing)
                .setFlashMode(FlashMode.ON)
                .build()

        imageCapture = ImageCapture(imageCaptureConfig)

        // Bind the camera to the lifecycle
        CameraX.bindToLifecycle(activity as LifecycleOwner, imageCapture, preview)
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }


    private fun initFilters() {
        val adapter = TabAdapter((context as AppCompatActivity).supportFragmentManager,0)
        adapter.addFragment(CameraFilterFragment("PRT "),"Portrait")
        adapter.addFragment(CameraFilterFragment("LND "),"Landscape")
        adapter.addFragment(CameraFilterFragment("STL "),"Style-Leo")

        filterViewPager.adapter = adapter
        filterTab.setupWithViewPager(filterViewPager)
    }
}