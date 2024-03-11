# Afrim Keyboard

Afrim IME for Android

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![GitHub release](https://img.shields.io/github/release/pythonbrad/afrim-keyboard.svg)](https://github.com/pythonbrad/afrim-keyboard/releases)
[![F-Droid release](https://img.shields.io/f-droid/v/cm.pythonbrad.afrim.svg)](https://f-droid.org/packages/cm.pythonbrad.afrimkeyboard)
[![Latest build](https://img.shields.io/github/last-commit/pythonbrad/afrim-keyboard.svg)](http://pythonbrad.github.io/afrim-keyboard/)

<center>
<video alt="afrim-keyboard-preview-03-2024" src="https://github.com/pythonbrad/afrim-keyboard/assets/45305909/20a22677-a628-479d-8068-3f34d5ff8149"></video>
</center>

## In development ⚠️

The project is in development and not ready to use.

## About

Afrim Keyboard is a versatile keyboard for Android users who wish to use African languages to type messages, compose emails and generally prefer to use them in addition to English on their phone.
 You can use this application to type anywhere in your phone that you would normally type in English

### Features:
- [x] Customizable dictionary
- [x] Easy to use interface
- [x] Adjustable keyboard height for more screen space
- [x] Number row
- [x] Swipe space to move pointer
- [x] Delete swipe
- [x] Custom theme colors
- [x] Ads-free
- [ ] Emojis
- [ ] GIFs
- [ ] Spell checker
- [ ] Swipe typing
- [ ] Auto-suggestion / Auto-correction / Auto-completion
- [ ] Full immersion mode for non-latin languages. (🚧 Experimental 🚧)

### What african languages (and layouts) are supported ?

- Amharic Keyboard - Transliteration
- Clafrica Keyboard - Transliteration
- Geez Keyboard - Transliteration
- Nufi Keyboard (Fe'efe'e) - Transliteration

### What is a keyboard layout ?
Afrim keyboard provides multiple "keyboard layouts".
 This means that you will have different ways to type in your native language.
 Transliteration allows you to type out words using English characters, but will automatically transform the words to your native language.
 For example, if you type "Afrim" in English while using Amharic transliteration keyboard, it will transform it to ዐፍሪም correctly.

## Downloads

- Stable Channel <br>
  [<img alt='Get it on F-Droid' src='https://fdroid.gitlab.io/artwork/badge/get-it-on.png' height='80px'/>](https://f-droid.org/packages/cm.pythonbrad.afrim)

- Canary Channel [Download](https://github.com/pythonbrad/afrim-keyboard/actions)

## Getting Started for developer

### About
Afrim Keyboard is an android IME based on the [Afrim](https://github.com/pythonbrad/afrim) rust library.
 It is designed to protect the native language of various local dialects of Africa and is a universal phonetic-based input method engine.

### Prepare

Android SDK and Android NDK should be correctly installed and configured.
 If you are new to Android development, please install Android Studio.

### Build

<details>
<summary>Prerequisites for Windows</summary>

Symbolic links will be created according to current build configurations, developers need:

- Enable [Developer Mode](https://learn.microsoft.com/en-us/windows/apps/get-started/enable-your-device-for-development) so that symlinks can be created without administrator privilege.

- Enable symlink support for `git`:

  ```powershell
  git config --global core.symlinks true
  ```

If you cannot or wouldn't like to enable anything, it doesn't matter.
 Copying will be used instead when error on creating symbolic links.

</details>

1. Clone this project and fetch all submodules:

```sh
git clone git@github.com:pythonbrad/afrim-keyboard.git
git submodule update --init --recursive
```

2. Debug version without signature:

On Linux or macOS, you may run:

```bash
make debug
```

On Windows, run:

```powershell
.\gradlew assembleDebug
```

3. Release version with signature:

Create `keystore.properties` file which contains following contents for [signing information](https://developer.android.com/studio/publish/app-signing.html):

```gradle.properties
storePassword=myStorePassword
keyPassword=mykeyPassword
keyAlias=myKeyAlias
storeFile=myStoreFileLocation
```

Then, on Linux or macOS, you may run:

```bash
make release
```

On Windows, run:

```powershell
.\gradlew assembleRelease
```

Run `make clean` on Linux or macOS, or run `.\gradlew clean` on Windows.

Other issues:

1. Try `make clean`
2. Make sure your repo is up-to-date. If one or more submodules are modified, also make sure they are compatible with the current version.
3. If the problem still exists(very unlikely), try to make a new clone.
4. Check if this is there is an issue/PR related to your problem. If yes, try their solutions.
5. If none of them works, you may make an issue to ask for help. (optional)

### Contributing

Your [contribution](CONTRIBUTING.md) are welcome ~ ! :tada:

### Credits

This keyboard is based on Simple keyboard. You can get the original source code from https://github.com/rkkr/simple-keyboard
