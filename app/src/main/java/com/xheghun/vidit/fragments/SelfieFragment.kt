package com.xheghun.vidit.fragments


import android.os.Bundle
import android.util.Rational
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.*
import androidx.lifecycle.LifecycleOwner
import com.talkgap.R

/**
 * A simple [Fragment] subclass.
 */
class SelfieFragment : Fragment() {

    private var lensFacing = CameraX.LensFacing.BACK
    private var imageCapture: ImageCapture? = null
    private lateinit var mView: View
    var x = 1
    private var flashOn: Boolean = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for context!! fragment
        mView = inflater.inflate(R.layout.fragment_selfie, container, false)

        //delete all files in internal storage
        val snapButton: ImageButton = mView.findViewById(R.id.snap_btn)
        val flashToggle: ImageButton = mView.findViewById(R.id.flash_toggle_btn)
        val switchCamButton: ImageButton = mView.findViewById(R.id.switch_cam_btn)
        val close = mView.findViewById<ImageButton>(R.id.back_btn)
        val timerBtn: ImageButton = mView.findViewById(R.id.timer_btn)
        val speedBtn : ImageButton = mView.findViewById(R.id.speed_btn)
        val filterBtn: ImageButton = mView.findViewById(R.id.filter_btn)

        snapButton.setOnClickListener {
            showToast("Snap Clicked")
        }

        flashToggle.setOnClickListener {
            if (flashOn) {
                showToast("flash on")
            } else {
                showToast("flash off")
            }
            flashOn = !flashOn
        }

        switchCamButton.setOnClickListener { showToast("switching lens") }

        close.setOnClickListener {
            showToast("Close")
        }

        filterBtn.setOnClickListener { showToast("filter clicked") }

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

}
