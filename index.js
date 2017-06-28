const { NativeModules } = require("react-native");

export const RNNode = {
  start() {
    NativeModules.RNNode.start();
  },

  stop() {
    // TODO
  }
};

export default RNNode;
