package com.example.overlaypopuplogger.api

import android.content.Context

interface FloatingLog {

    fun start(context : Context)

    fun d(tag : String, content : String)

    fun e(tag : String, content : String)

    fun i(tag : String, content : String)

    fun stop()

}