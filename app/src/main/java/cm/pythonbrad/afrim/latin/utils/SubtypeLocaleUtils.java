/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (C) 2025 Raimondas Rimkus
 * Copyright (C) 2025 Shunnuo
 * Copyright (C) 2025 GoodOldAbe
 * Copyright (C) 2025 Camille019
 * Copyright (C) 2023 Md. Rifat Hasan Jihan
 * Copyright (C) 2022 Md Rasel Hossain
 * Copyright (C) 2021 HanefiAcar
 * Copyright (C) 2021 wittmane
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

package cm.pythonbrad.afrim.latin.utils;

import android.content.res.Resources;
import cm.pythonbrad.afrim.R;
import cm.pythonbrad.afrim.data.DataManager;
import cm.pythonbrad.afrim.latin.Subtype;
import cm.pythonbrad.afrim.latin.common.LocaleUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Utility methods for building subtypes for the supported locales. */
public final class SubtypeLocaleUtils {

  private SubtypeLocaleUtils() {
    // This utility class is not publicly instantiable.
  }

  private static final String LOCALE_ENGLISH_UNITED_STATES = "en_US";
  // Variant of the locales built from the afrim datasets (eg: "fmp__afrim").
  public static final String VARIANT_AFRIM = "afrim";

  public static final String LAYOUT_QWERTY = "qwerty";
  private static final String LAYOUT_QWERTY_DISPLAY_NAME = "QWERTY";

  /**
   * Get a list of all of the currently supported subtype locales. Each dataset of the afrim-data
   * folder is loaded as a locale.
   *
   * @param resources the resources to use.
   * @return a list of subtype strings in the format of "ll_cc_variant" where "ll" is a language
   *     code, "cc" is a country code.
   */
  public static List<String> getSupportedLocales(final Resources resources) {
    DataManager.reloadDatasets(resources.getAssets());
    final ArrayList<String> locales = new ArrayList<>();
    locales.add(LOCALE_ENGLISH_UNITED_STATES);
    for (final String code : DataManager.getDatasets(resources.getAssets()).keySet()) {
      locales.add(getAfrimLocaleString(code));
    }
    return locales;
  }

  /**
   * Get the locale string of an afrim dataset.
   *
   * @param code the dataset code.
   * @return the locale string in the format of "code__afrim".
   */
  private static String getAfrimLocaleString(final String code) {
    return LocaleUtils.getLocaleString(new Locale(code, "", VARIANT_AFRIM));
  }

  /**
   * Get the display name of the afrim dataset behind a locale.
   *
   * @param locale the locale string to look up.
   * @param resources the resources to use.
   * @return the dataset name or null if the locale isn't backed by a dataset.
   */
  public static String getAfrimDatasetName(final String locale, final Resources resources) {
    final Locale localeObject = LocaleUtils.constructLocaleFromString(locale);
    if (!VARIANT_AFRIM.equals(localeObject.getVariant())) {
      return null;
    }
    final Map<String, String> datasets = DataManager.getDatasets(resources.getAssets());
    return datasets.get(localeObject.getLanguage());
  }

  /**
   * Get a list of all of the supported subtypes for a locale.
   *
   * @param locale the locale string for the subtypes to look up.
   * @param resources the resources to use.
   * @return the list of subtypes for the specified locale.
   */
  public static List<Subtype> getSubtypes(final String locale, final Resources resources) {
    final List<Subtype> subtypes = new ArrayList<>();
    final Subtype subtype = getDefaultSubtype(locale, resources);
    if (subtype != null) {
      subtypes.add(subtype);
    }
    return subtypes;
  }

  /**
   * Get the default subtype for a locale.
   *
   * @param locale the locale string for the subtype to look up.
   * @param resources the resources to use.
   * @return the default subtype for the specified locale or null if the locale isn't supported.
   */
  public static Subtype getDefaultSubtype(final String locale, final Resources resources) {
    if (LOCALE_ENGLISH_UNITED_STATES.equals(locale)) {
      return new Subtype(locale, LAYOUT_QWERTY, LAYOUT_QWERTY_DISPLAY_NAME, false, resources);
    }
    if (getAfrimDatasetName(locale, resources) != null) {
      return new Subtype(locale, LAYOUT_QWERTY, R.string.subtype_afrim, true, resources);
    }
    return null;
  }

  /**
   * Get a subtype for a specific locale and keyboard layout.
   *
   * @param locale the locale string for the subtype to look up.
   * @param layoutSet the keyboard layout set name for the subtype.
   * @param resources the resources to use.
   * @return the subtype for the specified locale and layout or null if it isn't supported.
   */
  public static Subtype getSubtype(
      final String locale, final String layoutSet, final Resources resources) {
    if (!LAYOUT_QWERTY.equals(layoutSet)) {
      return null;
    }
    return getDefaultSubtype(locale, resources);
  }

  /**
   * Get the list subtypes corresponding to the system's languages.
   *
   * @param resources the resources to use.
   * @return the default list of subtypes based on the system's languages.
   */
  public static List<Subtype> getDefaultSubtypes(final Resources resources) {
    // there needs to be at least one default subtype
    return getSubtypes(LOCALE_ENGLISH_UNITED_STATES, resources);
  }
}
