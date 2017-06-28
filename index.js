var { NativeModules } = require("react-native");
var RNNode = NativeModules.RNNode;

exports.start = function start() {
  RNNode.start();
};

