# React Native Node

*Run a **real** Node.js process in the background, behind a React Native app.*

Using this package you can: run `http` servers in Android, use Node streams, interface with the filesystem, off load some heavy processing out of the JS thread in React Native, and more! Running the real Node.js in Android, you can do everything that Node.js on desktop can.

- (only Android supported, for now. iOS support may come if [this project](http://www.janeasystems.com/blog/node-js-meets-ios/) evolves)
- (check also [node-on-mobile](https://github.com/node-on-mobile/node-on-android) if you're not using React Native)

[Example app](./example)

![screenshot.png](./screenshot.png)

## Install

```
npm install --save react-native-node
```

## Usage

1. Develop the background Node.js project under some directory
    - e.g. `./background`

2. In your React Native JavaScript source files, spawn the background process:

```diff
+import RNNode from "react-native-node";

 class MyApp extends Component {
   // ...
   componentDidMount() {
+    RNNode.start();
+    // or specify arguments to the process:
+    RNNode.start(['--color', 'red', '--port', '3000'])
   }

   componentWillUnmount() {
+    RNNode.stop();
   }
   // ...
 }
```

3. Bundle and insert the background application into the mobile app using the command

```
./node_modules/.bin/react-native-node insert ./background
```

- Compresses your background app into `./android/app/src/main/res/raw/rnnodebundle`
- Updates `AndroidManifest.xml` to include a Service class

4. (Re)build the mobile app

```
react-native run-android
```

### Tip

If you want to bundle and insert the background app always before building the mobile app, make a `prestart` package.json script (assuming you use the `start` script):

```diff
 "scripts": {
+  "prestart: "react-native-node insert ./background",
   "start": "node node_modules/react-native/local-cli/cli.js start",
```