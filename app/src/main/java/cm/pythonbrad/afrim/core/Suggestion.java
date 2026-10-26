package cm.pythonbrad.afrim.core;

import androidx.annotation.NonNull;

/** A suggestion (predicate) produced by the afrim translator. */
public final class Suggestion {
  private final String code;
  private final String remainingCode;
  private final String[] texts;
  private final boolean canCommit;

  public Suggestion(String code, String remainingCode, String[] texts, boolean canCommit) {
    this.code = code;
    this.remainingCode = remainingCode;
    this.texts = texts;
    this.canCommit = canCommit;
  }

  /** Returns the code matched by this suggestion. */
  public String getCode() {
    return code;
  }

  /** Returns the code which remains to type to fully match this suggestion. */
  public String getRemainingCode() {
    return remainingCode;
  }

  /** Returns the candidate texts of this suggestion. */
  public String[] getTexts() {
    return texts;
  }

  /** Whether this suggestion should be committed automatically. */
  public boolean canCommit() {
    return canCommit;
  }

  @NonNull public String toString() {
    return code + "(" + remainingCode + ") -> " + android.text.TextUtils.join("|", texts);
  }
}
