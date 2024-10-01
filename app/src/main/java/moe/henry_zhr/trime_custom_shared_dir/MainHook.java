package moe.henry_zhr.trime_custom_shared_dir;

import android.annotation.SuppressLint;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import java.io.File;

public class MainHook implements IXposedHookLoadPackage {
  @Override
  public void handleLoadPackage(LoadPackageParam lpparam) {
    final String TAG = "TrimeCustomSharedDir";
    final String TRIME_PACKAGE_NAME = "com.osfans.trime";
    if (!lpparam.packageName.equals(TRIME_PACKAGE_NAME)
        && !lpparam.packageName.startsWith(TRIME_PACKAGE_NAME + '.')) {
      return;
    }
    Log.v(TAG, "Starting hook");
    // https://github.com/osfans/trime/blob/fe9c39dc41d8c8d9596aded56b158e67659abb2c/app/src/main/java/com/osfans/trime/data/base/DataManager.kt#L70
    XposedHelpers.findAndHookConstructor(
        File.class,
        File.class,
        String.class,
        new XC_MethodHook() {
          @SuppressLint("SdCardPath")
          @Override
          protected void beforeHookedMethod(MethodHookParam param) {
            final String path = ((File) param.args[0]).getPath();
            if (param.args[1].equals("shared")
                && path.endsWith("/Android/data/" + lpparam.packageName + "/files")) {
              Log.v(TAG, "Modifying shared dir");
              param.args[0] = new File("/sdcard");
              param.args[1] = "rime-shared";
            }
          }
        });
    Log.v(TAG, "Finished hook");
  }
}
