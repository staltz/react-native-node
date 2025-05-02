# React Native Node: Seamless Node.js Background Processing in React Native Android Apps

## Project Overview

React Native Node is a powerful library that enables running a full Node.js process as a background service within a React Native Android application. It provides a unique solution for developers who need to leverage Node.js capabilities directly in their mobile apps.

### Key Features

- **Full Node.js Integration**: Run a real Node.js process behind a React Native app, allowing access to the full Node.js ecosystem
- **Background Processing**: Offload heavy computational tasks from the main JavaScript thread
- **Android Support**: Enables running Node.js v7.1.0 with V8 on Android devices
- **Flexible Use Cases**: 
  - Run HTTP servers
  - Use Node streams
  - Interact with the filesystem
  - Perform background processing

### Use Cases

Ideal for applications that require:
- Backend services within a mobile app
- Complex data processing
- Filesystem interactions
- Networking operations that benefit from Node.js capabilities

### Important Considerations

- **Deprecation Notice**: The library is no longer actively maintained
- **Platform Support**: Currently supports Android only
- **Recommended Alternative**: [Node.js Mobile](https://code.janeasystems.com/nodejs-mobile/) by Janea Systems

### Technical Approach

The library includes a precompiled Node.js binary (v7.1.0) that runs as a separate process, allowing full Node.js functionality within a React Native mobile application.

## Getting Started, Installation, and Setup

### Prerequisites

- React Native version 0.47.0 or higher
- Node.js for development
- Android device or emulator (iOS not supported)

### Installation

Install the package using npm:

```bash
npm install --save react-native-node
```

Link the native module:

```bash
react-native link react-native-node
```

### Quick Start

1. Create a background Node.js project (e.g., in a `./background` directory)

2. In your React Native app, import and use RNNode:

```javascript
import RNNode from "react-native-node";

class MyApp extends Component {
  componentDidMount() {
    // Start the background Node.js process
    RNNode.start();
    
    // Optionally, start with custom arguments
    RNNode.start(['--color', 'red', '--port', '3000'])
  }

  componentWillUnmount() {
    // Stop the background process when no longer needed
    RNNode.stop();
  }
}
```

3. Bundle the background application:

```bash
./node_modules/.bin/react-native-node insert ./background
```

This command does two things:
- Compresses your background app into `./android/app/src/main/res/raw/rnnodebundle`
- Updates `AndroidManifest.xml` to include a Service class

4. Build and run the mobile app:

```bash
react-native run-android
```

### Development Tips

- To automate bundling before starting the app, add a `prestart` script in `package.json`:

```json
"scripts": {
  "prestart": "react-native-node insert ./background",
  "start": "node node_modules/react-native/local-cli/cli.js start"
}
```

- For debugging, use `adb logcat` with specific tags:

```bash
adb logcat *:S nodejs:V ReactNative:V ReactNativeJS:V
```

### Limitations

- Currently supports Android only
- Relies on a prebuilt Node.js v7.1.0 binary
- Limited support for packages with native bindings

## API Reference

### Core Module: RNNode

The `RNNode` module provides functionality to manage a Node.js process running alongside your React Native application.

#### Methods

##### `start(args?: string[])`
Starts the Node.js process with optional command-line arguments.

- **Parameters**:
  - `args` (optional): An array of command-line arguments to pass to the Node.js process
  - If no arguments are provided, an empty array will be used
- **Returns**: `void`

**Example**:
```javascript
import RNNode from 'react-native-node';

// Start Node.js process without arguments
RNNode.start();

// Start Node.js process with custom arguments
RNNode.start(['--port', '3000']);
```

##### `stop()`
Stops the running Node.js process.

- **Parameters**: None
- **Returns**: `void`

**Example**:
```javascript
import RNNode from 'react-native-node';

RNNode.stop();
```

### Command-Line Interface

The library includes a CLI tool `react-native-node` which can be used for additional operations (configuration details available in the CLI tool itself).

## Project Structure

The project is organized into several key directories and files:

### Root Directory
- `bin.js`: Core executable script for the project
- `index.js`: Main entry point for the module
- `package.json`: Project configuration and dependency management
- `LICENSE`: Project licensing information
- `CHANGELOG.md`: Record of version changes and updates

### Android Integration
The `android/` directory contains platform-specific implementation for Android:
- `android/build.gradle`: Gradle build configuration for Android
- `android/src/main/java/com/staltz/reactnativenode/`: Contains Java classes for React Native Node module
  - `RNNodeModule.java`: Module implementation
  - `RNNodePackage.java`: Package configuration
  - `RNNodeService.java`: Service-related functionality
  - `RNNodeThread.java`: Thread management for Node.js operations

### Example Project
The `example/` directory provides a comprehensive demo application:
- `example/android/`: Android-specific project configuration
- `example/background/`: Background processing scripts
- `example/index.android.js`: Main Android entry point
- `example/package.json`: Example project dependencies

### Configuration Files
- `.eslintrc`: ESLint configuration for code linting
- `.flowconfig`: Flow type checking configuration
- `.gitignore`: Git ignore rules
- `.npmignore`: NPM package publishing exclusion rules

### Additional Resources
- `screenshot.png`: Visual preview or documentation image

## Technologies Used

### Core Technologies
- **React Native**: Mobile application framework (version 0.48.3)
- **Node.js**: Backend runtime environment
- **JavaScript**: Primary programming language

### Development Tools
- **Babel**: JavaScript transpiler (babel-preset-react-native)
- **ESLint**: Code linting tool
- **Gradle**: Build automation tool for Android

### Libraries and Dependencies
- **React**: UI component library (version 16.0.0-alpha.12)
- **Cheerio**: HTML parsing and manipulation library
- **Tar**: File compression and extraction library
- **Mkdirp**: Directory creation utility
- **Yargs**: Command-line argument parsing

### Android Specific
- **Android SDK**: Compilation SDK version 23
- **Android Support Library**: AppCompat v7
- **Apache Commons Compress**: Compression utilities

### Build and Packaging
- **npm/yarn**: Package management
- **React Native CLI**: Command-line interface for React Native projects

## Additional Notes

### Performance Considerations

When running Node.js in a mobile environment, be mindful of resource consumption. The bundled Node.js binary (v7.1.0) is precompiled and may have performance implications for complex applications.

### Debugging Techniques

Utilize Android logging to troubleshoot your Node.js background process. Key logging tags include:
- `RNNodeThread`: Process start, termination, and error events
- `RNNodeService`: Binary preparation and tar/untar operations
- `RNNode`: General library-specific logging

Debug command:
```
adb logcat *:S nodejs:V ReactNative:V ReactNativeJS:V
```

### Compatibility Limitations

#### Platform Support
- **Android**: Fully supported
- **iOS**: Not supported due to Apple's restrictions on Just-In-Time compilation

#### Package Ecosystem
- Native binding support is limited
- Most packages without explicit Android native bindings may work
- Recommended compilation approach: Use Termux on an Android device for native package compilation

### Security Note

The prebuilt Node.js binary is provided for convenience. For heightened security, consider compiling the binary yourself following the ["NodeBase"](https://github.com/dna2github/NodeBase) approach.

### Project Origin

This library was originally developed to support the [Scuttlebutt](https://www.scuttlebutt.nz/) ecosystem on mobile platforms, specifically for the [mmmmm mobile app](https://github.com/staltz/mmmmm-mobile).

## Contributing

We welcome contributions to react-native-node! Here are some guidelines to help you get started:

### Code Contribution Guidelines

- Fork the repository and create your branch from `master`
- Ensure your code follows the project's ESLint configuration
  - Uses single quotes for strings
  - Follows strict mode
  - Maintains consistent code style
- Run all existing tests before submitting a pull request
- Include clear, concise descriptions with your pull requests

### Code Style

The project uses ESLint with the following key style rules:
- Single quotes for strings
- Camel case for variables and functions
- Semicolons are required
- Consistent spacing around operators
- No trailing whitespaces

### Reporting Issues

- Use the GitHub Issues section to report bugs or suggest features
- Provide a clear and detailed description
- Include steps to reproduce the issue if applicable
- Specify the version of react-native-node you are using

### Support

If you'd like to support the project, you can do so via Patreon for the project author.

## License

This project is licensed under the MIT License. 

### Full License Text
The MIT License is a permissive free software license originating at the Massachusetts Institute of Technology. It puts very limited restrictions on reuse and has, therefore, high license compatibility.

#### Key Permissions
- Commercial use
- Modification
- Distribution
- Private use

#### Limitations
- Liability
- Warranty

For the complete license text, please refer to the [LICENSE](LICENSE) file in the repository.

#### Copyright
Copyright (c) 2017-present André Staltz (staltz.com)