package com.example.floatinglogsdk

import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings

import androidx.appcompat.app.AppCompatActivity
import com.example.floatinglogsdk.databinding.ActivityMainBinding
import com.example.overlaypopuplogger.api.FloatingLog
import com.example.overlaypopuplogger.api.FloatingLogImpl
import com.example.overlaypopuplogger.core.OverlayPopUpLogger

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityMainBinding
    private lateinit var floatLogger : FloatingLog
    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        initialSet()
    }

    private fun initialSet() {
        floatLogger= FloatingLogImpl()
        mBinding.btStart.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                // Overlay 권한 요청
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, 1234)
            } else {
                // 권한이 있으면 Service 시작
                floatLogger.start(this)
            }
            floatLogger.start(this)
        }
        mBinding.apply {
            btLogd.setOnClickListener {
                floatLogger.d("test_log","click Logd")
            }
            btLogi.setOnClickListener {
                floatLogger.i("test_log","click Logi")
            }
            btLoge.setOnClickListener {
                floatLogger.e("test_log", "click Loge")
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        if (requestCode == 1234) {
            floatLogger.start(this)
        }
    }
}