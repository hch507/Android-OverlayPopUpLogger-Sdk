package com.example.overlaypopuplogger.api
import com.example.overlaypopuplogger.internal.core.FloatingLogImpl

object FloatingLogSdk {
    fun create(): FloatingLog {
        return FloatingLogImpl()
    }
}