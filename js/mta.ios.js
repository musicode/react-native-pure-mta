
import { NativeModules } from 'react-native'

const { RNTMTA } = NativeModules

export default {

  /**
   * 启动 SDK
   *
   * @param {string} appKey
   * @param {boolean} isDebug
   */
  start(appKey, isDebug) {
    RNTMTA.start(appKey, isDebug)
  }

}
