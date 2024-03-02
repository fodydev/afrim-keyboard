package cm.pythonbrad.afrim.core;

import android.util.Log;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deserializer {
    private final static Pattern rCommitCommand = Pattern.compile("^CommitText\\(\"(.*)\"\\)$");
    public static Command fromCommand(String cmd) {
        Log.d("TAG", "fromCommand: "+cmd);
        switch (cmd) {
            case "Pause":
                return new Command(Command.PAUSE);
            case "Resume":
                return new Command(Command.RESUME);
            case "Delete":
                return new Command(Command.DELETE);
            case "":
                return new Command((Command.NOP));
            default:
               final Matcher data = rCommitCommand.matcher(cmd);
               if (data.find()) return new Command(Command.COMMIT, data.group(1));
               return new Command(Command.UNKNOWN);
        }
    }
}
