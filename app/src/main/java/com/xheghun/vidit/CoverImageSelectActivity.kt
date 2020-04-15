package com.xheghun.vidit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.TokenWatcher
import androidx.appcompat.widget.Toolbar
import com.talkgap.R

class CoverImageSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cover_image_select)

        val toolbar: Toolbar = findViewById(R.id.toolbar)

        toolbar.setNavigationOnClickListener { finish() }
        toolbar.setOnMenuItemClickListener {
            finish()
            false
        }
    }
}
