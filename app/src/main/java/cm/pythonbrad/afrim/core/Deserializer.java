package cm.pythonbrad.afrim.core;

import org.json.JSONException;
import org.json.JSONObject;

public class Deserializer {
  public static Command fromCommand(String cmd) {
    switch (cmd) {
      case "\"Pause\"":
        return new Command(Command.PAUSE);
      case "\"Resume\"":
        return new Command(Command.RESUME);
      case "\"Delete\"":
        return new Command(Command.DELETE);
      case "\"CleanDelete\"":
        return new Command(Command.CLEAN_DELETE);
      case "\"NOP\"":
        return new Command((Command.NOP));
      default:
        try {
          final String text = new JSONObject(cmd).getString("CommitText");
          return new Command(Command.COMMIT, text);
        } catch (JSONException e) {
          return new Command(Command.UNKNOWN);
        }
    }
  }
}
