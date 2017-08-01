import React, { Component } from "react";
import { AppRegistry, StyleSheet, Text, View } from "react-native";
import RNNode from "react-native-node";

export default class Example extends Component {
  constructor() {
    super();
    this.state = { msg: "..." };
  }

  componentDidMount() {
    RNNode.start();
  }

  sendRequest() {
    fetch("http://localhost:5000").then(res => res.json()).then(json => {
      this.setState(json);
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.text}>
          {this.state.msg}
        </Text>
        <Text style={styles.button} onPress={() => this.sendRequest()}>
          Tap to make a request
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#F5FCFF"
  },
  text: {
    fontSize: 20,
    textAlign: "center",
    margin: 10
  },
  button: {
    color: "white",
    fontSize: 20,
    marginTop: 40,
    backgroundColor: "#5c7cfa",
    borderRadius: 4,
    padding: 8,
    textAlign: "center"
  }
});

AppRegistry.registerComponent("Example", () => Example);
