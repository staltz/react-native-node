package com.staltz.reactnativenode;

import android.util.Log;

import java.util.Scanner;

public class RNNodeThread extends Thread {
    private static final String TAG = "RNNodeThread";

    private boolean _running;
    private Process _process;
    private ProcessBuilder _processBuilder;

    public RNNodeThread(ProcessBuilder processBuilder) {
        _running = true;
        _processBuilder = processBuilder;
    }

    public void kill() {
        if (_running) {
            _process.destroy();
            _process = null;
            _running = false;
            Log.i(TAG, "Node.js process killed.");
        }
    }

    @Override
    public void run() {
        try {
            _process = _processBuilder.start();
            Log.i(TAG, "Node.js process is running...");
            // Report output
            Scanner s1 = new Scanner(_process.getInputStream()).useDelimiter("\\A");
            String output = s1.hasNext() ? s1.next() : "";
            if (output != null && output.length() > 0) {
                Log.v(TAG, output);
            }
            // Report errors
            Scanner s = new Scanner(_process.getErrorStream()).useDelimiter("\\A");
            String errors = s.hasNext() ? s.next() : "";
            if (errors != null && errors.length() > 0) {
                Log.e(TAG, errors);
            }
        } catch (Exception e) {
            // probably interrupted
            Log.e(TAG, "Error running Node.js process:", e);
        }
        Log.i(TAG, "Node.js process terminated.");
        _running = false;
    }
}
