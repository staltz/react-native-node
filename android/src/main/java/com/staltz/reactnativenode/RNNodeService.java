package com.staltz.reactnativenode;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RNNodeService extends Service {
    private static final String TAG = "RNNodeService";
    private static final String RAW_BUNDLE_NAME = "rnnodebundle";
    private static final String APP_NAME = "rnnodeapp";
    private static final String DEFAULT_APP_ENTRY = "index.js";

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
            ArrayList<String> args = intent.getStringArrayListExtra("args");
            startNode(this.getApplicationInfo().dataDir, args);
        }
        // running until explicitly stop
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        Log.v(TAG, "Create and prepare");
        String dataDir = this.getApplicationInfo().dataDir;
        prepareNode(this, dataDir);
        File bundleTar = prepareBundle(this, dataDir);
        if (bundleTar == null) {
            Log.i(TAG, "Skipping untaring of the bundle, because it would be redundant");
            return;
        }
        try {
            unTar(bundleTar, new File(dataDir + "/" + APP_NAME));
        } catch (Exception e) {
            Log.e(TAG, "Cannot uncompress the background app bundle", e);
        }
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

    public File prepareBundle(Context context, String dataDir) {
        InputStream bundleBinary = null;
        try {
            int rawId = context.getResources().getIdentifier(RAW_BUNDLE_NAME, "raw", context.getPackageName());
            bundleBinary = context.getResources().openRawResource(rawId);
            String tarFilename = String.format("%s/%s.tgz", dataDir, APP_NAME);
            File tarFile = new File(tarFilename);
            if (tarFile.exists() && tarFile.length() == bundleBinary.available()) {
                return null;
            }
            tarFile.createNewFile();
            InputStreamReader reader = new InputStreamReader(bundleBinary);
            FileOutputStream writer = new FileOutputStream(tarFile, false);
            byte[] binary = new byte[(int)(bundleBinary.available())];
            bundleBinary.read(binary);
            writer.write(binary);
            writer.flush();
            writer.close();
            bundleBinary.close();
            return tarFile;
        } catch (Exception e) {
            Log.e(TAG, "Cannot prepare tar file for the bundle. " +
                    "Are you sure you ran \"react-native-node insert\"?",
                    e
            );
        }
        return null;
    }

    private static List<File> unTar(final File inputFile, final File outputDir) throws FileNotFoundException, IOException, ArchiveException {
        Log.i(TAG, String.format("Untaring %s to dir %s", inputFile.getAbsolutePath(), outputDir.getAbsolutePath()));

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        final List<File> untaredFiles = new LinkedList<File>();
        final InputStream is = new FileInputStream(inputFile);
        final TarArchiveInputStream debInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", is);
        TarArchiveEntry entry = null;
        while ((entry = (TarArchiveEntry)debInputStream.getNextEntry()) != null) {
            final File outputFile = new File(outputDir, entry.getName());
            if (entry.isDirectory()) {
                if (!outputFile.exists()) {
                    if (!outputFile.mkdirs()) {
                        throw new IllegalStateException(String.format("Couldn't create directory %s.", outputFile.getAbsolutePath()));
                    }
                }
            } else {
                final OutputStream outputFileStream = new FileOutputStream(outputFile);
                IOUtils.copy(debInputStream, outputFileStream);
                outputFileStream.close();
            }
            untaredFiles.add(outputFile);
        }
        debInputStream.close();

        return untaredFiles;
    }

    public void startNode(String dataDir, ArrayList<String> args) {
        if (_thread != null) {
            return;
        }
        Log.v(TAG, "Will start RNNodeThread now...");
        ArrayList<String> cmd = new ArrayList<>();
        cmd.add(String.format("%s/node", dataDir));
        cmd.add(String.format("%s/%s/%s", dataDir, APP_NAME, DEFAULT_APP_ENTRY));
        if (args != null) {
            cmd.addAll(args);
        }
        try {
            ProcessBuilder pb = (new ProcessBuilder(cmd))
                    .directory(new File(dataDir));
            Map<String, String> env = pb.environment();
            env.put("TMPDIR", getCacheDir().getAbsolutePath());
            env.put("HOME", dataDir);
            env.put("LD_LIBRARY_PATH", this.getApplicationInfo().nativeLibraryDir);
            _thread = new RNNodeThread(pb);
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
