package com.github.musicode.mta

import android.content.pm.PackageManager
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
    fun start(appKey: String, isDebug: Boolean, channelMetaName: String) {

        currentActivity?.application?.let {

            if (isDebug) {
                StatConfig.setDebugEnable(true)
            }

            var channelName = "default"

            try {
                val appInfo = it.packageManager.getApplicationInfo(it.packageName, PackageManager.GET_META_DATA)
                channelName = appInfo.metaData.get(channelMetaName) as String
            }
            catch (err: PackageManager.NameNotFoundException) {

            }

            StatConfig.setAppKey(it, appKey)
            StatConfig.setInstallChannel(it, channelName)

            StatService.registerActivityLifecycleCallbacks(it)
        }

    }

}