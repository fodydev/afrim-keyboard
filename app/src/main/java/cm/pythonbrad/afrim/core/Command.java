package cm.pythonbrad.afrim.core;

import androidx.annotation.NonNull;

public class Command {
  public static final int NOP = 0;
  public static final int PAUSE = 1;
  public static final int RESUME = 2;
  public static final int DELETE = 3;
  public static final int COMMIT = 4;
  public static final int CLEAN_DELETE = 5;
  public static final int UNKNOWN = -1;
  private final int code;
  private final String data;

  public Command(int code) {
    this.code = code;
    this.data = null;
  }

  public Command(int code, String data) {
    this.code = code;
    this.data = data;
  }

  public int getCode() {
    return code;
  }

  public String getData() {
    return data;
  }

  @NonNull public String toString() {
    switch (this.code) {
      case NOP:
        return "NOP";
      case PAUSE:
        return "PAUSE";
      case RESUME:
        return "RESUME";
      case DELETE:
        return "DELETE";
      case COMMIT:
        return "COMMIT";
      case CLEAN_DELETE:
        return "CLEAN_DELETE";
      default:
        return "UNKNOWN";
    }
  }
}
