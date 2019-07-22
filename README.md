# react-native-pure-mta

SDK 版本：

* ios: 2.5.3
* android: 3.4.7

## 安装

```
npm i react-native-pure-mta
react-native link react-native-pure-mta
```

## 配置

### iOS

无

### Android

`android/app/build.gradle` 加上这段

```
buildTypes {
    // 测试包
    debug {
        // 这里一般有一些别的配置

        // 重点是这 2 个配置项
        manifestPlaceholders = [
            MTA_APPKEY: "",
            MTA_CHANNEL: ""
        ]
    }
    // 线上包
    release {
        // 这里一般有一些别的配置

        // 重点是这 2 个配置项
        manifestPlaceholders = [
            MTA_APPKEY: "",
            MTA_CHANNEL: ""
        ]
    }
}
```

`android/app/src/main/AndroidManifest.xml` 加上 `android:usesCleartextTraffic`、 `meta-data`、`uses-library`。

```xml
<application
  android:usesCleartextTraffic="true">

  <uses-library
      android:name="org.apache.http.legacy"
      android:required="false" />

</application>
```

## 用法

```js
import mta from 'react-native-pure-mta'

// 启动 SDK
// 第二个参数表示是否输出调试信息
mta.start('appKey', false)

// 如果帐号是手机号，登入登出打点
mta.addPhoneAccount('15811112222')
mta.removePhoneAccount('15811112222')

// 如果帐号是微信 Open ID，登入登出打点
mta.addWechatAccount(openId)
mta.removeWechatAccount(openId)

// 自定义事件
mta.trackCustomEvent('name')
// 如果有数据，可传第二个参数，注意，值必须是字符串
mta.trackCustomEvent('name', { id: 'xxx' })
```

## 声明

保证会及时跟进最新版 SDK，放心使用。

## 打赏

走过路过的都打赏一点吧，给点动力继续更新。

微信

<img src="https://user-images.githubusercontent.com/2732303/44254903-ce6d3f80-a236-11e8-86dd-f6b27a7f94df.png" width="200">

支付宝

<img src="https://user-images.githubusercontent.com/2732303/44254929-e5139680-a236-11e8-95e2-f5a864246f83.png" width="200">