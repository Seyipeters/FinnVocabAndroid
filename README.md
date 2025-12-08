# FinnVocab

A simple and effective Android application for learning Finnish vocabulary through interactive flashcards, quizzes, and a detailed grammar guide.

## Features

- [x] **Interactive Flashcards**: Study vocabulary with a flippable card interface directly on the home screen.
- [x] **Dynamic Quizzes**: Test your knowledge with multiple-choice questions generated from the database.
- [x] **Instant Feedback**: Get animated "Correct" or "Wrong" feedback during quizzes.
- [x] **Comprehensive Grammar Guide**: Access detailed explanations for Finnish grammar topics via a navigation drawer.
- [x] **Local Database**: All words and grammar are stored on-device using Room, making the app fully offline.
- [x] **Custom Word Entry**: Add your own words and sentences to the vocabulary list.
- [x] **Animated Splash Screen**: A clean, animated startup screen.

## Tech Stack

- **Language**: Kotlin
- **UI**: Android Views (XML Layouts) & Material Design Components
- **Architecture**: Activity-based with `lifecycleScope` for Coroutines
- **Storage**: Room Database
- **Navigation**: Android `Intent`s and `NavigationView`
- **Testing**: JUnit & Espresso

## Project Structure

```
FinnVocabAndroid/
└── app/
    ├── src/main/
    │   ├── java/com/finnvocab/app/  # Main source code
    │   │   ├── MainActivity.kt        # Home screen (Flashcards & Menu)
    │   │   ├── QuizActivity.kt        # Quiz screen
    │   │   ├── GrammarActivity.kt     # Grammar content viewer
    │   │   ├── SplashActivity.kt      # Startup splash screen
    │   │   ├── WordDatabase.kt        # Room DB setup & pre-population
    │   │   └── ...
    │   ├── res/                         # Resources
    │   │   ├── layout/                  # UI layouts (XML)
    │   │   ├── drawable/                # Icons and vector graphics
    │   │   ├── menu/                    # Navigation drawer menu
    │   │   └── values/                  # Strings, colors, styles
    └── build.gradle                 # App-level dependencies
```

## Screenshots

![App Screenshot 1](https://i.imgur.com/iYyW6Ua.png)
![App Screenshot 2](https://i.imgur.com/g05vY7c.png)
![App Screenshot 3](https://i.imgur.com/o1bZ2jY.png)

## How to Run

1. Clone the repository: `git clone https://github.com/Seyipeters/FinnVocabAndroid.git`
2. Open the project in Android Studio (Hedgehog 2023.1.1 or newer).
3. Let Gradle sync the dependencies.
4. Run on an Android emulator or a physical device (API 24 / Android 7.0+).

## License

This project is licensed under the MIT License.
