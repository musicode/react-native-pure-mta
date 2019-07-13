
import { NativeModules } from 'react-native'

const { RNTMTA } = NativeModules

export default {

  /**
   * 启动 SDK
   *
   * @param {string} appKey
   * @param {boolean} isDebug
   * @param {string} channelMetaName
   */
  start(appKey, isDebug, channelMetaName) {
    RNTMTA.start(appKey, isDebug, channelMetaName)
  }

}
