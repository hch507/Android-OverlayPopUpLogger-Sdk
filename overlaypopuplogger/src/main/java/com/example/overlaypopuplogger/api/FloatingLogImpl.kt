package com.example.overlaypopuplogger.api

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.example.overlaypopuplogger.core.OverlayPopUpLogger

class FloatingLogImpl : FloatingLog {
    private var overlayPopUpLogger: OverlayPopUpLogger? = null
    private var cnt = 1
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as OverlayPopUpLogger.LocalBinder
            overlayPopUpLogger = binder.getService()

            overlayPopUpLogger?.loggerD(tag = "tag", msg = "content")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            overlayPopUpLogger = null
        }
    }
    override fun start(context: Context) {
        Log.d("test_logd", "start: ")

        val intent = Intent(context, OverlayPopUpLogger::class.java)
        context.startService(intent)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE) // 바인딩


    }


    override fun d(tag: String, content: String) {
        cnt++
        overlayPopUpLogger?.loggerD(tag = tag+cnt, msg = content)
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