# Example

A simple express.js app running in a background node.js process, which the React Native app can talk to through HTTP requests to localhost.

## Install

```
yarn
```

(note: after this you should also run `react-native link react-native-node`, but in this example we already ran that for you)

## Build the background process

Run `npm run build` or follow these steps manually:

The node.js project is in the directory `/background`, you need to install its `node_modules` **separately** from the `node_modules` for your React Native project:

```
cd background
yarn
cd ..
```

```
react-native-node insert ./background
```

(or `./node_modules/.bin/react-native-node insert ./background`)

## Run

```
react-native run-android
```
