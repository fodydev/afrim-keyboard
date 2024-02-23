# Afrim Keyboard
Afrim IME for Android

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![GitHub release](https://img.shields.io/github/release/pythonbrad/afrim-keyboard.svg)](https://github.com/pythonbrad/afrim-keyboard/releases)
[![F-Droid release](https://img.shields.io/f-droid/v/com.pythonbrad.afrimkeyboard.svg)](https://f-droid.org/packages/com.pythonbrad.afrimkeyboard)
[![Latest build](https://img.shields.io/github/last-commit/pythonbrad/afrim-keyboard.svg)](http://pythonbrad.github.io/afrim-keyboard/)

<img src="images/screenshot-0.png"
      alt="closeup"
      width="500"/>

## In development ⚠️ 
The project is in development and not ready to use.

## About

Afrim is a frontend of open-source [Afrim](https://github.com/pythonbrad/afrim) input method framework and written in Java with JNI.
 It is designed to protect the native language of various local dialects of Africa and is a universal phonetic-based input method platform.

Features:
- Small size (<1MB)
- Adjustable keyboard height for more screen space
- Number row
- Swipe space to move pointer
- Delete swipe
- Custom theme colors
- Minimal permissions (only Vibrate)
- Ads-free

Feature it doesn't have and probably will never have:
- Emojis
- GIFs
- Spell checker
- Swipe typing

## Downloads

- Stable Channel <br>
  [<img alt='Get it on F-Droid' src='https://fdroid.gitlab.io/artwork/badge/get-it-on.png' height='80px'/>](https://f-droid.org/packages/com.pythonbrad.afrim)

- Canary Channel [Download](https://github.com/pythonbrad/afrim-keyboard/actions)

## Getting Started for developer

### Prepare

Android SDK and Android NDK should be correctly installed and configured. If you are new to Android development, please install Android Studio.

### Build

<details>
<summary>Prerequisites for Windows</summary>

Symbolic links will be created according to current build configurations, developers need:

- Enable [Developer Mode](https://learn.microsoft.com/en-us/windows/apps/get-started/enable-your-device-for-development) so that symlinks can be created without administrator privilege.

- Enable symlink support for `git`:

    ```powershell
    git config --global core.symlinks true
    ```

If you cannot or wouldn't like to enable anything, it doesn't matter. Copying will be used instead when error on creating symbolic links.

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

Licensed under Apache License Version 2

This keyboard is based on Simple keyboard. You can get the original source code in https://github.com/rkkr/simple-keyboard

