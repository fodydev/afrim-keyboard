package cm.pythonbrad.afrim.latin;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cm.pythonbrad.afrim.R;
import cm.pythonbrad.afrim.core.Suggestion;

/** A horizontal strip displaying the afrim suggestions above the keyboard. */
public final class SuggestionStripView extends HorizontalScrollView {
  public interface Listener {
    void onPickSuggestion(String text);
  }

  private final LinearLayout mSuggestionsContainer;
  private final int mTextColor;
  private final int mHintColor;
  private final float mTextSize;
  private final int mItemPadding;
  private Listener mListener;

  public SuggestionStripView(final Context context, final AttributeSet attrs) {
    super(context, attrs);

    // Use the same colors as the keys of the keyboard.
    final TypedArray keyAttr = context.obtainStyledAttributes(attrs, R.styleable.Keyboard_Key);
    mTextColor = keyAttr.getColor(R.styleable.Keyboard_Key_keyTextColor, Color.BLACK);
    mHintColor = keyAttr.getColor(R.styleable.Keyboard_Key_keyHintLetterColor, Color.GRAY);
    keyAttr.recycle();

    mTextSize = getResources().getDimension(R.dimen.config_suggestion_strip_text_size);
    mItemPadding = getResources().getDimensionPixelSize(R.dimen.config_suggestion_item_padding);

    setHorizontalScrollBarEnabled(false);
    mSuggestionsContainer = new LinearLayout(context);
    mSuggestionsContainer.setOrientation(LinearLayout.HORIZONTAL);
    addView(
        mSuggestionsContainer,
        new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
  }

  public void setListener(final Listener listener) {
    mListener = listener;
  }

  public void setSuggestions(final Suggestion[] suggestions) {
    mSuggestionsContainer.removeAllViews();
    scrollTo(0, 0);

    for (final Suggestion suggestion : suggestions) {
      for (final String text : suggestion.getTexts()) {
        if (TextUtils.isEmpty(text)) continue;
        mSuggestionsContainer.addView(createSuggestionView(text, suggestion.getRemainingCode()));
      }
    }
  }

  public void clear() {
    mSuggestionsContainer.removeAllViews();
  }

  private TextView createSuggestionView(final String text, final String remainingCode) {
    final SpannableStringBuilder label = new SpannableStringBuilder(text);
    // Show the code which remains to type to reach this suggestion.
    if (!TextUtils.isEmpty(remainingCode)) {
      final int start = label.length();
      label.append(" ").append(remainingCode);
      label.setSpan(
          new ForegroundColorSpan(mHintColor),
          start,
          label.length(),
          Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    final TextView view = new TextView(getContext());
    view.setText(label);
    view.setTextColor(mTextColor);
    view.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
    view.setGravity(Gravity.CENTER);
    view.setSingleLine();
    view.setPadding(mItemPadding, 0, mItemPadding, 0);
    view.setMinWidth(getResources().getDimensionPixelSize(R.dimen.config_suggestion_min_width));

    final TypedValue outValue = new TypedValue();
    if (getContext()
        .getTheme()
        .resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)) {
      view.setBackgroundResource(outValue.resourceId);
    }

    view.setOnClickListener(
        v -> {
          if (mListener != null) mListener.onPickSuggestion(text);
        });
    view.setLayoutParams(
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
    return view;
  }
}
