package com.github.musicode.mta

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.tencent.stat.StatConfig
import com.tencent.stat.StatService

class RNTMTAModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "RNTMTA"
    }

    @ReactMethod
    fun start(appKey: String, isDebug: Boolean) {

        if (isDebug) {
            StatConfig.setDebugEnable(true)
        }

        currentActivity?.application?.let {
            StatService.registerActivityLifecycleCallbacks(it)
        }

    }

}