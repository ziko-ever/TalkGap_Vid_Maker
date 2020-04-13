package com.xheghun.vidit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.talkgap.R
import kotlinx.android.synthetic.main.activity_upload_video.*

class UploadVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_upload)

        changeCover_btn.setOnClickListener { startActivity(Intent(this,CoverImageSelectActivity::class.java)) }
        toolbar.setNavigationOnClickListener { finish() }

        post_btn.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            val viewGroup = findViewById<ViewGroup>(android.R.id.content)
            val dialogView: View = LayoutInflater.from(this).inflate(R.layout.broadcast_video_dialog, viewGroup, false)
            builder.setView(dialogView)
            val alertDialog: AlertDialog = builder.create()
            dialogView.findViewById<MaterialButton>(R.id.no_btn).setOnClickListener {
                alertDialog.dismiss()
            }
            dialogView.findViewById<MaterialButton>(R.id.broadcast_btn).setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        }

        save_btn.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            val viewGroup = findViewById<ViewGroup>(android.R.id.content)
            val dialogView: View = LayoutInflater.from(this).inflate(R.layout.layout_protect_video_dialog, viewGroup, false)
            builder.setView(dialogView)
            val alertDialog: AlertDialog = builder.create()
            dialogView.findViewById<ImageView>(R.id.close_btn).setOnClickListener {
                alertDialog.dismiss()
            }
            dialogView.findViewById<MaterialButton>(R.id.yes_btn).setOnClickListener {
                alertDialog.dismiss()
                showPurchaseDialog()
            }
            alertDialog.show()
        }
    }

    private fun showPurchaseDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        val dialogView: View = LayoutInflater.from(this).inflate(R.layout.subscribe_with_koz_coin_dialog, viewGroup, false)
        builder.setView(dialogView)
        val alertDialog: AlertDialog = builder.create()
        dialogView.findViewById<ImageView>(R.id.close_btn).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<MaterialButton>(R.id.pay_btn).setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}
