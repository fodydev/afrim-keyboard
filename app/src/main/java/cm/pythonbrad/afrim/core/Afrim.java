package cm.pythonbrad.afrim.core;

import static cm.pythonbrad.afrim.core.Serializer.*;

import android.view.KeyEvent;

import cm.pythonbrad.afrim.latin.common.Constants;

/**
 * Afrim Input method wrapper.
 */
public final class Afrim {
    static final String TAG = Afrim.class.getSimpleName();

    // Load the native library "libafrim_jni.so".
    static {
        System.loadLibrary("afrim_jni");
    } 

    // Native functions implemented in Rust.
    // Singleton.
    private static native boolean nativeUpdateConfig(String filename);
    private static native void nativeInit();
    private static native boolean nativeStatus();
    private static native void nativeDrop();
    // Preprocessor.
    private static native boolean[] nativeProcessKey(String key, String state);
    private static native void nativeCommitText(String text);
    private static native String nativePopStack();
    private static native void nativeClear();
    private static native String nativeGetInput();
    // Translator.
    private static native String[] nativeTranslate();

    // Native function implemented in Java.
    // Singleton.
    public static void init() {
        nativeInit();
    }
    public static void drop() {
        nativeDrop();
    }
    public static boolean status() {
        return nativeStatus();
    }
    public static boolean updateConfig(String filename) {
        return nativeUpdateConfig(filename);
    }
    // Preprocessor.
    public static boolean[] processKey(String key, int state) {
        String _key = serializeKey(key);
        String _state = serializeState(state);

        return nativeProcessKey(_key, _state);
    }
    public static String popStack() {
        return nativePopStack();
    }
    // Translator.
    public static String getInput() {
        return nativeGetInput();
    }

}
