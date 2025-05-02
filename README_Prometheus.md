# React Native Node: Node.js Background Processing for Android React Native Apps

## Project Overview

React Native Node is a powerful library that enables developers to run a full Node.js process in the background of a React Native Android application. It provides a seamless way to leverage the full capabilities of Node.js within a mobile React Native environment.

### Key Features

- **Native Node.js Integration**: Runs a complete Node.js v7.1.0 process alongside a React Native app
- **Background Processing**: Allows running server-side tasks, HTTP servers, and complex computations in a separate thread
- **Filesystem and Stream Support**: Provides full access to Node.js filesystem and streaming capabilities
- **Android-Focused**: Specifically designed for Android mobile applications

### Use Cases

The library is particularly useful for scenarios such as:
- Running background HTTP servers in mobile apps
- Offloading heavy computational tasks from the JavaScript thread
- Utilizing Node.js-specific libraries and modules in mobile applications
- Enabling complex background services that require full Node.js functionality

### Limitations

- **Platform Support**: Currently limited to Android
- **Node.js Version**: Uses Node.js v7.1.0
- **Maintenance Status**: Deprecated in favor of Node.js Mobile

## Getting Started, Installation, and Setup

### Prerequisites

- React Native project (version 0.47.0 or higher)
- Node.js and npm/yarn installed
- Android development environment

### Quick Start

1. Install the package in your React Native project:
   ```bash
   npm install --save react-native-node
   ```

2. Link the native module:
   ```bash
   react-native link react-native-node
   ```

3. Create a background Node.js project (e.g., in a `./background` directory)

4. In your React Native application, import and use the module:
   ```javascript
   import RNNode from "react-native-node";

   class MyApp extends Component {
     componentDidMount() {
       // Start the Node.js background process
       RNNode.start();
       
       // Optional: Start with custom arguments
       // RNNode.start(['--port', '3000'])
     }

     componentWillUnmount() {
       // Stop the background process
       RNNode.stop();
     }
   }
   ```

5. Bundle the background application:
   ```bash
   ./node_modules/.bin/react-native-node insert ./background
   ```

6. Build and run your Android app:
   ```bash
   react-native run-android
   ```

### Development Workflow

#### Bundling Background App
Always bundle your background Node.js application before building the mobile app. You can automate this by adding a `prestart` script in your `package.json`:

```json
"scripts": {
  "prestart": "react-native-node insert ./background",
  "start": "node node_modules/react-native/local-cli/cli.js start"
}
```

#### Debugging
To debug the Node.js process, use `adb logcat` with specific tags:

```bash
adb logcat *:S nodejs:V ReactNative:V ReactNativeJS:V
```

### Platform Support
- Currently supports Android only
- iOS support is not available due to platform limitations

## API Reference

### RNNode Module

The `RNNode` module provides functionality to start and stop a Node.js process within a React Native application.

#### Methods

##### `start(args?: string[])`
Starts a Node.js process with optional arguments.

- **Parameters**:
  - `args` (optional): An array of string arguments to pass to the Node.js process
  - If no arguments are provided, an empty array will be used
- **Returns**: `void`

**Example**:
```javascript
import RNNode from 'react-native-node';

// Start Node.js process without arguments
RNNode.start();

// Start Node.js process with arguments
RNNode.start(['--port', '8080']);
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

### Notes
- Requires React Native version 0.47.0 or higher
- This module enables running a separate Node.js process in the background of a React Native application

## Technologies Used

### Frameworks and Libraries
- React Native (version 0.48.3+)
- Node.js (embedded runtime)

### Core Dependencies
- cheerio: HTML parsing and manipulation
- mkdirp: Directory creation utility
- path: File path utilities
- tar: Tar archive handling
- yargs: Command-line argument parsing

### Development Tools
- ESLint: Code linting
- Babel: JavaScript transpilation
- React Native CLI: Project development and build tool

### Platforms
- Android support
- React Native cross-platform development

### Programming Languages
- JavaScript (ES6+)
- Java (for Android native module)

### Build and Package Management
- Yarn
- npm

## Additional Notes

### Compatibility and Limitations

#### Android Support
- Currently supports Android platforms only
- Uses Node.js v7.1.0 compiled with V8
- Not compatible with iOS due to Apple's restrictions on Just-In-Time compilation

#### Package and Dependency Considerations
- Native package support is limited and depends on individual library Android compatibility
- Recommended for projects requiring background Node.js processing in React Native Android apps

### Performance and Security

#### Binary Considerations
- Prebuilt Node.js binary (`bin_node_v710`) is included with the library
- For enhanced security, consider compiling the Node.js binary yourself

### Debugging Resources

#### Logging Tags
Utilize the following ADB logcat tags for troubleshooting:
- `nodejs`: General Node.js process logging
- `RNNodeThread`: Process start, termination, and error details
- `RNNodeService`: Debugging for tar/untar and Node binary preparation
- `RNNode`: Additional library-specific logging

### Future Development

#### Project Status
**DEPRECATED**: The maintainer recommends using [Node.js Mobile](https://code.janeasystems.com/nodejs-mobile/) by Janea Systems, which offers:
- Similar functionality
- More up-to-date maintenance
- iOS support

### Related Projects
- [Scuttlebutt Ecosystem](https://www.scuttlebutt.nz/)
- [react-native-scuttlebot](https://github.com/ssbc/react-native-scuttlebot)
- [mmmmm Mobile App](https://github.com/staltz/mmmmm-mobile)

## Contributing

We welcome contributions to React Native Node! By contributing, you help improve this project for the entire community.

### Ways to Contribute

- Reporting bugs
- Suggesting enhancements
- Improving documentation
- Submitting pull requests

### Contribution Guidelines

#### Reporting Issues

- Check existing issues before opening a new one
- Use a clear and descriptive title
- Provide detailed steps to reproduce the issue
- Include your environment details (React Native version, device, etc.)

#### Pull Request Process

1. Fork the repository
2. Create a branch for your feature or bugfix
3. Ensure code follows existing code style
4. Add or update tests as appropriate
5. Verify all tests pass

#### Code Style

- Follow existing code conventions in the project
- Use clear, descriptive variable and function names
- Write comments for complex logic

#### Development Setup

- Requires React Native >=0.47.0
- Use `npm` or `yarn` for dependency management
- Run tests before submitting a pull request

### Note

This project is currently marked as deprecated. Contributions are still welcome, but the maintainer recommends using [Node.js Mobile](https://code.janeasystems.com/nodejs-mobile/) for new projects.

### Code of Conduct

Please be respectful and considerate of others. Harassment and discrimination are not tolerated.

### License

By contributing, you agree that your contributions will be licensed under the MIT License.

## License

This project is licensed under the MIT License. 

#### Licensing Details

The MIT License is a permissive free software license that allows users to:
- Use the software for any purpose
- Modify the software
- Distribute the software
- Sublicense the software
- Use the software commercially

#### Copyright

Copyright (c) 2017-present André Staltz (staltz.com)

#### Full License

For the complete license text, see the [LICENSE](LICENSE) file in the repository.