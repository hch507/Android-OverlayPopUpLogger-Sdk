package com.example.overlaypopuplogger.api

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.contentValuesOf
import com.example.overlaypopuplogger.core.OverlayPopUpLogger

internal class FloatingLogImpl : FloatingLog {
    private val overlayPopUpLogger : OverlayPopUpLogger? = null
    override fun start(context :Context) {
        val intent = Intent(context, overlayPopUpLogger::class.java)
        context.startService(intent)
    }

    override fun d(tag: String, content: String) {
        Log.d(tag, content)
    }

    override fun e(tag: String, content: String) {
        Log.e(tag, content, )
    }

    override fun i(tag: String, content: String) {
        Log.i(tag, content)
    }

    override fun stop() {

    }
}