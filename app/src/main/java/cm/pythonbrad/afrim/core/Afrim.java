package cm.pythonbrad.afrim.core;

import androidx.annotation.NonNull;

/**
 * Afrim Input method wrapper.
 */
public final class Afrim {
    private static final String TAG = Afrim.class.getSimpleName();
    private static String currentConfigFile = "";
    private static boolean state;
    private static final Afrim sInstance = new Afrim();

    // Load the native library "libafrim_jni.so".
    static {
        System.loadLibrary("afrim_jni");
    }

    private Afrim() {
        // Intentional empty constructor for singleton.
    }

    public static void init() {
        sInstance.initInternal();
    }

    private void initInternal() {
        nativeInit();
    }

    public static Afrim getInstance() {
        return sInstance;
    }

    // Native functions implemented in Rust.
    // Singleton.
    private static native boolean nativeUpdateConfig(@NonNull String filename);
    private static native void nativeInit();
    private static native boolean nativeIsInit();
    private static native void nativeDrop();
    // Preprocessor.
    private static native boolean[] nativeProcessKey(@NonNull String key, @NonNull String state);
    private static native void nativeCommitText(@NonNull String text);
    private static native String nativeNextCommand();
    private static native void nativeClear();
    private static native String nativeGetInput();
    // Translator.
    private static native Object[] nativeTranslateText();

    // Native function implemented in Java.
    // Singleton.
    public void drop() {
        nativeDrop();
    }
    public boolean isInit() {
        return nativeIsInit();
    }
    public boolean updateConfig(String configFile) {
        if (currentConfigFile.equals(configFile)) {
            return true;
        }

        currentConfigFile = configFile;
        return nativeUpdateConfig(configFile);
    }

    // Preprocessor.
    public void clear() {
        nativeClear();
    }
    public boolean[] processKey(String key, int state) {
        String _key = Serializer.keyToString(key);
        String _state = Serializer.stateToString(state);

        return nativeProcessKey(_key, _state);
    }
    public Command getCommand() {
        final String cmd = nativeNextCommand();
        return Deserializer.fromCommand(cmd);
    }
    public String getInput() {
        return nativeGetInput();
    }
    public void commitText(String text) {
        nativeCommitText(text);
    }

    // Translator.
    // TODO: display suggestions
    // For the moment, we just manage the fully matched predicate
    public String getSuggestion() {
        Object[] predicates = nativeTranslateText();

        if (predicates.length == 0) return null;
        String[] predicate = (String[]) predicates[0];

        if (!predicate[3].equals("true")) return null;
        return predicate[2];
    }
    // Custom
    public void setState(boolean value) {
        state = value;
    }
    public boolean canOperate() {
        return state;
    }
}
