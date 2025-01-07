package com.example.floatinglogsdk

import android.annotation.SuppressLint
import android.graphics.PixelFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleService


class FloatingLogImpl : LifecycleService() {


    private lateinit var windowManager: WindowManager
    private lateinit var overlayLogView: View
    private lateinit var logTextView: TextView
    private lateinit var params : WindowManager.LayoutParams
    private var touchX : Float = 0.0f
    private var touchY : Float = 0.0f
    private var viewX : Int = 0
    private var viewY : Int = 0
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
        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            type!!,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        )
        initialSet()
        windowManager.addView(overlayLogView, params)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initialSet() {
        val bt: ImageView = overlayLogView.findViewById(R.id.ib_close)
        bt.setOnClickListener {
            stopSelf()
        }
        overlayLogView.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN ->{
                    touchX = event.rawX
                    touchY = event.rawY
                    viewX = params.x
                    viewY = params.x
                }
                MotionEvent.ACTION_MOVE ->{
                    val x = (event.rawX- touchX).toInt()
                    val y = (event.rawY - touchY).toInt()

                    params.x =viewX+x
                    params.y =viewY+y

                    windowManager.updateViewLayout(overlayLogView, params)
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
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