# React Native Node: Bridging Node.js and Mobile Development

## Project Overview

React Native Node is a powerful library that enables running a separate Node.js process alongside a React Native application. It provides a unique solution for developers who need to leverage Node.js functionality within their mobile applications.

### Key Features

- **Isolated Node.js Process**: Runs Node.js as a separate thread/process within a React Native app
- **Cross-Platform Support**: Works on Android platforms
- **Background Execution**: Allows Node.js code to run in the background of mobile applications
- **Flexible Integration**: Enables using Node.js modules and capabilities not typically available in mobile environments

### Primary Purpose

The library solves the challenge of integrating Node.js functionality directly into React Native mobile applications. It creates a bridge that allows developers to:
- Execute Node.js code in a mobile context
- Run background services
- Utilize Node.js-specific libraries and tools within mobile apps
- Extend the capabilities of React Native beyond its standard runtime environment

### Benefits

- Seamless integration of Node.js and React Native
- Minimal performance overhead
- Simplified process of running complex backend logic in mobile apps
- Enhanced flexibility for developers working across web and mobile platforms

## Getting Started, Installation, and Setup

### Prerequisites

- React Native 0.47.0 or higher
- Node.js installed on your system
- For Android: Android SDK and development environment
- For iOS: Xcode and CocoaPods

### Installation

Install the package using npm or yarn:

```bash
npm install react-native-node
# or
yarn add react-native-node
```

### Setup and Configuration

#### Android Setup

1. Open your project's `android/build.gradle` file and ensure the build tools are compatible.
2. Link the library to your React Native project:

```bash
react-native link react-native-node
```

#### iOS Setup

1. Install CocoaPods if not already installed.
2. Link the library to your React Native project:

```bash
react-native link react-native-node
```

### Quick Start

Here's a basic example of how to use react-native-node in your project:

```javascript
import RNNode from 'react-native-node';

// Start a Node.js process in the background
RNNode.start('./background');

// Stop the Node.js process when no longer needed
RNNode.stop();
```

### Development and Build

#### Development

To run the project in development mode:

```bash
# For React Native
yarn start

# Build the background Node.js process
yarn build
```

#### Production Build

For a production build, follow your React Native project's standard build process for Android and iOS.

### Troubleshooting

- Ensure all dependencies are correctly installed
- Check that your Node.js version is compatible with the project
- Verify React Native version meets the minimum requirement (0.47.0)

## API Reference

### Exports

#### `RNNode`

The `RNNode` object provides methods for managing a Node.js runtime within a React Native application.

##### Methods

###### `start(args?: string[])`
Starts the Node.js runtime with optional command-line arguments.

- **Parameters**:
  - `args` (optional): An array of strings representing command-line arguments to pass to the Node.js runtime. If not an array, an empty array will be used.
- **Returns**: `void`

**Example**:
```javascript
import RNNode from 'react-native-node';

// Start Node.js with no arguments
RNNode.start();

// Start Node.js with specific arguments
RNNode.start(['-e', 'console.log("Hello from Node.js")']);
```

###### `stop()`
Stops the Node.js runtime.

- **Parameters**: None
- **Returns**: `void`

**Example**:
```javascript
import RNNode from 'react-native-node';

RNNode.stop();
```

#### Default Export

The library also exports `RNNode` as the default export, which can be imported directly:

```javascript
import RNNode from 'react-native-node';
```

## Technologies Used

### Programming Languages
- JavaScript
- Node.js

### Frameworks and Libraries
- React Native
- Cheerio
- Noderify

### Build and Development Tools
- Babel
- ESLint
- Yarn
- npm

### Platform-Specific Technologies
- Android (Android SDK, Java)

### Key Dependencies
- mkdirp (directory creation utility)
- path (file path utilities)
- tar (file archiving)
- yargs (command-line argument parsing)

### Development Environment
- React Native CLI
- Gradle (for Android build)

### Compatibility
- Supports React Native 0.47.0 and above

## Additional Notes

### Deprecation Notice

This library is officially deprecated. The recommended alternative is [Node.js Mobile](https://code.janea.systems.com/nodejs-mobile/) by Janea Systems, which offers similar functionality with broader support.

### Debugging and Logging

When troubleshooting, use `adb logcat` with specific tags to gain insights into the Node.js process:

```bash
adb logcat *:S nodejs:V ReactNative:V ReactNativeJS:V
```

Key logging tags include:
- `RNNodeThread`: Process start, termination, and error information
- `RNNodeService`: Debugging for tar/untar and Node binary preparation
- `RNNode`: General library-related logs

### Technical Limitations

#### Platform Support
- **Android**: Fully supported with Node.js v7.1.0
- **iOS**: Currently unsupported due to Apple's restrictions on Just-In-Time compilation

#### Native Package Compatibility
- Native package support is theoretical and depends on individual library implementations
- Most packages without Android-specific native bindings should work
- For complex native packages, compiling directly on an Android device using Termux is recommended

### Security Considerations

The library includes a prebuilt Node.js binary (`bin_node_v710`) compiled using the NodeBase approach. For enhanced security, consider:
- Reviewing the prebuilt binary
- Compiling the Node.js binary yourself if you have specific security requirements

### Project Origin

This library was originally developed to support the [Scuttlebutt](https://www.scuttlebutt.nz/) ecosystem on mobile platforms, specifically for the [mmmmm mobile app](https://github.com/staltz/mmmmm-mobile).

## Contributing

We welcome contributions to the project! Please follow these guidelines when contributing:

### Contribution Process

1. Fork the repository and create your branch from `master`
2. If you've added code that should be tested, add tests
3. Ensure the test suite passes
4. Make sure your code passes the linting checks
5. Issue a pull request with a clear description of your changes

### Code Style

- Use ESLint for code style enforcement
- Follow the existing code formatting in the project
- Write clear, concise comments for complex logic

### Development Setup

- The project requires React Native version 0.47.0 or higher
- Use `npm` or `yarn` for dependency management
- Run `npm install` or `yarn install` to set up the development environment

### Reporting Issues

- Use GitHub Issues to report bugs or suggest features
- Provide a clear and detailed description
- Include steps to reproduce the issue if applicable
- Mention the version of react-native-node and React Native you are using

### Notes

- The project is currently **DEPRECATED**
- Contributions are welcome, but major feature additions may not be merged
- Consider using [Node.js Mobile](https://code.janeasystems.com/nodejs-mobile/) for new projects

### License

Contributions will be accepted under the MIT License, which is the current license of the project.

## License

This project is licensed under the MIT License. 

#### Licensing Terms

The MIT License is a permissive free software license that allows users to:
- Use the software commercially
- Modify the software
- Distribute the software
- Sublicense the software
- Use the software privately

#### Copyright

Copyright (c) 2017-present André Staltz (staltz.com)

#### Full License

For the complete license text, see the [LICENSE](LICENSE) file in the repository.