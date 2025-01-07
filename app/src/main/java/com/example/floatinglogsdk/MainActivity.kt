package com.example.floatinglogsdk

import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.floatinglogsdk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        initialSet()
    }

    private fun initialSet() {
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
                val intent = Intent(this, FloatingLogImpl::class.java)
                startService(intent)
            }
            val intent = Intent(this, FloatingLogImpl::class.java)
            startService(intent)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        if (requestCode == 1234) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                // 권한이 승인되면 Service 시작
                val intent = Intent(this, FloatingLogImpl::class.java)
                startService(intent)
            }
        }
    }
}