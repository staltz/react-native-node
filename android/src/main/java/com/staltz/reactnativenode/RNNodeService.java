package com.staltz.reactnativenode;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.File;
import java.io.InputStreamReader;

public class RNNodeService extends Service {
    private static final String TAG = "RNNodeService";

    private RNNodeThread _thread;

    public RNNodeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            startNode(this.getApplicationInfo().dataDir);
        }
        // running until explicitly stop
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.v(TAG, "Create and prepare");
        String dataDir = this.getApplicationInfo().dataDir;
        prepareNode(this, dataDir);
        prepareEntryFileTemporaryCrap(this, dataDir);
        prepareOtherFileTemporaryCrap(this, dataDir);
    }

    public void prepareNode(Context context, String dataDir) {
        InputStream nodeBinary = null;
        try {
            nodeBinary = context.getResources().openRawResource(R.raw.bin_node_v710);
            String nodeFilename = String.format("%s/node", dataDir);
            File nodeFile = new File(nodeFilename);
            if (nodeFile.exists()) {
                return;
            }
            nodeFile.createNewFile();
            InputStreamReader reader = new InputStreamReader(nodeBinary);
            FileOutputStream writer = new FileOutputStream(nodeFile);
            byte[] binary = new byte[(int)(nodeBinary.available())];
            nodeBinary.read(binary);
            writer.write(binary);
            writer.flush();
            writer.close();
            nodeFile.setExecutable(true, true);
            nodeBinary.close();
        } catch (Exception e) {
            Log.e(TAG, "Cannot create binary file for \"node\"");
        }
    }

    public void prepareEntryFileTemporaryCrap(Context context, String dataDir) {
        InputStream fileContents = null;
        try {
            fileContents = context.getResources().openRawResource(R.raw.testjs);
            String filenameInDataDir = String.format("%s/test.js", dataDir);
            File file = new File(filenameInDataDir);
            if (file.exists()) {
                return;
            }
            file.createNewFile();
            InputStreamReader reader = new InputStreamReader(fileContents);
            FileOutputStream writer = new FileOutputStream(file);
            byte[] binary = new byte[(int)(fileContents.available())];
            fileContents.read(binary);
            writer.write(binary);
            writer.flush();
            writer.close();
            file.setExecutable(true, true);
            fileContents.close();
        } catch (Exception e) {
            Log.e(TAG, "Cannot create entry file \"test.js\"");
        }
    }

    public void prepareOtherFileTemporaryCrap(Context context, String dataDir) {
        InputStream fileContents = null;
        try {
            fileContents = context.getResources().openRawResource(R.raw.otherjs);
            String filenameInDataDir = String.format("%s/other.js", dataDir);
            File file = new File(filenameInDataDir);
            if (file.exists()) {
                return;
            }
            file.createNewFile();
            InputStreamReader reader = new InputStreamReader(fileContents);
            FileOutputStream writer = new FileOutputStream(file);
            byte[] binary = new byte[(int)(fileContents.available())];
            fileContents.read(binary);
            writer.write(binary);
            writer.flush();
            writer.close();
            file.setExecutable(true, true);
            fileContents.close();
        } catch (Exception e) {
            Log.e(TAG, "Cannot create entry file \"other.js\"");
        }
    }

    public void startNode(String dataDir) {
        if (_thread != null) {
            return;
        }
        Log.v(TAG, "Will start RNNodeThread now...");
        String[] cmd = new String[] {
                String.format("%s/node", dataDir),
                String.format("%s/test.js", dataDir)
        };
        try {
            Process process = (new ProcessBuilder(cmd)).redirectErrorStream(true).start();
            _thread = new RNNodeThread(process);
            _thread.start();
            Log.v(TAG, "RNNodeThread started.");
        } catch (Exception e) {
            Log.e(TAG, "Cannot start \"node\"", e);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "destroy");
        stopNode();
    }

    public void stopNode() {
        if (_thread != null) {
            _thread.kill();
            _thread = null;
        }
    }
}
