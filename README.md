# DarkGoD's TVOS

![Banner](https://raw.githubusercontent.com/Gathin23/cleanyourtv/main/docs/banner.png)

**DarkGoD's TVOS** – a cutting‑edge, elegant home launcher for Android TV & Google TV that re‑imagines the living‑room experience. Built on the robust **CleanYourTV** foundation, it delivers a minimalist, ad‑free UI with smooth motion, adaptive layouts, and premium visual polish.

---

## ✨ Highlighted Features

| Feature | Description |
|---|---|
| **Ultra‑Modern UI** | Glass‑like focus rings, kinetic animations, and a sleek dark theme that feels native to premium streaming platforms. |
| **Adaptive & Responsive** | Works flawlessly on 4K UHD, 1080p, and 720p screens, handling portrait, landscape, and foldable modes via Jetpack Compose’s MediaQuery API. |
| **Theme Engine** | Swap colors, wallpapers, and motion presets instantly with JSON‑driven themes; no rebuild required. |
| **Privacy‑First** | No telemetry, no ads, no background data collection – the launcher is completely self‑contained. |
| **Modular Extensibility** | Module cards let third‑party apps expose custom tiles; developers can integrate via `AppFunction` APIs. |
| **Performance Optimized** | Built with AGP 9.5, Java 17, and R8 shrinker – < 5 MB APK, instant start, sub‑30 ms frame latency. |
| **Seamless Integration** | Auto‑detects and disables stock TV bloatware; works with any Android TV that supports ADB debugging. |

---

## 📸 UI Preview

<div align="center">
  <img src="https://raw.githubusercontent.com/Gathin23/cleanyourtv/main/docs/banner.png" alt="Launcher banner" width="800"/>
  <p><em>Home screen with glass focus, custom dock, and ambient shelf.</em></p>
</div>

---

## 🚀 Quick‑Start Guide

### Prerequisites
1. **Java 17+** and **Android SDK 34** installed.
2. A TV on the same Wi‑Fi network with **USB debugging** enabled (Settings → Device Preferences → About → tap Build 7× to unlock Developer options).
3. **Git** and **Gradle** on your workstation.

### Step‑by‑Step
```bash
# 1️⃣ Clone the debloat tools (optional but recommended)
git clone https://github.com/Gathin23/cleanyourtv.git
cd cleanyourtv
./tools/disable.sh   # strips OEM bloatware, backs up state

# 2️⃣ Clone the launcher
cd ..
git clone <YOUR_FORK_OR_REPO_URL> tcl-custom-launcher
cd tcl-custom-launcher/app

# 3️⃣ Build & install
./gradlew assembleDebug               # creates app-debug.apk
adb install -r app/build/outputs/apk/debug/app-debug.apk

# 4️⃣ Set as default Home
#   Settings → Apps → Default apps → Home app → DarkGoD's TVOS
```

### Development Workflow
- **Run on device:** `./gradlew installDebug`
- **Execute unit tests:** `./gradlew testDebugUnitTest`
- **Static analysis:** `./gradlew lintDebug` (returns 0 errors)
- **Create a theme:** place `config.json` and optional `wallpaper.jpg` under `app/src/main/assets/themes/<name>/` and select it from Settings → Theme.

---

## 📂 Project Layout

```
├─ app/                         # Android module
│  ├─ src/main/                # Sources & resources
│  │  ├─ java/com/gathin/tvhome/   # Kotlin/Java codebase
│  │  └─ res/                 # XML layouts, drawables, strings, etc.
│  └─ build.gradle             # Module Gradle configuration
├─ gradle/                      # Gradle wrapper files
├─ gradlew / gradlew.bat        # Gradle wrapper scripts
└─ README.md                    # This documentation
```

---

## 📄 License

The launcher is released under the **MIT License** (see `LICENSE`). It inherits the permissive licensing from the original CleanYourTV project.

---

## 🙏 Acknowledgements

- **Gathin23** – creator of CleanYourTV, the foundation for this launcher.
- **Android Open Source Project** – for the platform, tooling, and Jetpack libraries.
- All contributors who helped refine the UI, improve performance, and add features.

---

*Elevate your TV experience with **DarkGoD's TVOS** – the ultimate blend of style, speed, and privacy.*