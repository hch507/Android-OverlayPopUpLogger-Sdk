package com.example.overlaypopuplogger.core


import android.annotation.SuppressLint
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.overlaypopuplogger.R
import com.example.overlaypopuplogger.core.recycler.OverlayLoggerAdapter
import com.example.overlaypopuplogger.model.OverlayLogItem
import java.util.UUID

class OverlayPopUpLogger : LifecycleService() {

    private lateinit var windowManager: WindowManager
    private lateinit var overlayLogView: View
    private lateinit var logTextView: TextView
    private lateinit var params: WindowManager.LayoutParams
    private lateinit var recyclerView: RecyclerView
    private val logAdapter: OverlayLoggerAdapter by lazy { OverlayLoggerAdapter() }

    private var touchX: Float = 0.0f
    private var touchY: Float = 0.0f
    private var viewX: Int = 0
    private var viewY: Int = 0
    private var type: Int? = null
    private var isFullScreen = false
    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        createFloatingView()
    }

    private fun createFloatingView() {
        val inflater = LayoutInflater.from(this)
        overlayLogView = inflater.inflate(R.layout.floating_log_view, null)
        recyclerView = overlayLogView.findViewById(R.id.rv_overlay_view)

        setType()
        setRecyclerView()
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
        val btClose: ImageView = overlayLogView.findViewById(R.id.iv_close)
        val btFullScreen: ImageView = overlayLogView.findViewById(R.id.iv_open_full_screen)
        val layoutLogScreen: LinearLayout = overlayLogView.findViewById(R.id.layout_log_screen)

        btClose.setOnClickListener {
            stopSelf()
        }
        btFullScreen.setOnClickListener {

            if (isFullScreen) {
                layoutLogScreen.visibility = View.GONE
                isFullScreen = false
            } else {
                layoutLogScreen.visibility = View.VISIBLE
                isFullScreen = true
            }
        }
        overlayLogView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    touchX = event.rawX
                    touchY = event.rawY
                    viewX = params.x
                    viewY = params.x
                }

                MotionEvent.ACTION_MOVE -> {
                    val x = (event.rawX - touchX).toInt()
                    val y = (event.rawY - touchY).toInt()

                    params.x = viewX + x
                    params.y = viewY + y

                    windowManager.updateViewLayout(overlayLogView, params)
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }

    }

    private fun setRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL,
                false
            )
            adapter = logAdapter
        }
    }

    private fun setType() {
        type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else
            WindowManager.LayoutParams.TYPE_PHONE

    }

    fun loggerD(tag: String, msg: String) {

        val logItem = OverlayLogItem(UUID.randomUUID().toString(), tag, msg)
        logAdapter.submitList(logAdapter.currentList + logItem)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::overlayLogView.isInitialized) {
            windowManager.removeView(overlayLogView)
        }
    }


}