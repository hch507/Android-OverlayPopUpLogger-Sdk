package com.example.floatinglogsdk

import android.graphics.PixelFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleService


class FloatingLogImpl : LifecycleService() {


    private lateinit var windowManager: WindowManager
    private lateinit var overlayLogView: View
    private lateinit var logTextView: TextView
    private var type: Int? = null
    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        createFloatingView()
    }

    private fun createFloatingView() {
        val inflater = LayoutInflater.from(this)
        overlayLogView = inflater.inflate(R.layout.floating_log_view, null)

        setType()
        // LayoutParams 설정
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            type!!,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        )
        initialSet()
        windowManager.addView(overlayLogView, params)
    }

    private fun initialSet() {
        val bt: ImageView = overlayLogView.findViewById(R.id.ib_close)
        bt.setOnClickListener {
            stopSelf()
        }

    }

    private fun setType() {
        type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else
            WindowManager.LayoutParams.TYPE_PHONE

    }

    override fun onDestroy() {
        super.onDestroy()
        if (::overlayLogView.isInitialized) {
            windowManager.removeView(overlayLogView)
        }
    }
}