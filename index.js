const { NativeModules } = require("react-native");

export const RNNode = {
  start() {
    NativeModules.RNNode.start();
  },

  stop() {
    NativeModules.RNNode.stop();
  }
};

export default RNNode;
