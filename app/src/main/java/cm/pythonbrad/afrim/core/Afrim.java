package cm.pythonbrad.afrim.core;

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
    private static native boolean nativeUpdateConfig(String filename);
    private static native void nativeInit();
    private static native boolean nativeCheck();
    private static native void nativeDrop();
    // Preprocessor.
    private static native boolean[] nativeProcessKey(String key, String state);
    private static native void nativeCommitText(String text);
    private static native String nativePopQueue();
    private static native void nativeClear();
    private static native String nativeGetInput();
    // Translator.
    private static native String[] nativeTranslate();

    // Native function implemented in Java.
    // Singleton.
    public void drop() {
        nativeDrop();
    }
    public boolean check() {
        return nativeCheck();
    }
    public boolean updateConfig(String configFile) {
        if (currentConfigFile.equals(configFile)) {
            return false;
        }

        currentConfigFile = configFile;
        return nativeUpdateConfig(configFile);
    }
    // Preprocessor.
    public void clear() {
        nativeClear();
        // Currently, the afrim don't clean it internally.
        if (check()) while (getCommand().getCode() != Command.NOP);
    }
    public boolean[] processKey(String key, int state) {
        String _key = Serializer.keyToString(key);
        String _state = Serializer.stateToString(state);

        return nativeProcessKey(_key, _state);
    }
    public Command getCommand() {
        final String cmd = nativePopQueue();
        return Deserializer.fromCommand(cmd);
    }
    public String getInput() {
        return nativeGetInput();
    }
    // Translator.
    public String[] getSuggestion() {
        return nativeTranslate();
    }
    // Custom
    public void setState(boolean value) {
        state = value;
    }
    public boolean getState() {
        return state;
    }
}
