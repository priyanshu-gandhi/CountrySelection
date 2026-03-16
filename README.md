# Country Selection App

A modern Android application designed to provide a seamless experience for browsing and selecting countries. This project demonstrates a high-quality UI/UX with a hybrid approach using both XML-based layouts and Jetpack Compose.

## 🚀 Features

- **Country Listing**: Fetch and display a comprehensive list of countries with their names and international dialing codes.
- **Search Functionality**: Quickly find countries using a responsive search bar.
- **Profile Screen**: A rich profile view featuring a Collapsing Toolbar, profile statistics, and country selection history.
- **Edge-to-Edge Experience**: Fully immersive UI with proper handling of system bar insets.
- **Modern Architecture**: Built using Clean Architecture principles and MVVM.

## 🛠 Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Frameworks**: 
    - [Jetpack Compose](https://developer.android.com/jetpack/compose) for modern declarative UI components.
    - **XML Layouts** with **ViewBinding** for robust screen structures.
- **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Networking**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/)
- **Image Loading**: [Glide](https://github.com/bumptech/glide)
- **Local Database**: [Room](https://developer.android.com/training/data-storage/room)
- **Navigation**: [Jetpack Navigation Component](https://developer.android.com/guide/navigation)
- **Design**: Material Design 3 (M3)

## 📁 Project Structure

```
com.example.countryselection/
├── data/           # Repositories and Data Sources (Retrofit API, Room DAO)
├── domain/         # Domain Models and Use Cases
├── ui/             # UI Components (Activities, Fragments, ViewModels, Compose screens)
│   └── theme/      # Compose Theme and Design System
└── util/           # Helper classes and extensions
```

## 🛠 Getting Started

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/CountrySelection.git
    ```
2.  **Open in Android Studio**:
    - Open Android Studio (Ladybug or newer recommended).
    - Select **File > Open** and navigate to the project folder.
3.  **Sync Project**:
    - Let Gradle sync and download dependencies.
4.  **Run**:
    - Select an emulator or physical device and click the **Run** button.

## 🎨 Resources & Styling

- **Typography**: Uses the **Montserrat** font family for a modern look.
- **Theming**: Implements `Theme.Material3.DayNight` for light and dark mode support.
- **Responsive Layouts**: All dimensions, strings, and colors are centralized in `res/values` for easy maintenance.
