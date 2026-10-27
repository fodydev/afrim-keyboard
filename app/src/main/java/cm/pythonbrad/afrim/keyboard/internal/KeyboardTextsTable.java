/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (C) 2025 Raimondas Rimkus
 * Copyright (C) 2023 soshial
 * Copyright (C) 2023 Md. Rifat Hasan Jihan
 * Copyright (C) 2021 wittmane
 * Copyright (C) 2021 Luis
 * Copyright (C) 2020 dioraman
 * Copyright (C) 2019 Emmanuel
 * Copyright (C) 2018 Saadat Mateen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cm.pythonbrad.afrim.keyboard.internal;

import java.util.HashMap;
import java.util.Locale;

public final class KeyboardTextsTable {
  // Name to index map.
  private static final HashMap<String, Integer> sNameToIndexesMap = new HashMap<>();
  // Locale to texts table map.
  private static final HashMap<String, String[]> sLocaleToTextsTableMap = new HashMap<>();

  public static String getText(final String name, final String[] textsTable) {
    final Integer indexObj = sNameToIndexesMap.get(name);
    if (indexObj == null) {
      throw new RuntimeException("Unknown text name=" + name);
    }
    final int index = indexObj;
    final String text = (index < textsTable.length) ? textsTable[index] : null;
    if (text != null) {
      return text;
    }
    // Sanity check.
    if (index >= 0 && index < TEXTS_DEFAULT.length) {
      return TEXTS_DEFAULT[index];
    }
    // Throw exception for debugging purpose.
    throw new RuntimeException("Illegal index=" + index + " for name=" + name);
  }

  public static String[] getTextsTable(final Locale locale) {
    final String localeKey = locale.toString();
    if (sLocaleToTextsTableMap.containsKey(localeKey)) {
      return sLocaleToTextsTableMap.get(localeKey);
    }
    final String languageKey = locale.getLanguage();
    if (sLocaleToTextsTableMap.containsKey(languageKey)) {
      return sLocaleToTextsTableMap.get(languageKey);
    }
    return TEXTS_DEFAULT;
  }

  private static final String[] NAMES = {
    //  /* index:histogram */ "name",
    /*   0: 1 */ "morekeys_a",
    /*   1: 1 */ "morekeys_o",
    /*   2: 1 */ "morekeys_e",
    /*   3: 1 */ "morekeys_u",
    /*   4: 0 */ "keylabel_to_alpha",
    /*   5: 1 */ "morekeys_i",
    /*   6: 1 */ "morekeys_n",
    /*   7: 1 */ "morekeys_c",
    /*   8: 0 */ "double_quotes",
    /*   9: 1 */ "morekeys_s",
    /*  10: 0 */ "single_quotes",
    /*  11: 0 */ "keyspec_currency",
    /*  12: 0 */ "morekeys_y",
    /*  13: 0 */ "morekeys_z",
    /*  14: 0 */ "morekeys_d",
    /*  15: 0 */ "morekeys_t",
    /*  16: 0 */ "morekeys_l",
    /*  17: 0 */ "morekeys_g",
    /*  18: 0 */ "single_angle_quotes",
    /*  19: 0 */ "double_angle_quotes",
    /*  20: 0 */ "morekeys_r",
    /*  21: 0 */ "morekeys_k",
    /*  22: 0 */ "morekeys_cyrillic_ie",
    /*  23: 0 */ "keyspec_nordic_row1_11",
    /*  24: 0 */ "keyspec_nordic_row2_10",
    /*  25: 0 */ "keyspec_nordic_row2_11",
    /*  26: 0 */ "morekeys_nordic_row2_10",
    /*  27: 0 */ "keyspec_east_slavic_row1_9",
    /*  28: 0 */ "keyspec_east_slavic_row2_2",
    /*  29: 0 */ "keyspec_east_slavic_row2_11",
    /*  30: 0 */ "keyspec_east_slavic_row3_5",
    /*  31: 0 */ "morekeys_cyrillic_soft_sign",
    /*  32: 0 */ "keyspec_symbols_1",
    /*  33: 0 */ "keyspec_symbols_2",
    /*  34: 0 */ "keyspec_symbols_3",
    /*  35: 0 */ "keyspec_symbols_4",
    /*  36: 0 */ "keyspec_symbols_5",
    /*  37: 0 */ "keyspec_symbols_6",
    /*  38: 0 */ "keyspec_symbols_7",
    /*  39: 0 */ "keyspec_symbols_8",
    /*  40: 0 */ "keyspec_symbols_9",
    /*  41: 0 */ "keyspec_symbols_0",
    /*  42: 0 */ "keylabel_to_symbol",
    /*  43: 0 */ "additional_morekeys_symbols_1",
    /*  44: 0 */ "additional_morekeys_symbols_2",
    /*  45: 0 */ "additional_morekeys_symbols_3",
    /*  46: 0 */ "additional_morekeys_symbols_4",
    /*  47: 0 */ "additional_morekeys_symbols_5",
    /*  48: 0 */ "additional_morekeys_symbols_6",
    /*  49: 0 */ "additional_morekeys_symbols_7",
    /*  50: 0 */ "additional_morekeys_symbols_8",
    /*  51: 0 */ "additional_morekeys_symbols_9",
    /*  52: 0 */ "additional_morekeys_symbols_0",
    /*  53: 0 */ "morekeys_tablet_period",
    /*  54: 0 */ "morekeys_nordic_row2_11",
    /*  55: 0 */ "morekeys_punctuation",
    /*  56: 0 */ "keyspec_tablet_comma",
    /*  57: 0 */ "keyspec_period",
    /*  58: 0 */ "morekeys_period",
    /*  59: 0 */ "keyspec_tablet_period",
    /*  60: 0 */ "keyspec_swiss_row1_11",
    /*  61: 0 */ "keyspec_swiss_row2_10",
    /*  62: 0 */ "keyspec_swiss_row2_11",
    /*  63: 0 */ "morekeys_swiss_row1_11",
    /*  64: 0 */ "morekeys_swiss_row2_10",
    /*  65: 0 */ "morekeys_swiss_row2_11",
    /*  66: 0 */ "morekeys_star",
    /*  67: 0 */ "keyspec_left_parenthesis",
    /*  68: 0 */ "keyspec_right_parenthesis",
    /*  69: 0 */ "keyspec_left_square_bracket",
    /*  70: 0 */ "keyspec_right_square_bracket",
    /*  71: 0 */ "keyspec_left_curly_bracket",
    /*  72: 0 */ "keyspec_right_curly_bracket",
    /*  73: 0 */ "keyspec_less_than",
    /*  74: 0 */ "keyspec_greater_than",
    /*  75: 0 */ "keyspec_less_than_equal",
    /*  76: 0 */ "keyspec_greater_than_equal",
    /*  77: 0 */ "keyspec_left_double_angle_quote",
    /*  78: 0 */ "keyspec_right_double_angle_quote",
    /*  79: 0 */ "keyspec_left_single_angle_quote",
    /*  80: 0 */ "keyspec_right_single_angle_quote",
    /*  81: 0 */ "keyspec_comma",
    /*  82: 0 */ "morekeys_tablet_comma",
    /*  83: 0 */ "keyhintlabel_period",
    /*  84: 0 */ "morekeys_question",
    /*  85: 0 */ "morekeys_h",
    /*  86: 0 */ "morekeys_w",
    /*  87: 0 */ "morekeys_east_slavic_row2_2",
    /*  88: 0 */ "morekeys_cyrillic_u",
    /*  89: 0 */ "morekeys_cyrillic_en",
    /*  90: 0 */ "morekeys_cyrillic_ghe",
    /*  91: 0 */ "morekeys_cyrillic_o",
    /*  92: 0 */ "morekeys_cyrillic_i",
    /*  93: 0 */ "keyspec_south_slavic_row1_6",
    /*  94: 0 */ "keyspec_south_slavic_row2_11",
    /*  95: 0 */ "keyspec_south_slavic_row3_8",
    /*  96: 0 */ "morekeys_tablet_punctuation",
    /*  97: 0 */ "keyspec_spanish_row2_10",
    /*  98: 0 */ "morekeys_bullet",
    /*  99: 0 */ "morekeys_left_parenthesis",
    /* 100: 0 */ "morekeys_right_parenthesis",
    /* 101: 0 */ "morekeys_arabic_diacritics",
    /* 102: 0 */ "keyhintlabel_tablet_comma",
    /* 103: 0 */ "keyhintlabel_tablet_period",
    /* 104: 0 */ "keyspec_symbols_question",
    /* 105: 0 */ "keyspec_symbols_semicolon",
    /* 106: 0 */ "keyspec_symbols_percent",
    /* 107: 0 */ "morekeys_symbols_semicolon",
    /* 108: 0 */ "morekeys_symbols_percent",
    /* 109: 0 */ "label_pause_key",
    /* 110: 0 */ "label_wait_key",
    /* 111: 0 */ "morekeys_v",
    /* 112: 0 */ "morekeys_j",
    /* 113: 0 */ "morekeys_q",
    /* 114: 0 */ "morekeys_x",
    /* 115: 0 */ "keyspec_q",
    /* 116: 0 */ "keyspec_w",
    /* 117: 0 */ "keyspec_y",
    /* 118: 0 */ "keyspec_x",
    /* 119: 0 */ "morekeys_east_slavic_row2_11",
    /* 120: 0 */ "morekeys_cyrillic_ka",
    /* 121: 0 */ "morekeys_cyrillic_a",
    /* 122: 0 */ "morekeys_currency_dollar",
    /* 123: 0 */ "morekeys_plus",
    /* 124: 0 */ "morekeys_less_than",
    /* 125: 0 */ "morekeys_greater_than",
    /* 126: 0 */ "morekeys_exclamation",
    /* 127: 0 */ "morekeys_currency_generic",
    /* 128: 0 */ "morekeys_symbols_1",
    /* 129: 0 */ "morekeys_symbols_2",
    /* 130: 0 */ "morekeys_symbols_3",
    /* 131: 0 */ "morekeys_symbols_4",
    /* 132: 0 */ "morekeys_symbols_5",
    /* 133: 0 */ "morekeys_symbols_6",
    /* 134: 0 */ "morekeys_symbols_7",
    /* 135: 0 */ "morekeys_symbols_8",
    /* 136: 0 */ "morekeys_symbols_9",
    /* 137: 0 */ "morekeys_symbols_0",
    /* 138: 0 */ "morekeys_am_pm",
    /* 139: 0 */ "keyspec_settings",
    /* 140: 0 */ "keyspec_action_next",
    /* 141: 0 */ "keyspec_action_previous",
    /* 142: 0 */ "keylabel_to_more_symbol",
    /* 143: 0 */ "keylabel_tablet_to_more_symbol",
    /* 144: 0 */ "keylabel_to_phone_numeric",
    /* 145: 0 */ "keylabel_to_phone_symbols",
    /* 146: 0 */ "keylabel_time_am",
    /* 147: 0 */ "keylabel_time_pm",
    /* 148: 0 */ "keyspecs_left_parenthesis_more_keys",
    /* 149: 0 */ "keyspecs_right_parenthesis_more_keys",
    /* 150: 0 */ "single_laqm_raqm",
    /* 151: 0 */ "single_raqm_laqm",
    /* 152: 0 */ "double_laqm_raqm",
    /* 153: 0 */ "double_raqm_laqm",
    /* 154: 0 */ "single_lqm_rqm",
    /* 155: 0 */ "single_9qm_lqm",
    /* 156: 0 */ "single_9qm_rqm",
    /* 157: 0 */ "single_rqm_9qm",
    /* 158: 0 */ "double_lqm_rqm",
    /* 159: 0 */ "double_9qm_lqm",
    /* 160: 0 */ "double_9qm_rqm",
    /* 161: 0 */ "double_rqm_9qm",
    /* 162: 0 */ "morekeys_single_quote",
    /* 163: 0 */ "morekeys_double_quote",
    /* 164: 0 */ "morekeys_tablet_double_quote",
    /* 165: 0 */ "morekeys_cyrillic_ya",
    /* 166: 0 */ "morekeys_cyrillic_yu",
  };

  private static final String EMPTY = "";

  /* Default texts */
  private static final String[] TEXTS_DEFAULT = {
    /* morekeys_a ~ */
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    /* ~ morekeys_u */
    // Label for "switch to alphabetic" key.
    /* keylabel_to_alpha */ "ABC",
    /* morekeys_i ~ */
    EMPTY,
    EMPTY,
    EMPTY,
    /* ~ morekeys_c */
    /* double_quotes */ "!text/double_lqm_rqm",
    /* morekeys_s */ EMPTY,
    /* single_quotes */ "!text/single_lqm_rqm",
    /* keyspec_currency */ "$",
    /* morekeys_y ~ */
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    /* ~ morekeys_g */
    /* single_angle_quotes */ "!text/single_laqm_raqm",
    /* double_angle_quotes */ "!text/double_laqm_raqm",
    /* morekeys_r ~ */
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    /* ~ morekeys_cyrillic_soft_sign */
    /* keyspec_symbols_1 */ "1",
    /* keyspec_symbols_2 */ "2",
    /* keyspec_symbols_3 */ "3",
    /* keyspec_symbols_4 */ "4",
    /* keyspec_symbols_5 */ "5",
    /* keyspec_symbols_6 */ "6",
    /* keyspec_symbols_7 */ "7",
    /* keyspec_symbols_8 */ "8",
    /* keyspec_symbols_9 */ "9",
    /* keyspec_symbols_0 */ "0",
    // Label for "switch to symbols" key.
    /* keylabel_to_symbol */ "?123",
    /* additional_morekeys_symbols_1 ~ */
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    /* ~ additional_morekeys_symbols_0 */
    /* morekeys_tablet_period */ "!text/morekeys_tablet_punctuation",
    /* morekeys_nordic_row2_11 */ EMPTY,
    /* morekeys_punctuation */ "!autoColumnOrder!8,\\,,?,!,#,!text/keyspec_right_parenthesis,!text/keyspec_left_parenthesis,/,;,',@,:,-,\",+,\\%,&",
    /* keyspec_tablet_comma */ ",",
    // Period key
    /* keyspec_period */ ".",
    /* morekeys_period */ "!text/morekeys_punctuation",
    /* keyspec_tablet_period */ ".",
    /* keyspec_swiss_row1_11 ~ */
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    /* ~ morekeys_swiss_row2_11 */
    // U+2020: "†" DAGGER
    // U+2021: "‡" DOUBLE DAGGER
    // U+2605: "★" BLACK STAR
    /* morekeys_star */ "\u2020,\u2021,\u2605",
    // The all letters need to be mirrored are found at
    // http://www.unicode.org/Public/6.1.0/ucd/BidiMirroring.txt
    // U+2039: "‹" SINGLE LEFT-POINTING ANGLE QUOTATION MARK
    // U+203A: "›" SINGLE RIGHT-POINTING ANGLE QUOTATION MARK
    // U+2264: "≤" LESS-THAN OR EQUAL TO
    // U+2265: "≥" GREATER-THAN EQUAL TO
    // U+00AB: "«" LEFT-POINTING DOUBLE ANGLE QUOTATION MARK
    // U+00BB: "»" RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK
    /* keyspec_left_parenthesis */ "(",
    /* keyspec_right_parenthesis */ ")",
    /* keyspec_left_square_bracket */ "[",
    /* keyspec_right_square_bracket */ "]",
    /* keyspec_left_curly_bracket */ "{",
    /* keyspec_right_curly_bracket */ "}",
    /* keyspec_less_than */ "<",
    /* keyspec_greater_than */ ">",
    /* keyspec_less_than_equal */ "\u2264",
    /* keyspec_greater_than_equal */ "\u2265",
    /* keyspec_left_double_angle_quote */ "\u00AB",
    /* keyspec_right_double_angle_quote */ "\u00BB",
    /* keyspec_left_single_angle_quote */ "\u2039",
    /* keyspec_right_single_angle_quote */ "\u203A",
    // Comma key
    /* keyspec_comma */ ",",
    /* morekeys_tablet_comma */ EMPTY,
    /* keyhintlabel_period */ EMPTY,
    // U+00BF: "¿" INVERTED QUESTION MARK
    /* morekeys_question */ "\u00BF",
    /* morekeys_h ~ */
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    /* ~ keyspec_south_slavic_row3_8 */
    /* morekeys_tablet_punctuation */ "!autoColumnOrder!7,\\,,',#,!text/keyspec_right_parenthesis,!text/keyspec_left_parenthesis,/,;,@,:,-,\",+,\\%,&",
    // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
    /* keyspec_spanish_row2_10 */ "\u00F1",
    // U+266A: "♪" EIGHTH NOTE
    // U+2665: "♥" BLACK HEART SUIT
    // U+2660: "♠" BLACK SPADE SUIT
    // U+2666: "♦" BLACK DIAMOND SUIT
    // U+2663: "♣" BLACK CLUB SUIT
    /* morekeys_bullet */ "\u266A,\u2665,\u2660,\u2666,\u2663",
    /* morekeys_left_parenthesis */ "!fixedColumnOrder!3,!text/keyspecs_left_parenthesis_more_keys",
    /* morekeys_right_parenthesis */ "!fixedColumnOrder!3,!text/keyspecs_right_parenthesis_more_keys",
    /* morekeys_arabic_diacritics ~ */
    EMPTY,
    EMPTY,
    EMPTY,
    /* ~ keyhintlabel_tablet_period */
    /* keyspec_symbols_question */ "?",
    /* keyspec_symbols_semicolon */ ";",
    /* keyspec_symbols_percent */ "%",
    /* morekeys_symbols_semicolon */ EMPTY,
    // U+2030: "‰" PER MILLE SIGN
    /* morekeys_symbols_percent */ "\u2030",
    /* label_pause_key */ "!string/label_pause_key",
    /* label_wait_key */ "!string/label_wait_key",
    /* morekeys_v ~ */
    EMPTY,
    EMPTY,
    EMPTY,
    EMPTY,
    /* ~ morekeys_x */
    /* keyspec_q */ "q",
    /* keyspec_w */ "w",
    /* keyspec_y */ "y",
    /* keyspec_x */ "x",
    /* morekeys_east_slavic_row2_11 ~ */
    EMPTY,
    EMPTY,
    EMPTY,
    /* ~ morekeys_cyrillic_a */
    // U+00A2: "¢" CENT SIGN
    // U+00A3: "£" POUND SIGN
    // U+20AC: "€" EURO SIGN
    // U+00A5: "¥" YEN SIGN
    // U+20B1: "₱" PESO SIGN
    /* morekeys_currency_dollar */ "\u00A2,\u00A3,\u20AC,\u00A5,\u20B1",
    // U+00B1: "±" PLUS-MINUS SIGN
    /* morekeys_plus */ "\u00B1",
    /* morekeys_less_than */ "!fixedColumnOrder!3,!text/keyspec_left_single_angle_quote,!text/keyspec_less_than_equal,!text/keyspec_left_double_angle_quote",
    /* morekeys_greater_than */ "!fixedColumnOrder!3,!text/keyspec_right_single_angle_quote,!text/keyspec_greater_than_equal,!text/keyspec_right_double_angle_quote",
    // U+00A1: "¡" INVERTED EXCLAMATION MARK
    /* morekeys_exclamation */ "\u00A1",
    /* morekeys_currency_generic */ "$,\u00A2,\u20AC,\u00A3,\u00A5,\u20B1",
    // U+00B9: "¹" SUPERSCRIPT ONE
    // U+00BD: "½" VULGAR FRACTION ONE HALF
    // U+2153: "⅓" VULGAR FRACTION ONE THIRD
    // U+00BC: "¼" VULGAR FRACTION ONE QUARTER
    // U+215B: "⅛" VULGAR FRACTION ONE EIGHTH
    /* morekeys_symbols_1 */ "\u00B9,\u00BD,\u2153,\u00BC,\u215B",
    // U+00B2: "²" SUPERSCRIPT TWO
    // U+2154: "⅔" VULGAR FRACTION TWO THIRDS
    /* morekeys_symbols_2 */ "\u00B2,\u2154",
    // U+00B3: "³" SUPERSCRIPT THREE
    // U+00BE: "¾" VULGAR FRACTION THREE QUARTERS
    // U+215C: "⅜" VULGAR FRACTION THREE EIGHTHS
    /* morekeys_symbols_3 */ "\u00B3,\u00BE,\u215C",
    // U+2074: "⁴" SUPERSCRIPT FOUR
    /* morekeys_symbols_4 */ "\u2074",
    // U+215D: "⅝" VULGAR FRACTION FIVE EIGHTHS
    /* morekeys_symbols_5 */ "\u215D",
    /* morekeys_symbols_6 */ EMPTY,
    // U+215E: "⅞" VULGAR FRACTION SEVEN EIGHTHS
    /* morekeys_symbols_7 */ "\u215E",
    /* morekeys_symbols_8 */ EMPTY,
    /* morekeys_symbols_9 */ EMPTY,
    // U+207F: "ⁿ" SUPERSCRIPT LATIN SMALL LETTER N
    // U+2205: "∅" EMPTY SET
    /* morekeys_symbols_0 */ "\u207F,\u2205",
    /* morekeys_am_pm */ "!fixedColumnOrder!2,!hasLabels!,!text/keylabel_time_am,!text/keylabel_time_pm",
    /* keyspec_settings */ "!icon/paste_key|!code/key_paste,!icon/settings_key|!code/key_settings",
    /* keyspec_action_next */ "!code/key_action_next",
    /* keyspec_action_previous */ "!code/key_action_previous",
    // Label for "switch to more symbol" modifier key ("= \ <"). Must be short to fit on key!
    /* keylabel_to_more_symbol */ "= \\\\ <",
    // Label for "switch to more symbol" modifier key on tablets.  Must be short to fit on key!
    /* keylabel_tablet_to_more_symbol */ "~ [ <",
    // Label for "switch to phone numeric" key.  Must be short to fit on key!
    /* keylabel_to_phone_numeric */ "123",
    // Label for "switch to phone symbols" key.  Must be short to fit on key!
    // U+FF0A: "＊" FULLWIDTH ASTERISK
    // U+FF03: "＃" FULLWIDTH NUMBER SIGN
    /* keylabel_to_phone_symbols */ "\uFF0A\uFF03",
    // Key label for "ante meridiem"
    /* keylabel_time_am */ "AM",
    // Key label for "post meridiem"
    /* keylabel_time_pm */ "PM",
    /* keyspecs_left_parenthesis_more_keys */ "!text/keyspec_less_than,!text/keyspec_left_curly_bracket,!text/keyspec_left_square_bracket",
    /* keyspecs_right_parenthesis_more_keys */ "!text/keyspec_greater_than,!text/keyspec_right_curly_bracket,!text/keyspec_right_square_bracket",
    // The following characters don't need BIDI mirroring.
    // U+2018: "‘" LEFT SINGLE QUOTATION MARK
    // U+2019: "’" RIGHT SINGLE QUOTATION MARK
    // U+201A: "‚" SINGLE LOW-9 QUOTATION MARK
    // U+201C: "“" LEFT DOUBLE QUOTATION MARK
    // U+201D: "”" RIGHT DOUBLE QUOTATION MARK
    // U+201E: "„" DOUBLE LOW-9 QUOTATION MARK
    // Abbreviations are:
    // laqm: LEFT-POINTING ANGLE QUOTATION MARK
    // raqm: RIGHT-POINTING ANGLE QUOTATION MARK
    // lqm: LEFT QUOTATION MARK
    // rqm: RIGHT QUOTATION MARK
    // 9qm: LOW-9 QUOTATION MARK
    // The following each quotation mark pair consist of
    // <opening quotation mark>, <closing quotation mark>
    // and is named after (single|double)_<opening quotation mark>_<closing quotation mark>.
    /* single_laqm_raqm */ "!text/keyspec_left_single_angle_quote,!text/keyspec_right_single_angle_quote",
    /* single_raqm_laqm */ "!text/keyspec_right_single_angle_quote,!text/keyspec_left_single_angle_quote",
    /* double_laqm_raqm */ "!text/keyspec_left_double_angle_quote,!text/keyspec_right_double_angle_quote",
    /* double_raqm_laqm */ "!text/keyspec_right_double_angle_quote,!text/keyspec_left_double_angle_quote",
    // The following each quotation mark triplet consists of
    // <another quotation mark>, <opening quotation mark>, <closing quotation mark>
    // and is named after (single|double)_<opening quotation mark>_<closing quotation mark>.
    /* single_lqm_rqm */ "\u201A,\u2018,\u2019",
    /* single_9qm_lqm */ "\u2019,\u201A,\u2018",
    /* single_9qm_rqm */ "\u2018,\u201A,\u2019",
    /* single_rqm_9qm */ "\u2018,\u2019,\u201A",
    /* double_lqm_rqm */ "\u201E,\u201C,\u201D",
    /* double_9qm_lqm */ "\u201D,\u201E,\u201C",
    /* double_9qm_rqm */ "\u201C,\u201E,\u201D",
    /* double_rqm_9qm */ "\u201C,\u201D,\u201E",
    /* morekeys_single_quote */ "!fixedColumnOrder!5,!text/single_quotes,!text/single_angle_quotes",
    /* morekeys_double_quote */ "!fixedColumnOrder!5,!text/double_quotes,!text/double_angle_quotes",
    /* morekeys_tablet_double_quote */ "!fixedColumnOrder!6,!text/double_quotes,!text/single_quotes,!text/double_angle_quotes,!text/single_angle_quotes",
    /* morekeys_cyrillic_ya */ EMPTY,
    /* morekeys_cyrillic_yu */ EMPTY,
  };

  /* Locale en: English */
  private static final String[] TEXTS_en = {
    // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
    // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
    // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
    // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
    // U+00E6: "æ" LATIN SMALL LETTER AE
    // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
    // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
    // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
    /* morekeys_a */ "\u00E0,\u00E1,\u00E2,\u00E4,\u00E6,\u00E3,\u00E5,\u0101",
    // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
    // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
    // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
    // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
    // U+0153: "œ" LATIN SMALL LIGATURE OE
    // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
    // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
    // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
    /* morekeys_o */ "\u00F3,\u00F4,\u00F6,\u00F2,\u0153,\u00F8,\u014D,\u00F5",
    // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
    // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
    // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
    // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
    // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
    /* morekeys_e */ "\u00E9,\u00E8,\u00EA,\u00EB,\u0113",
    // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
    // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
    // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
    // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
    // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
    /* morekeys_u */ "\u00FA,\u00FB,\u00FC,\u00F9,\u016B",
    /* keylabel_to_alpha */ null,
    // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
    // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
    // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
    // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
    // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
    /* morekeys_i */ "\u00ED,\u00EE,\u00EF,\u012B,\u00EC",
    // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
    /* morekeys_n */ "\u00F1",
    // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
    /* morekeys_c */ "\u00E7",
    /* double_quotes */ null,
    // U+00DF: "ß" LATIN SMALL LETTER SHARP S
    /* morekeys_s */ "\u00DF",
  };

  private static final Object[] LOCALES_AND_TEXTS = {
    // "locale", TEXT_ARRAY,  /* numberOfNonNullText/lengthOf_TEXT_ARRAY localeName */
    "DEFAULT", TEXTS_DEFAULT, /* 176/176 DEFAULT */
    "en", TEXTS_en, /*   8/ 10 English */
  };

  static {
    for (int index = 0; index < NAMES.length; index++) {
      sNameToIndexesMap.put(NAMES[index], index);
    }

    for (int i = 0; i < LOCALES_AND_TEXTS.length; i += 2) {
      final String locale = (String) LOCALES_AND_TEXTS[i];
      final String[] textsTable = (String[]) LOCALES_AND_TEXTS[i + 1];
      sLocaleToTextsTableMap.put(locale, textsTable);

      // Uncomment to log locales where text is in range
      // int textToCheck = 95;
      // if (textsTable.length > textToCheck)
      //    Log.i("LocaleIndexCheck", textToCheck + " is used in " + locale + " total length: " +
      // textsTable.length);
    }
  }
}
