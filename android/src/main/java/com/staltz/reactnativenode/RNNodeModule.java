package com.staltz.reactnativenode;

import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
* The NativeModule acting as a JS API layer for react-native-node.
*/
public final class RNNodeModule extends ReactContextBaseJavaModule {
    private static final String TAG = "RNNode";
    private ReactContext _reactContext;

    public RNNodeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        _reactContext = reactContext;
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void start() {
        Log.d(TAG, "Launching an intent for RNNodeService...");
        Intent intent = new Intent(_reactContext, RNNodeService.class);
        _reactContext.startService(intent);
    }
}