package cm.pythonbrad.afrim.core;

// TODO: Finalize the serializer.
public class Serializer {
    public static String serializeKey(String key) {
        if (key.length() > 1) {
            key = key.substring(0, 1).toUpperCase()+key.substring(1);
        }

        switch (key) {
            case "Space": return " ";
            case "Delete": return "Backspace";
            case "Symbol": return "Shift";
            default: return key;
        }
    }
    public static String serializeState(int state) {
        if (state == 0) return "\"Down\"";
        return "\"Up\"";
    }
}
