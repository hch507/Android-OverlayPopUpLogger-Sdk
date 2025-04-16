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
    private var context : Context? =null
    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as OverlayPopUpLogger.LocalBinder
            overlayPopUpLogger = binder.getService()
            overlayPopUpLogger!!.setController(this@FloatingLogImpl)

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            overlayPopUpLogger = null
        }
    }
    override fun start(context: Context) {
        this.context =context
        Log.d("test_logd", "start: ")

        val intent = Intent(context, OverlayPopUpLogger::class.java)
        context.startService(intent)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE) // 바인딩

    }


    override fun d(tag: String, content: String) {
        overlayPopUpLogger?.loggerD(tag = tag, msg = content)
        Log.d(tag, content)
    }

    override fun e(tag: String, content: String) {
//        overlayPopUpLogger?.logg
        overlayPopUpLogger?.loggerE(tag = tag, msg = content)
        Log.e(tag, content, )
    }

    override fun i(tag: String, content: String) {
        overlayPopUpLogger?.loggerI(tag = tag, msg = content)
        Log.i(tag, content)
    }


    override fun stop() {
        try {
            context?.unbindService(serviceConnection)
            Log.d("FloatingLog", "unbindService 성공")
        } catch (e: Exception) {
            Log.e("FloatingLog", "unbind 실패", e)
        }

        overlayPopUpLogger = null
    }
}