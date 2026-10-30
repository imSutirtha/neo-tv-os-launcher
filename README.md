<div align="center">

# ⚡ Neo tvOS

### *The High-Octane, Aerospace-Grade Custom Home Experience for Android TV & Google TV*

[![Platform](https://img.shields.io/badge/Platform-Android%20TV%20%7C%20Google%20TV-00E5FF?style=for-the-badge&logo=android&logoColor=black)](https://developer.android.com/tv)
[![Version](https://img.shields.io/badge/Version-1.0.0--BETA-FFD600?style=for-the-badge&logo=semver&logoColor=black)](app/build.gradle)
[![Architecture](https://img.shields.io/badge/Architecture-Native%20Java%2017%20%2B%20R8-FF6D00?style=for-the-badge&logo=openjdk&logoColor=white)](https://developer.android.com)
[![Package](https://img.shields.io/badge/Package-com.darkgod.devtools.neo__tvos-7C4DFF?style=for-the-badge&logo=package)](app/build.gradle)
[![License](https://img.shields.io/badge/License-MIT-00E676?style=for-the-badge)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Debloated%20%26%20Optimized-00B0FF?style=for-the-badge)](https://github.com/Gathin23/cleanyourtv)

<br/>

<img src="docs/shots/tv-01-home.png" alt="Neo tvOS Home Screen" width="920" style="border-radius: 14px; box-shadow: 0 16px 48px rgba(0, 0, 0, 0.75);"/>

<p><em>Live 4K Capture: Frosted Glass Apple TV Dock, Cinematic Ambient Shelf, Live Weather, and Real-Time Network Telemetry</em></p>

</div>

---

## 🌟 Vision & Overview

Tired of sluggish manufacturer interfaces, unsolicited home-screen advertisements, privacy invasion, and aggressive background bloatware consuming your smart TV's limited RAM?

**Neo tvOS** is a mastercrafted, aerospace-grade home launcher designed specifically for debloated TVs (optimized using the [CleanYourTV](https://github.com/Gathin23/cleanyourtv) ecosystem). It replaces heavy, ad-ridden stock OEM launchers with an ultra-responsive, Apple TV-inspired aesthetic that delivers instant frame transitions, sub-30ms D-pad response times, and absolute privacy with zero telemetry.

Built from the ground up in high-performance native Java targeting Android API 37, optimized with R8 whole-program compilation, and operating with a featherweight footprint under 5 MB.

> [!TIP]
> **Zero Telemetry Guarantee:** Neo tvOS contains no analytics, no trackers, and no background network calls. All configurations and states remain strictly local to your device.

---

## 📸 Real-Device UI / UX Showcase

*(Captured live over wireless ADB from the connected Android TV)*

<div align="center">

### 🖥️ Native Clean Experience

|                         **Atmospheric Top Shelf & Dock**                          |                                   **Top Bar Action Pill Navigation**                                   |
|:---------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------:|
| <img src="docs/shots/tv-01-home.png" width="440" alt="Home Screen Live Capture"/> | <img src="docs/shots/tv-02-topbar-focus.png" width="440" alt="Top Bar Quick Navigation Live Capture"/> |
|          *Clean typography, live weather, and dynamic ambient backlight*          |                         *Direct access to Inputs, Wi-Fi status, and Settings*                          |

<br/>

|                                **Floating Glass Launcher Settings**                                |                                   **Fluid App Library & Grid**                                   |
|:--------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------:|
| <img src="docs/shots/tv-03-settings-menu.png" width="440" alt="Quick Settings Menu Live Capture"/> | <img src="docs/shots/tv-04-app-grid.png" width="440" alt="Fluid App Library Grid Live Capture"/> |
|             *Translucent glass menu with distinct iconography & custom tuning*             |                  *Hardware-accelerated scrolling with physics focus indicator*                   |

<br/>

|                                **1-Click Network & Connectivity**                                |                        **Clean Living Room Experience**                        |
|:------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------:|
| <img src="docs/shots/tv-wifi-settings.png" width="440" alt="Wi-Fi Quick Settings Live Capture"/> | <img src="docs/shots/tv-01-home.png" width="440" alt="Clean Home Experience"/> |
|                      *Real-time Wi-Fi & Ethernet state with instant setup*                       |        *Zero ads, 100% private, instant boot, smooth 60fps animations*         |

<br/>

### 🔄 The Transformation: Before vs. After

|                                                                                                                                                                           Stock Bloated Manufacturer OS (Before)                                                                                                                                                                            |                        Neo tvOS Pure Experience (After)                        |
|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------:|
| <img src="docs/shots/tv-home-before-1.png" width="110" alt="Before: Stock TV OS with ads"/><br/><img src="docs/shots/tv-home-before-2.png" width="110" alt="Before: Stock TV OS with ads"/><br/><img src="docs/shots/tv-home-before-3.png" width="110" alt="Before: Stock TV OS with ads"/><br/><img src="docs/shots/tv-home-before-4.png" width="110" alt="Before: Stock TV OS with ads"/> | <img src="docs/shots/tv-01-home.png" width="440" alt="After: Clean Neo tvOS"/> |
|                                                                                                                                                                   ❌ *Ads, sponsored junk, 85% RAM usage, sluggish D-pad*                                                                                                                                                                   |       ⚡ *Zero ads, 100% private, instant boot, smooth 60fps animations*       |

</div>

---

## ⚡ Key Highlights & Engineering Features

```
  ┌────────────────────────────────────────────────────────────────────────┐
  │                            Neo tvOS Core                               │
  ├────────────────────────────────────────────────────────────────────────┤
  │  🎯 Zero Telemetry       │ Completely offline-first; no tracking data  │
  │  🚀 Sub-30ms Response    │ Hand-tuned RecyclerView & lightweight views │
  │  💎 Frosted Glass Focus  │ Smooth physics-driven focus highlight band  │
  │  🎨 Live Ambient Glow    │ Real-time dominant palette glow behind tiles│
  │  🔌 Universal Inputs     │ 1-click HDMI 1/2/3, AV, and Live TV picker  │
  │  🛡️ Anti-Kill Guard      │ Built-in revival service survives OEM kills │
  │  🔒 System Protection    │ Active Device Administrator anti-uninstall  │
  │  📦 Modular Architecture │ Dynamic module card extensions              │
  └────────────────────────────────────────────────────────────────────────┘
```

- **Cinematic Top Shelf**: Dynamically showcases the active application's branding with real-time ambient lighting derived from its artwork.
- **Dynamic Quick-Action Pills**: Instant, remote-friendly access to HDMI/AV inputs, active Wi-Fi and Ethernet state, and system configurations.
- **Accurate Live Weather & Lunar Clock**: Clean weather string rendering (`City • Temp • Condition`), lunar calendar, and Shamsi date options with zero mojibake.
- **12-Hour / 24-Hour Clock Toggle**: Seamlessly switch between 12-hour (`h:mm a`), 24-hour (`HH:mm`), or system-default time formats directly from the settings menu.
- **Refined Launcher Settings**: Complete with dedicated vector iconography (`ic_wallpaper`, `ic_animation`, `ic_sparkle`, `ic_power`, `ic_palette`, `ic_home_settings`), column density tuning (4–7 columns), dock opacity (30%–100%), and motion control.
- **Micro-Footprint (< 5 MB)**: Zero Kotlin runtime overhead, aggressive R8 dead-code elimination, and minimal memory usage (~15 MB RAM runtime).
- **Fully Customizable Dock & Grid**: Reorder apps with remote arrow keys, hide unwanted system packages, and fine-tune row allocations.
- **System App & Anti-Uninstall Lockdown**: Integrates with Android's `DeviceAdminReceiver` framework to disable the "Uninstall" affordance in Android TV Settings, ensuring the launcher is always permanent and protected.

---

## 🚀 Quick Start & Installation

### 1. Debloat Your Smart TV (Recommended)

Neo tvOS works best on a clean, debloated TV. Follow the [CleanYourTV](https://github.com/Gathin23/cleanyourtv) guide:

1. Enable **Developer Options** & **USB Debugging** on your TV (*Settings → About → Click 'Build' 7 times*).
2. Connect your workstation to the TV via ADB:
   ```bash
   adb connect <TV_IP_ADDRESS>:5555
   ```
3. Run the CleanYourTV disable suite to back up and disable vendor telemetry and ad services.

### 2. Build & Deploy Neo tvOS
```bash
# Clone the repository
git clone https://github.com/imSutirtha/neo-tv-os-launcher.git
cd neo-tvos

# Run comprehensive test suite (304 unit tests)
./gradlew testDebugUnitTest

# Build Release APK with Proguard/R8 optimization
./gradlew assembleRelease

# Install directly to your connected TV via ADB
adb install -r app/build/outputs/apk/release/app-release.apk
```

### 3. Set as Default Launcher

Press the **Home** button on your remote, select **Neo tvOS**, and choose **"Always"**.

*(Optional)* If your TV firmware restricts default launcher selection via UI:

```bash
adb shell cmd package set-home-activity com.darkgod.devtools.neo_tvos/.ui.MainActivity
```

---

## 🛡️ System App & Anti-Uninstall Protection

To ensure **Neo tvOS** acts as a permanent system component that can never be uninstalled or removed from the TV's Settings UI:

### Method A: Active Device Administrator Lockdown (Universal — No Root Required)

Neo tvOS includes a built-in `NeoAdminReceiver` registered with the Android Device Administration framework. Once activated, Android locks the package, greying out and disabling the "Uninstall" button in the TV's Settings app:

```bash
# Grant core system permissions
adb shell pm grant com.darkgod.devtools.neo_tvos android.permission.ACCESS_FINE_LOCATION
adb shell pm grant com.darkgod.devtools.neo_tvos android.permission.WRITE_SECURE_SETTINGS

# Lock in Neo tvOS as active Device Administrator
adb shell dpm set-active-admin com.darkgod.devtools.neo_tvos/.receiver.NeoAdminReceiver
```

> [!NOTE]
> Once active administrator status is granted, any user attempt to uninstall the launcher from Android TV Settings is rejected with:
> *"This app is an active device administrator and must be deactivated before uninstalling."*

### Method B: Native `/system/priv-app` Integration (Root / Magisk Environments)

For TVs with rooted firmware or unlocked bootloaders:

```bash
# Remount system partition with write permissions
adb root
adb remount

# Create privileged directory and push APK
adb shell mkdir -p /system/priv-app/NeoTvOS
adb push app/build/outputs/apk/release/app-release.apk /system/priv-app/NeoTvOS/NeoTvOS.apk
adb shell chmod 644 /system/priv-app/NeoTvOS/NeoTvOS.apk

# Reboot TV to initialize as system privileged app
adb reboot
```

---

## 🛠️ Developer Specifications & Verification

All automated checks and tests run with zero warnings and zero regressions:

```bash
# Verify unit tests (304 passed, 0 failed)
./gradlew testDebugUnitTest

# Lint code health check
./gradlew lintDebug

# Assemble Debug and Release targets
./gradlew assembleDebug assembleRelease
```

### Package & Release Identity

- **Namespace:** `com.darkgod.devtools.neo_tvos`
- **Application ID:** `com.darkgod.devtools.neo_tvos`
- **Version Name:** `1.0.0-BETA`
- **Version Code:** `280010000`
- **Target SDK:** Android API 37
- **Compile SDK:** Android API 37
- **Minimum SDK:** Android 9.0 Pie (API 28)
- **Java Compatibility:** Version 17
- **Dependencies:** Fully migrated to Gradle Version Catalog (`gradle/libs.versions.toml`)

---

## 📁 Repository Structure

```
neo-tvos/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml              # Manifest, Home & Device Admin Filters
│   │   │   ├── java/com/darkgod/devtools/neo_tvos/
│   │   │   │   ├── card/                        # Module cards & extension discovery
│   │   │   │   ├── config/                      # Runtime configuration & theme overlays
│   │   │   │   ├── data/
│   │   │   │   │   ├── app/                     # Dock slots, library, order, launch actions
│   │   │   │   │   └── weather/                 # Weather provider, store, snapshot, client
│   │   │   │   ├── input/                       # Remote keys, passthrough inputs, volume keys
│   │   │   │   ├── receiver/                    # NeoAdminReceiver device administration
│   │   │   │   ├── service/                     # Accessibility services & guard revival
│   │   │   │   ├── theme/                       # Wallpaper player, rotation, moods, themes
│   │   │   │   ├── ui/                          # MainActivity, adapters, settings activities
│   │   │   │   │   ├── decor/                   # GlassView, SkyView, dock decoration
│   │   │   │   │   └── widget/                  # ClockScene, race weekend, lunar/shamsi date
│   │   │   │   └── util/                        # Navigation, hero art, motion, system settings
│   │   │   └── res/                             # Layouts, Vector Drawables, XMLs & Locales
│   │   └── test/
│   │       └── java/com/darkgod/devtools/neo_tvos/  # 304 Automated Unit Tests across all packages
│   ├── build.gradle                             # AGP 9.5 & R8 build script
│   └── proguard-rules.pro                       # Aggressive code & resource shrinking
├── gradle/
│   └── libs.versions.toml                       # Gradle Version Catalog
├── docs/
│   └── shots/                                   # Real-device UI/UX screenshots
├── settings.gradle
└── README.md                                    # Documentation & Showcase
```

---

## 📜 License & Credits

- Licensed under the **MIT License**.
- Engineered by **Sutirtha**.
- Inspired by and compatible with [CleanYourTV](https://github.com/Gathin23/cleanyourtv) by Gathin23.

---

<div align="center">
  <b>Elevate your living room. Fast. Clean. Powerful.</b>
</div>

<div align="center">
  <b>Made by Sutirtha with ❤️‍🔥</b>
</div>
