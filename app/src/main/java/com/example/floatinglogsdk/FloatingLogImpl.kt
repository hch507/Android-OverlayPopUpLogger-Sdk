package com.example.floatinglogsdk

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.lifecycle.LifecycleService


class FloatingLogImpl: LifecycleService() {


    private lateinit var windowManager: WindowManager
    private lateinit var overlayLogView : View
    private lateinit var logTextView: TextView
    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        createFloatingView()
    }

    private fun createFloatingView() {
        val inflater = LayoutInflater.from(this)
        overlayLogView= inflater.inflate(R.layout.floating_log_view,null)


    }

}