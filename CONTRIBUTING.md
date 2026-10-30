# Contributing to Neo tvOS Launcher

First off, thank you for considering contributing to **Neo tvOS Launcher**! It is people like you who make Neo tvOS a polished, high-performance, and telemetry-free experience for Android TV users worldwide.

---

## 📌 Code of Conduct

This project and everyone participating in it is governed by the [Neo tvOS Code of Conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code.

---

## 🚀 How Can I Contribute?

### Reporting Bugs
- Ensure the bug was not already reported by searching on GitHub under [Issues](https://github.com/imSutirtha/neo-tv-os-launcher/issues).
- If you're unable to find an open issue addressing the problem, [open a new one](https://github.com/imSutirtha/neo-tv-os-launcher/issues/new).
- Include device details (e.g. Sony Bravia, Mi TV, Fire TV stick), Android version, steps to reproduce, and logs if applicable (`adb logcat`).

### Suggesting Enhancements
- Open a feature request under [Issues](https://github.com/imSutirtha/neo-tv-os-launcher/issues).
- Describe the suggestion, the problem it solves, and why it benefits the TV experience.

### Pull Requests
1. Fork the repo and create your branch from `master`:
   ```bash
   git checkout -b feature/my-new-feature
   ```
2. Make your changes adhering to existing design principles:
   - **Zero telemetry**: Never introduce analytics or phone-home requests.
   - **Performance**: Maintain 60fps DPAD navigation without UI thread stutters.
   - **10-foot UI**: Keep TV ergonomics first.
3. Test thoroughly on an emulator or real Android TV hardware:
   ```bash
   ./gradlew testDebugUnitTest
   ./gradlew assembleRelease
   ```
4. Push your branch and submit a Pull Request with a clear description and screenshots/GIFs if modifying UI.

---

## 🛠️ Development Setup

- **IDE**: Android Studio Ladybug or newer
- **JDK**: OpenJDK 17 or higher
- **Android SDK**: API 28 (Min) to API 37 (Target / Compile)

Happy hacking! 🎉
