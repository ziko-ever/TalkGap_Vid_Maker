package com.xheghun.vidit.fragments


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Rational
import android.util.Size
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.talkgap.R
import com.xheghun.vidit.AddMusicActivity
import com.xheghun.vidit.SelfieVideoEditActivity
import com.xheghun.vidit.VideoEditActivity
import com.xheghun.vidit.XheghunViewPager
import com.xheghun.vidit.adapter.TabAdapter
import com.xheghun.vidit.classes.Animate
import kotlinx.android.synthetic.main.snap_layout.view.*
import java.util.concurrent.atomic.AtomicBoolean

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
    private var handler = Handler()
    private var progressStatus: Int = 0
    private var progress: Int = 0
    private var thread: Thread? = null
    private var running = AtomicBoolean()


    private lateinit var effectsLayout: ViewGroup
    private lateinit var effectsTab: TabLayout
    private lateinit var effectsViewPager: XheghunViewPager

    private lateinit var selfieNextBtn: FloatingActionButton
    private lateinit var backBtn: View
    private lateinit var snapLayout: ViewGroup
    private lateinit var addMusicBtn: View
    private lateinit var camActionsLayout: ViewGroup
    private lateinit var snapButton: ImageButton
    private lateinit var flashToggle: ImageButton
    private lateinit var switchCamButton: ImageButton
    private lateinit var close:View
    private lateinit var timerBtn:View
    private lateinit var speedBtn : ImageButton
    private lateinit var filterBtn: ImageButton
    private lateinit var textureView: TextureView
    private lateinit var effectsBtn: View
    private lateinit var mActivity: FragmentManager
    private lateinit var videoProgressBar: ProgressBar

    private lateinit var xActivity: LifecycleOwner

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for context!! fragment
        // The view that displays the preview
        mView = inflater.inflate(R.layout.fragment_selfie, container, false)

        mActivity =  (super.getActivity()!!.supportFragmentManager)
        xActivity = (super.getActivity() as LifecycleOwner)


        textureView = mView.findViewById(R.id.texture_view_selfie)

        filterTab = mView.findViewById(R.id.filter_tab)
        filterLayout = mView.findViewById(R.id.filter_layout)
        filterViewPager = mView.findViewById(R.id.filter_viewpager)
        videoProgressBar = mView.findViewById(R.id.video_progress_bar)

        effectsTab = mView.findViewById(R.id.effects_tab)
        effectsLayout = mView.findViewById(R.id.effects_layout)
        effectsViewPager = mView.findViewById(R.id.effects_viewpager)

        effectsBtn = mView.findViewById(R.id.effects_btn)
        backBtn = mView.findViewById<ImageButton>(R.id.back_btn)
        snapLayout = mView.findViewById(R.id.snap_layout)
        addMusicBtn = mView.findViewById(R.id.add_music_btn)
        camActionsLayout = mView.findViewById(R.id.cam_actions_layout)
        snapButton = mView.findViewById(R.id.snap_btn)
        flashToggle = mView.findViewById(R.id.flash_toggle_btn)
        switchCamButton = mView.findViewById(R.id.switch_cam_btn)
        close = mView.findViewById<ImageButton>(R.id.close_btn)
        timerBtn = mView.findViewById(R.id.timer_btn)
        speedBtn = mView.findViewById(R.id.speed_btn)
        filterBtn = mView.findViewById(R.id.filter_btn)
        selfieNextBtn = mView.findViewById(R.id.selfie_next_btn)

        initFilters()
        initEffects()


        snapButton.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                Thread.sleep(1000)

                mView.effects_btn_layout.visibility = View.GONE
                mView.add_music_btn_layout.visibility = View.GONE
                Animate.slideOutRight(camActionsLayout,context)
                Animate.slideInDown(videoProgressBar,context)
                startProgress()
                Toast.makeText(context,"yo",Toast.LENGTH_SHORT).show()
            } else if (event.action == MotionEvent.ACTION_UP) {
                stopThread()
                Animate.slideOutUp(videoProgressBar,context)
                if (selfieNextBtn.visibility != View.VISIBLE) {
                    mView.add_music_btn_layout.visibility = View.VISIBLE
                    mView.effects_btn_layout.visibility = View.VISIBLE

                } else {
                    selfieNextBtn.hide()
                }
                Toast.makeText(context,"ha",Toast.LENGTH_SHORT).show()

            }
            false
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

        selfieNextBtn.setOnClickListener {
            startActivity(Intent(context,SelfieVideoEditActivity::class.java))
        }

        addMusicBtn.setOnClickListener {
            startActivityForResult(Intent(context,AddMusicActivity::class.java),ADD_MUSIC_REQUEST_CODE)
        }

        backBtn.setOnClickListener {
          showCamActions()
        }

        textureView.setOnClickListener {
          showCamActions()
        }

        filterBtn.setOnClickListener {
            if (filterLayout.visibility == View.INVISIBLE || filterLayout.visibility == View.GONE) {
                backBtn.visibility = View.VISIBLE
                Animate.slideInUp(filterLayout,context)
                Animate.slideOutLeft(close,context)
                Animate.slideOutRight(camActionsLayout,context)
                Animate.slideOutDown(snapLayout,context)
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
                Animate.slideOutDown(snapLayout,context)
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

        filterViewPager.offscreenPageLimit = 3
        filterViewPager.adapter = adapter

        filterTab.setupWithViewPager(filterViewPager)
    }

    private fun initEffects() {
        val adapter = TabAdapter(mActivity,1)
        adapter.addFragment(CamEffectsFragment(8),"Free")
        adapter.addFragment(CamEffectsFragment(6),"My Effects")
        adapter.addFragment(CamEffectsFragment(12),"Favourite")

        effectsViewPager.offscreenPageLimit = 3
        effectsViewPager.adapter = adapter

        effectsTab.setupWithViewPager(effectsViewPager)
    }
    private fun showCamActions() {
        if (camActionsLayout.visibility == View.GONE) {
            Animate.slideInLeft(close, context)
            Animate.slideInRight(camActionsLayout, context)
            Animate.slideInUp(snapLayout, context)
        }
        backBtn.visibility = View.GONE
        if (filterLayout.visibility == View.VISIBLE) {
            Animate.slideOutDown(filterLayout,context)
        } else if (effectsLayout.visibility == View.VISIBLE) {
            Animate.slideOutDown(effectsLayout,context)
        }
    }

    private fun stopThread() {
        running.set(false)
    }


    private fun startProgress() {
        thread = null
        progressStatus = 0
        thread = Thread( Runnable {
            running.set(true)
            if (running.get()) {
                while (progressStatus < 15) {
                    progressStatus += 1
                    handler.post {
                        videoProgressBar.progress = progressStatus
                        if (progressStatus > 5) {
                            selfieNextBtn.show()
                        } else {
                            selfieNextBtn.hide()
                        }
                    }

                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        })

        thread!!.start()

    }
}