package com.staltz.reactnativenode;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RNNodeThread extends Thread {
    private static final String TAG = "RNNodeThread";
    private static final String NODETAG = "nodejs";

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

    static class Logger implements Runnable {
        private BufferedReader reader;
        private final boolean isError;

        public Logger(InputStream rS, boolean isError) throws IOException {
            this.reader = new BufferedReader(new InputStreamReader(rS));
            this.isError = isError;
        }

        @Override
        public void run() {
            try {
                String line;
                while ((line = this.reader.readLine()) != null) {
                    if (this.isError) {
                        Log.e(NODETAG, line);
                    } else {
                        Log.v(NODETAG, line);
                    }
                }
            } catch (IOException e) {
                Log.e(TAG + ".Logger", e.toString());
            }
        }
    }

    @Override
    public void run() {
        try {
            _process = _processBuilder.start();
            Log.i(TAG, "Node.js process is running...");
            final Thread stdoutThread = new Thread(new Logger(_process.getInputStream(), false));
            final Thread stderrThread = new Thread(new Logger(_process.getErrorStream(), true));
            stdoutThread.run();
            stderrThread.run();
            _process.waitFor();
        } catch (Exception e) {
            // probably interrupted
            Log.e(TAG, "Error running Node.js process:", e);
        }
        Log.i(TAG, "Node.js process terminated.");
        _running = false;
    }
}
