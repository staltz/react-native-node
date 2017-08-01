const { NativeModules } = require("react-native");

export const RNNode = {
  start(args) {
    NativeModules.RNNode.start(Array.isArray(args) ? args : []);
  },

  stop() {
    NativeModules.RNNode.stop();
  }
};

export default RNNode;
