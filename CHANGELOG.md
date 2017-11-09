# 3.1.1

- Support `TMPDIR` pointing to an actual directory

# 3.0.0

## Breaking change

The Android background Service now uses the stickiness level `START_NOT_STICKY` which makes more sense by default. In future versions we should enable choosing the stickiness level from the JavaScript API.

# 2.1.0

## Feature / bug fix

- Displays the stdout and stderr of your node.js process in the Android logcat, with the tag `nodejs`, as "verbose"

# 2.0.0

## Breaking changes

- Depends on React Native v0.48, so breaking changes from RN v0.40 to RN v0.48 also apply to this package.

