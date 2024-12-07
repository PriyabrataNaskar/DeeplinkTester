# Deeplink Tester Pro

Lightweight Ads free Deep Link Tester - Android app designed to simplify deeplink testing during development. It helps developers save, manage, and test deep links efficiently, making debugging faster and more organized.

# Key Features
- Store unique deep links for reuse.
- Create, delete, and manage deep links.
- Test deep links with ease, validating their functionality.
- Single-screen app with a clean and intuitive UI built with Jetpack Compose, Clean Arch.

# Interesting Techniques
- Jetpack Compose UI: Modern way for building UI. [Learn more about Compose here.](https://developer.android.com/jetpack)
- Dependency Injection with Hilt: Simplifies dependency management. [Documentation here.](https://developer.android.com/training/dependency-injection/hilt-android)
- Room Database: Used for local data persistence to store and manage deep links. [See details here.](https://developer.android.com/training/data-storage/room)
- Orbit MVI: Implements reactive state management for Compose screens. [Learn more about Orbit MVI here.](https://orbit-mvi.org/)
- Clean Architecture: Separation of concerns for better code organization and maintainability. [Learn more about Clean Arch here.](https://developer.android.com/topic/architecture)

# Libraries and Technologies
- Jetpack Compose: Modern way of building UI.[Documentation.](https://developer.android.com/jetpack)
- Hilt: For dependency injection. [Documentation.](https://developer.android.com/training/dependency-injection/hilt-android)
- Room: For database management. [Documentation.](https://developer.android.com/jetpack/androidx/releases/room)
- Orbit MVI: For state management. [Documentation.](https://github.com/orbit-mvi/orbit-mvi)

# Project Structure
`
├── app/                     # Main app module
│   ├── src/
│       ├── main/
│           ├── java/        # Application logic
│               ├── home/data  # Data Layer (Room, DI, Models, Repositories)
|               ├── home/ui/view    # UI Layer (Compose components, ViewModel)
|               ├── ui.theme    # Compose Theme configuration
│           ├── res/         # UI resources (strings, drawables, etc.)
├── build.gradle             # Project-level Gradle configuration
├── settings.gradle          # Settings for project modules
├── proguard-rules.pro       # ProGuard configuration for release builds
`

# Screenshots

# Download APK

[APK Link]()