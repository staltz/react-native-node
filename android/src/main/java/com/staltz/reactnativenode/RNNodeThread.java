package com.staltz.reactnativenode;

import android.util.Log;

public class RNNodeThread extends Thread {
   private static final String TAG = "RNNodeThread";

   private boolean _running;
   private Process _process;

   public RNNodeThread(Process process) {
      _running = true;
      _process = process;
   }

   public void kill() {
      if (_running) {
         _process.destroy();
      }
   }

   @Override
   public void run() {
      try {
         Log.i(TAG, "Node.js process is running...");
         _process.waitFor();
      } catch (Exception e) {
         // probably interrupted
         Log.e(TAG, "node error", e);
      }
      Log.i(TAG, "Node.js process stopped.");
      _running = false;
   }
}
