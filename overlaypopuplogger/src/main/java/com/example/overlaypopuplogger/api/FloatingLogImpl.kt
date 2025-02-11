package com.example.overlaypopuplogger.api

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.contentValuesOf
import com.example.overlaypopuplogger.core.OverlayPopUpLogger

class FloatingLogImpl : FloatingLog {
    private var overlayPopUpLogger : OverlayPopUpLogger? = null
    override fun start(context :Context) {
        overlayPopUpLogger = OverlayPopUpLogger()
        val intent = Intent(context, OverlayPopUpLogger::class.java)
        context.startService(intent)
    }

    override fun d(tag: String, content: String) {
        overlayPopUpLogger?.loggerD()
        Log.d(tag, content)
    }

    override fun e(tag: String, content: String) {
        if (overlayPopUpLogger!=null){

        }
        Log.e(tag, content, )
    }

    override fun i(tag: String, content: String) {
        if (overlayPopUpLogger!=null){

        }
        Log.i(tag, content)
    }

    override fun stop() {

    }
}