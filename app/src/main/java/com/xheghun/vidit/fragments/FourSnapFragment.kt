package com.xheghun.vidit.fragments


import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xheghun.vidit.EditActivity
import com.xheghun.vidit.fragments.GalleryFragment
import com.talkgap.R
import com.xheghun.vidit.adapter.ImageFrameAdapter
import com.xheghun.vidit.classes.Animate
import com.xheghun.vidit.classes.FFmpegSingleton
import com.xheghun.vidit.classes.GenericExtFilter
import java.io.File

class FourSnapFragment : Fragment() {
    private var lensFacing = CameraX.LensFacing.BACK
    private var imageCapture: ImageCapture? = null
    private lateinit var mView: View
    var x = 1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for context!! fragment
        mView = inflater.inflate(R.layout.fragment_four_snaps, container, false)

        //delete all files in internal storage
        val snapButton: ImageButton = mView.findViewById(R.id.snap_btn)
        val flashToggle: ImageButton = mView.findViewById(R.id.flash_toggle_btn)
        val switchCamButton: ImageButton = mView.findViewById(R.id.switch_cam_btn)
        val nextBtn = mView.findViewById<FloatingActionButton>(R.id.next_btn)
        val imageRC = mView.findViewById<RecyclerView>(R.id.image_frame_recycler_view)
        val clearBtn = mView.findViewById<TextView>(R.id.clear_btn)

        bindCamera()

        val txtPath = "${Environment.getExternalStorageDirectory()}/image_path.txt"
        val txtFile = File(txtPath)

        if (txtFile.exists()) {
            txtFile.delete()
        }

        val images = ArrayList<String>()
        snapButton.setOnClickListener {
            // Toast.makeText(context!!,"$filesDir",Toast.LENGTH_LONG).show()
            val filename = "img_${System.currentTimeMillis()}.jpg"
            x++

            val dest = File(context!!.filesDir, filename)
            imageCapture?.takePicture(dest,
                    object : ImageCapture.OnImageSavedListener {
                        override fun onError(error: ImageCapture.UseCaseError,
                                             message: String, exc: Throwable?) {
                            Log.e("Image", error.toString())
                        }

                        override fun onImageSaved(file: File) {
                            Log.v("Image", "Successfully saved image")
                            txtFile.appendText("file '${dest.absolutePath}'\n")

                            val image = file.absolutePath
                            //Toast.makeText(context!!,file.absolutePath,Toast.LENGTH_LONG).show()
                            if (images.size > 15) {
                                Toast.makeText(context!!, "Maximum Of 16 Photos Allowed", Toast.LENGTH_SHORT).show()
                            } else {
                                images.add(image)
                            }


                            //Toast.makeText(context!!,"photo path: ${file.path},\n current image: $image",Toast.LENGTH_LONG).show()

                            val layoutManager = LinearLayoutManager(context!!)
                            layoutManager.orientation = RecyclerView.HORIZONTAL
                            Animate.fadeInAnimation(imageRC, context!!)
                            imageRC.layoutManager = layoutManager
                            imageRC.adapter = ImageFrameAdapter(images, context!!, ImageFrameAdapter.ImageFrameCancelClick { path, _ ->
                                run {
                                    images.remove(path)
                                    imageRC.adapter!!.notifyDataSetChanged()
                                }
                            })

                            if (images.size in 4..16) {
                                Animate.fadeInAnimation(nextBtn, context!!)
                                nextBtn.setOnClickListener {
                                    val array = arrayOfNulls<String>(images.size)


                                    //convert Images to Video
                                    val destinationFile = File(context!!.filesDir, "video.mp4")
                                    if (destinationFile.exists()) {
                                        destinationFile.delete()
                                    }


                                    Toast.makeText(context!!, txtPath, Toast.LENGTH_LONG).show()
                                    val cmd = arrayOf("-y", "-f", "concat", "-r", "1/5", "-safe", "0", "-i", txtFile.absolutePath, "-preset", "ultrafast", "-vsync", "vfr", "-vf", "scale=1280:720:force_original_aspect_ratio=decrease,pad=1280:720:(ow-iw)/2:(oh-ih)/2,fps=30,format=yuv420p", destinationFile.absolutePath)

                                    //val cmd = arrayOf("-y", "-r", "1/5","-f", "concat", "-safe", "0", "-i", txtFile.absolutePath , "-c:v", "libx264", "-vf", "fps=25,format=yuv420p", destinationFile.absolutePath)


                                    val intent = Intent(context, EditActivity::class.java)
                                    intent.putExtra("video_path", destinationFile.absolutePath)
                                    FFmpegSingleton.executeCmD(context, cmd, intent)
                                }

                            } else {
                                Animate.fadeOutAnimation(nextBtn, context)
                            }

                            if (images.size > 0) {
                                clearBtn.visibility = View.VISIBLE
                                clearBtn.setOnClickListener {
                                    images.clear()
                                    imageRC.adapter!!.notifyDataSetChanged()

                                    Animate.fadeOutAnimation(imageRC, context)
                                    Animate.fadeOutAnimationIV(clearBtn, context)
                                    Animate.fadeOutAnimation(nextBtn, context)
                                }
                            }
                            // Toast.makeText(context!!,"Photo Taken",Toast.LENGTH_SHORT).show()

                        }
                    }
            )
        }
        if (images.size < 3) {
            Animate.fadeOutAnimation(nextBtn, context)
        }

        flashToggle.setOnClickListener {
            val flashMode = imageCapture?.flashMode
            if (flashMode == FlashMode.ON) {
                imageCapture?.flashMode = FlashMode.OFF
                Toast.makeText(context!!, "Flash Off", Toast.LENGTH_SHORT).show()
            } else {
                imageCapture?.flashMode = FlashMode.ON
                Toast.makeText(context!!, "Flash On", Toast.LENGTH_SHORT).show()
            }
        }

        switchCamButton.setOnClickListener {
            lensFacing = if (CameraX.LensFacing.FRONT == lensFacing) {
                CameraX.LensFacing.BACK
            } else {
                CameraX.LensFacing.FRONT
            }
            bindCamera()
            Toast.makeText(context!!, "Switching Cam", Toast.LENGTH_SHORT).show()

        }

        return mView
    }

    private fun bindCamera() {
        CameraX.unbindAll()

        // The view that displays the preview
        val textureView: TextureView = mView.findViewById(R.id.texture_view)

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

    override fun onDestroy() {
        super.onDestroy()
        deleteFiles()
    }

    private fun deleteFiles(file: String) {
        val fileList = File("$file/")

        if (fileList.isDirectory) {
            val filenames = fileList.listFiles()

            for (mFile in filenames!!) {
                mFile.delete()
            }
        } else {
            fileList.delete()
        }
    }

    private fun deleteFiles() {
        val dir = File("${context!!.filesDir}/")
        if (!dir.exists()) {
            val files = dir.listFiles((GenericExtFilter(".jpg")))
            for (file in files!!) {
                if (!file.isDirectory) {
                    val result = file.delete()
                    Log.d("TAG", "Deleted: $result")
                }
            }
        }
    }
}