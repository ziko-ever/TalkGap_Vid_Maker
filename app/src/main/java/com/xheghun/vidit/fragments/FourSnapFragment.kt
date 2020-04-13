package com.xheghun.vidit.fragments


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Rational
import android.util.Size
import android.view.*
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.talkgap.R
import com.xheghun.vidit.AddMusicActivity
import com.xheghun.vidit.SelfieVideoEditActivity
import com.xheghun.vidit.VideoEditActivity
import com.xheghun.vidit.XheghunViewPager
import com.xheghun.vidit.adapter.CamFilterAdapter
import com.xheghun.vidit.adapter.TabAdapter
import com.xheghun.vidit.classes.Animate
import kotlinx.android.synthetic.main.fragment_four_snaps.*
import kotlinx.android.synthetic.main.snap_layout.view.*
import java.util.concurrent.atomic.AtomicBoolean

class FourSnapFragment : Fragment() {
    private val ADD_MUSIC_REQUEST_CODE = 9492
    private var lensFacing = CameraX.LensFacing.BACK
    private var imageCapture: ImageCapture? = null
    private lateinit var mView: View
    private var flashOn: Boolean = false
    private lateinit var filterLayout: ViewGroup
    private lateinit var filterTab: TabLayout
    private lateinit var filterViewPager: XheghunViewPager

    private lateinit var effectsLayout: ViewGroup
    private lateinit var effectsTab: TabLayout
    private lateinit var effectsViewPager: XheghunViewPager

    private lateinit var backBtn: View
    private lateinit var camActionsLayout: ViewGroup
    private lateinit var snapButton: View
    private lateinit var flashToggle: ImageButton
    private lateinit var switchCamButton: ImageButton
    private lateinit var close:View
    private lateinit var timerBtn:View
    private lateinit var speedBtn : ImageButton
    private lateinit var filterBtn: ImageButton
    private lateinit var textureView: TextureView
    private lateinit var effectsBtn: View
    private lateinit var mActivity: FragmentManager
    private lateinit var nextFab: FloatingActionButton
    private lateinit var cancelButton: MaterialButton

    private lateinit var xActivity: LifecycleOwner

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for context!! fragment
        // The view that displays the preview
        mView = inflater.inflate(R.layout.fragment_four_snaps, container, false)

        mActivity =  (super.getActivity()!!.supportFragmentManager)
        xActivity = (super.getActivity() as LifecycleOwner)


        textureView = mView.findViewById(R.id.texture_view)

        filterTab = mView.findViewById(R.id.filter_tab_4)
        filterLayout = mView.findViewById(R.id.filter_layout_4)
        filterViewPager = mView.findViewById(R.id.filter_viewpager_4)

        effectsTab = mView.findViewById(R.id.effects_tab_4)
        effectsLayout = mView.findViewById(R.id.effects_layout_4)
        effectsViewPager = mView.findViewById(R.id.effects_viewpager_4)

        cancelButton = mView.findViewById(R.id.cancel_snap_btn)
        nextFab = mView.findViewById(R.id.next_fab)
        effectsBtn = mView.findViewById(R.id.effects_btn)
        backBtn = mView.findViewById<ImageButton>(R.id.back_btn)
        camActionsLayout = mView.findViewById(R.id.cam_actions_layout)
        snapButton = mView.findViewById(R.id.snap_btn)
        flashToggle = mView.findViewById(R.id.flash_toggle_btn)
        switchCamButton = mView.findViewById(R.id.switch_cam_btn)
        close = mView.findViewById<ImageButton>(R.id.close_btn)
        timerBtn = mView.findViewById(R.id.timer_btn)
        speedBtn = mView.findViewById(R.id.speed_btn)
        filterBtn = mView.findViewById(R.id.filter_btn)

        initFilters()
        initEffects()

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

        cancelButton.setOnClickListener {
            nextFab.hide()
            cancelButton.visibility = View.INVISIBLE
        }

        nextFab.setOnClickListener {
            startActivity(Intent(context,VideoEditActivity::class.java))
        }

        backBtn.setOnClickListener {
            showCamActions()
        }

        textureView.setOnClickListener {
            showCamActions()
        }

        snapButton.setOnClickListener {
            nextFab.show()
            cancelButton.visibility = View.VISIBLE
        }

        filterBtn.setOnClickListener {
            if (filterLayout.visibility == View.INVISIBLE || filterLayout.visibility == View.GONE) {
                backBtn.visibility = View.VISIBLE
                Animate.slideInUp(filterLayout,context)
                Animate.slideOutLeft(close,context)
                Animate.slideOutRight(camActionsLayout,context)
                Animate.fadeOutAnimation(snap_btn_lyt,context)
            } else {
                Animate.slideOutDown(filterLayout,context)
            }
            showToast("filter clicked")
        }

        effectsBtn.setOnClickListener {
            if (effectsLayout.visibility == View.INVISIBLE || effectsLayout.visibility == View.GONE) {
                backBtn.visibility = View.VISIBLE
                Animate.slideInUp(effectsLayout,context)
                Animate.slideOutLeft(close,context)
                Animate.slideOutRight(camActionsLayout,context)
                Animate.fadeOutAnimation(snap_btn_lyt,context)
            } else {
                Animate.slideOutDown(effectsLayout,context)
            }
            showToast("effects clicked")
        }

        speedBtn.setOnClickListener { showToast("speed clicked") }

        timerBtn.setOnClickListener { showToast("timer clicked") }


        bindCamera()

        return mView
    }

    private fun bindCamera() {

        CameraX.unbindAll()

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
        CameraX.bindToLifecycle(xActivity, imageCapture, preview)
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    private fun initFilters() {
        val adapter = TabAdapter(mActivity,0)
        adapter.addFragment(CameraFilterFragment("PRT "),"Portrait")
        adapter.addFragment(CameraFilterFragment("LND "),"Landscape")
        adapter.addFragment(CameraFilterFragment("STL "),"Style-Leo")
        adapter.notifyDataSetChanged()

        filterViewPager.offscreenPageLimit = 3
        filterViewPager.adapter = adapter
        filterTab.setupWithViewPager(filterViewPager)
    }

    private fun initEffects() {
        val adapter = TabAdapter(mActivity,1)
        adapter.addFragment(CamEffectsFragment(),"Free")
        adapter.addFragment(CamEffectsFragment(),"My Effects")
        adapter.addFragment(CamEffectsFragment(),"Favourite")
        adapter.notifyDataSetChanged()

        effectsViewPager.requestFocus()

        effectsViewPager.offscreenPageLimit = 3



        effectsViewPager.adapter = adapter



        effectsTab.setupWithViewPager(effectsViewPager)
    }

    private fun showCamActions() {
        if (camActionsLayout.visibility == View.GONE) {
            Animate.slideInLeft(close, context)
            Animate.slideInRight(camActionsLayout, context)
            Animate.fadeInAnimation(snap_btn_lyt,context)
        }
        backBtn.visibility = View.GONE
        if (filterLayout.visibility == View.VISIBLE) {
            Animate.slideOutDown(filterLayout,context)
        } else if (effectsLayout.visibility == View.VISIBLE) {
            Animate.slideOutDown(effectsLayout,context)
        }
    }

}