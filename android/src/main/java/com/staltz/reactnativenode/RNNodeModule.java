package com.staltz.reactnativenode;

import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

import java.util.ArrayList;

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

    public ArrayList<String> toStringArrayList(ReadableArray array) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            arrayList.add(array.getString(i));
        }
        return arrayList;
    }

    @ReactMethod
    public void start(ReadableArray args) {
        Log.d(TAG, "Launching an intent for RNNodeService...");
        Intent intent = new Intent(_reactContext, RNNodeService.class);
        intent.putExtra("args", this.toStringArrayList(args));
        _reactContext.startService(intent);
    }

    @ReactMethod
    public void stop() {
        Log.d(TAG, "Stopping an intent for RNNodeService...");
        Intent intent = new Intent(_reactContext, RNNodeService.class);
        _reactContext.stopService(intent);
    }
}