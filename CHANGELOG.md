# 3.3.1

* Fix issue where tar file is updated and should be decompressed. Previously, the updated tar was never decompressed if it already existed. Now, the updated raw resource tar will be decompressed every time its byte size is different to existing tar file.

# 3.3.0

* Support `LD_LIBRARY_PATH` env var pointing to the app's library directory
  (`getApplicationInfo().nativeLibraryDir)`). The app library directory is
  populated with contents from your project's `android/app/src/main/jniLibs`
  directory.

# 3.2.0

* Support HOME env var pointing to the app's data directory

# 3.1.1

* Support `TMPDIR` pointing to an actual directory

# 3.0.0

## Breaking change

The Android background Service now uses the stickiness level `START_NOT_STICKY`
which makes more sense by default. In future versions we should enable choosing
the stickiness level from the JavaScript API.

# 2.1.0

## Feature / bug fix

* Displays the stdout and stderr of your node.js process in the Android logcat,
  with the tag `nodejs`, as "verbose"

# 2.0.0

## Breaking changes

* Depends on React Native v0.48, so breaking changes from RN v0.40 to RN v0.48
  also apply to this package.
