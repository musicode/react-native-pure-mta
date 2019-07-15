package com.github.musicode.mta

import android.content.pm.PackageManager
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.tencent.stat.StatConfig
import com.tencent.stat.StatService
import com.tencent.stat.StatMultiAccount


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

    @ReactMethod
    fun addPhoneAccount(phone: String) {

        val account = StatMultiAccount(
                StatMultiAccount.AccountType.PHONE_NO, phone)

        val time = System.currentTimeMillis() / 1000

        // 登陆时间，单秒为秒
        account.lastTimeSec = time

        // 过期时间，单位为秒，这里先设置为 1 个月
        account.expireTimeSec = time + 30 * 24 * 60 * 60

        StatService.reportMultiAccount(currentActivity, account)


    }

    @ReactMethod
    fun removePhoneAccount() {

        StatService.removeMultiAccount(currentActivity, StatMultiAccount.AccountType.PHONE_NO)

    }

    @ReactMethod
    fun addWechatAccount(openId: String) {

        val account = StatMultiAccount(
                StatMultiAccount.AccountType.OPEN_WEIXIN, openId)

        val time = System.currentTimeMillis() / 1000

        // 登陆时间，单位为秒
        account.lastTimeSec = time

        // 过期时间，单位为秒，这里先设置为 1 个月
        account.expireTimeSec = time + 30 * 24 * 60 * 60

        StatService.reportMultiAccount(currentActivity, account)


    }

    @ReactMethod
    fun removeWechatAccount() {

        StatService.removeMultiAccount(currentActivity, StatMultiAccount.AccountType.OPEN_WEIXIN)

    }

}