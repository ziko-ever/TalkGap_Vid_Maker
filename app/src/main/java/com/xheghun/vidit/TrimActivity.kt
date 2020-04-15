package com.xheghun.vidit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.talkgap.R
import kotlinx.android.synthetic.main.activity_trim.*

class TrimActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trim)

        toolbar.setNavigationOnClickListener { finish() }
    }
}
