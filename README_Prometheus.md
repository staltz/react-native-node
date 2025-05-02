# React Native Node: Empowering Mobile Apps with Background Node.js Processing

## Project Overview

React Native Node is a powerful library that enables running a full Node.js process in the background of a React Native Android application. It provides a unique solution for developers who need to leverage Node.js capabilities within a mobile React Native environment.

### Core Purpose

The library allows developers to:
- Run a complete Node.js process alongside a React Native app
- Execute Node.js functionality that is typically unavailable in mobile JavaScript environments
- Off-load heavy processing from the main JavaScript thread
- Interface with the file system
- Run HTTP servers directly from the mobile application

### Key Features

- Seamless integration with React Native
- Runs a real Node.js process (v7.1.0) using V8 engine
- Supports background processing
- Enables use of Node.js streams
- Provides method to start and stop the Node.js background process
- Compresses and bundles the Node.js application into the mobile app

### Limitations

- **Deprecated**: The project is no longer actively maintained
- Currently supports Android only
- iOS support is not available due to platform restrictions
- Limited support for packages with native bindings

## Getting Started, Installation, and Setup

### Prerequisites

- React Native (version 0.47.0 or higher)
- npm or yarn
- Android development environment

### Installation

Install the package using npm or yarn:

```bash
npm install --save react-native-node
# or
yarn add react-native-node
```

Link the native module:

```bash
react-native link react-native-node
```

### Quick Start

1. Create a background Node.js project (e.g., in a `./background` directory)

2. In your React Native app, import and use the module:

```javascript
import RNNode from "react-native-node";

class MyApp extends Component {
  componentDidMount() {
    // Start the background Node.js process
    RNNode.start();
    
    // Optionally, pass arguments to the process
    // RNNode.start(['--color', 'red', '--port', '3000'])
  }

  componentWillUnmount() {
    // Stop the background process when no longer needed
    RNNode.stop();
  }
}
```

3. Bundle your background application:

```bash
./node_modules/.bin/react-native-node insert ./background
```

This command does two things:
- Compresses your background app into `./android/app/src/main/res/raw/rnnodebundle`
- Updates `AndroidManifest.xml` to include a Service class

4. Build and run your mobile app:

```bash
react-native run-android
```

### Platform Support

#### Android
- Fully supported using Node.js v7.1.0 with V8
- Requires Android development environment

#### iOS
- **Not supported** due to Apple's restrictions on Just-In-Time compilation

### Debugging

To debug the Node.js process, use `adb logcat` with specific tags:

```bash
adb logcat *:S nodejs:V RNNodeThread:V RNNodeService:V
```

### Important Notes

- This library is **DEPRECATED**
- Consider using [Node.js Mobile](https://code.janeasystems.com/nodejs-mobile/) as an alternative
- Most suitable for Android development
- Supports Node.js packages, but native bindings may require additional configuration

## API Reference

### Constants

There are no exported constants in this library.

### RNNode Module

#### `start(args?: string[])`
Starts the Node.js runtime within the mobile application.

- **Parameters**:
  - `args` (optional): An array of command-line arguments to pass to Node.js. If not an array, an empty array will be used.
- **Returns**: `void`

**Example**:
```javascript
import RNNode from 'react-native-node';

// Start Node.js with no arguments
RNNode.start();

// Start Node.js with specific arguments
RNNode.start(['-e', 'console.log("Hello from Node.js")']);
```

#### `stop()`
Stops the Node.js runtime that was previously started.

- **Parameters**: None
- **Returns**: `void`

**Example**:
```javascript
import RNNode from 'react-native-node';

RNNode.stop();
```

### Default Export
The library also exports the `RNNode` module as its default export, which is equivalent to the named export.

**Example**:
```javascript
import RNNode from 'react-native-node';
// or
import { RNNode } from 'react-native-node';

// Both forms can be used interchangeably
RNNode.start();
```

## Project Structure

The project is organized into several key directories and files:

#### Root Directory
- `bin.js`: Core script for the main functionality
- `index.js`: Main entry point for the project
- `package.json`: Project configuration and dependencies
- `LICENSE`: Project licensing information
- `CHANGELOG.md`: Version history and changes

#### Android Integration
The `android/` directory contains Android-specific implementation:
- `android/build.gradle`: Android build configuration
- `android/src/main/java/com/staltz/reactnativenode/`: Contains Java classes for React Native Node module
  - `RNNodeModule.java`: Main module implementation
  - `RNNodePackage.java`: Package configuration
  - `RNNodeService.java`: Service-related functionality
  - `RNNodeThread.java`: Threading implementation

#### Example Project
The `example/` directory provides a sample implementation:
- `example/index.android.js`: Example Android entry point
- `example/background/`: Background processing example
- `example/android/`: Example Android project structure
  - `example/android/app/`: Contains Android app-specific files
  - `example/android/app/src/main/java/com/example/`: MainActivity and MainApplication

#### Configuration Files
- `.eslintrc`: ESLint configuration for code linting
- `.flowconfig`: Flow type checking configuration
- `.npmignore` and `.gitignore`: Exclusion rules for npm and git

## Technologies Used

### Core Technologies
- **React Native**: Mobile application framework (peer dependency)
- **Node.js**: Version 7.1.0, used for background processing
- **V8 JavaScript Engine**: Used for Node.js runtime

### Programming Languages
- JavaScript
- Java (for Android implementation)

### Core Dependencies
- **cheerio**: HTML parsing and manipulation library
- **mkdirp**: Directory creation utility
- **path**: File path manipulation
- **tar**: Tar archive manipulation
- **yargs**: Command-line argument parsing

### Development Tools
- **ESLint**: Code linting
- **Babel**: JavaScript transpilation

### Platforms
- Android (primary supported platform)

### Build and Packaging
- Gradle (for Android build process)
- npm/yarn for package management

### Key Features
- Background Node.js process running in React Native
- Native Node.js binary compilation
- Service-based background processing

## Additional Notes

### Limitations and Considerations

- **Deprecation Notice**: This library is no longer actively maintained. The recommended alternative is [Node.js Mobile](https://code.janeasystems.com/nodejs-mobile/) by Janea Systems.

### Debugging and Troubleshooting

When working with this library, use `adb logcat` with specific tags to diagnose issues:

```
adb logcat *:S nodejs:V ReactNative:V ReactNativeJS:V
```

Key logging tags:
- `RNNodeThread`: Process start, termination, and error events
- `RNNodeService`: Debug information for tar/untar and Node binary preparation
- `RNNode`: General library-related logging

### Performance and Optimization

#### Bundle Size Optimization
To reduce the size of the `rnnodebundle` file, consider using tools like [noderify](https://www.npmjs.com/package/noderify) to create a single JavaScript file.

### Technical Details

- **Node.js Version**: Uses Node.js v7.1.0 with V8 engine
- **Platform Support**: 
  - Android: Fully supported
  - iOS: Not supported due to Apple's restrictions on Just-In-Time compilation

### Native Package Compatibility

While the library supports packages with native bindings in theory, compatibility depends on individual library implementations. For complex native compilations, consider using `termux` on an Android device for direct compilation.

### Origin and Use Case

This library was primarily developed to support the [Scuttlebutt](https://www.scuttlebutt.nz/) ecosystem, specifically for the mobile app project [mmmmm](https://github.com/staltz/mmmmm-mobile).

## Contributing

We welcome contributions to the React Native Node project! Here are some guidelines to help you contribute effectively:

### Types of Contributions

- Bug reports and feature suggestions via GitHub Issues
- Pull requests for bug fixes, improvements, or new features
- Documentation enhancements
- Code improvements and performance optimizations

### Contribution Process

1. Fork the repository
2. Create a new branch for your contribution
3. Make your changes, following the project's existing code style
4. Add or update tests if applicable
5. Ensure all existing tests pass
6. Submit a pull request with a clear description of your changes

### Code Style and Standards

- Follow the existing code style in the project
- Use ESLint for code linting (configuration provided in `.eslintrc`)
- Write clear, concise commit messages
- Include comments for complex logic
- Ensure backward compatibility with React Native >=0.47.0

### Testing

- Run existing tests before submitting a pull request
- Add tests for new functionality or bug fixes
- Verify your changes work across supported platforms

### Reporting Issues

- Use GitHub Issues to report bugs or suggest features
- Provide a clear and detailed description
- Include steps to reproduce the issue
- If reporting a bug, include your environment details (React Native version, device, etc.)

### Note

This project is currently deprecated. While contributions are welcome, the maintainers recommend using [Node.js Mobile](https://code.janeasystems.com/nodejs-mobile/) for similar functionality.

### License

By contributing, you agree that your contributions will be licensed under the project's MIT License.

## License

This project is licensed under the MIT License. 

### Full License Details

The MIT License is a permissive free software license originating at the Massachusetts Institute of Technology. It puts limited restrictions on reuse and has, therefore, high license compatibility.

#### Key Permissions
- Commercial use
- Modification
- Distribution
- Private use

#### Limitations
- No Liability
- No Warranty

### License Text

The full license text can be found in the [LICENSE](LICENSE) file in the project root. The license covers the entire project and applies to all code, documentation, and resources.

#### Copyright
Copyright (c) 2017-present André Staltz (staltz.com)