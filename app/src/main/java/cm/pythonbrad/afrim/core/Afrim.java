package cm.pythonbrad.afrim.core;

import android.util.Log;

import java.util.Arrays;

public final class Afrim {
    static final String TAG = Afrim.class.getSimpleName();

    // Load the native library "libafrim_jni.so".
    static {
        System.loadLibrary("afrim_jni");
    } 

    // Native functions implemented in Rust.
    private static native int nativeInit();
    private static native boolean nativeStatus();
    private static native boolean nativeUpdateConfig(String filename);
    private static native void nativeDrop();
    private static native Boolean[] nativeProcessKey(String key, String state);
    private static native void nativeCommitText(String text);
    private static native void nativePopStack();
    private static native void nativeClear();
    private static native String nativeGetInput();
    private static native String[] nativeTranslate();

    public static int init() {
        return nativeInit();
    }
    public static  boolean check() {
        return nativeStatus();
    }
    public static void processKey(String key, String state) {
        final Boolean[] status = nativeProcessKey(key, state);
        Log.d("libafrim_jni", "processKey: " + Arrays.toString(status));
    }
}
