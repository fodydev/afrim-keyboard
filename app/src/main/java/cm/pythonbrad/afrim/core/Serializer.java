package cm.pythonbrad.afrim.core;

public class Serializer {
  private Serializer() {
    // This class is not publicly instantiable.
  }

  public static String keyToString(String key) {
    if (key.length() > 1) {
      key = key.substring(0, 1).toUpperCase() + key.substring(1);
    }

    switch (key) {
      case "LanguageSwitch":
        return "Meta";
      case "Space":
        return " ";
      case "Delete":
        return "Backspace";
      case "Symbol":
        return "Shift";
      default:
        return key;
    }
  }

  public static String stateToString(int state) {
    if (state == 0) return "\"Down\"";
    return "\"Up\"";
  }
}
