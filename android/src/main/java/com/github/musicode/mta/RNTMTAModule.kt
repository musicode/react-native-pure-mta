package com.github.musicode.mta

import com.facebook.react.ReactActivity
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.PermissionAwareActivity
import com.github.herokotlin.permission.Permission
import com.tencent.stat.StatConfig
import com.tencent.stat.StatService
import com.tencent.stat.StatMultiAccount
import java.util.*


class RNTMTAModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "RNTMTA"
    }

    companion object {

        var channel = ""

        private val permission = Permission(8000, listOf(android.Manifest.permission.READ_PHONE_STATE))

        private var permissionListener = { requestCode: Int, permissions: Array<out String>?, grantResults: IntArray? ->
            if (permissions != null && grantResults != null) {
                permission.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
            true
        }

    }

    @ReactMethod
    fun start(appKey: String, isDebug: Boolean) {

        val activity = currentActivity ?: return

        val callback = {

            if (isDebug) {
                StatConfig.setDebugEnable(true)
            }

            StatConfig.setAppKey(activity.application, appKey)

            if (channel.isNotEmpty()) {
                StatConfig.setInstallChannel(activity.application, channel)
            }

            StatService.registerActivityLifecycleCallbacks(activity.application)

        }

        permission.onPermissionsGranted = callback

        permission.onRequestPermissions = { activity, list, requestCode ->
            if (activity is ReactActivity) {
                activity.requestPermissions(list, requestCode, permissionListener)
            }
            else if (activity is PermissionAwareActivity) {
                (activity as PermissionAwareActivity).requestPermissions(list, requestCode, permissionListener)
            }
        }

        permission.requestPermissions(activity, callback)

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

    @ReactMethod
    fun trackCustomEvent(name: String, options: ReadableMap) {

        val prop = Properties()
        val iterator = options.keySetIterator()

        while (iterator.hasNextKey()) {
            val key = iterator.nextKey()
            prop.setProperty(key, options.getString(key))
        }

        StatService.trackCustomKVEvent(currentActivity, name, prop)

    }

}