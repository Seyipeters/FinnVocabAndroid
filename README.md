# FinnVocab Android - Kotlin Version

This is a **starter Android app** converted from the Python/Flet FinnVocab project.

## ✅ What's Included

### Core Features Implemented:
- ✅ **Home Screen** with Word of the Day
- ✅ **Add Word** functionality
- ✅ **Room Database** (local SQLite storage)
- ✅ **Material Design UI**
- ✅ **Navigation between screens**

### Not Yet Implemented (You'll need to add):
- ⏳ Categories screen with word list
- ⏳ Quiz functionality
- ⏳ Flashcards screen
- ⏳ Grammar reference pages
- ⏳ Progress tracking
- ⏳ Dictionary integration

## 🚀 How to Open in Android Studio

### 1. Install Android Studio
Download from: https://developer.android.com/studio

### 2. Open the Project
1. Launch Android Studio
2. Click "Open" (or File → Open)
3. Navigate to: `C:\Users\Omole Peter\Desktop\webapp\FinnVocabAndroid`
4. Click "OK"

### 3. Wait for Gradle Sync
Android Studio will automatically download dependencies (takes 2-5 minutes first time)

### 4. Run the App
**Option A: Emulator**
- Tools → Device Manager → Create Virtual Device
- Select a phone (e.g., Pixel 6)
- Click ▶️ Run button

**Option B: Real Phone**
- Enable Developer Options on your Android phone
- Enable USB Debugging
- Connect phone via USB
- Click ▶️ Run and select your device

## 📁 Project Structure

```
FinnVocabAndroid/
├── app/
│   ├── build.gradle                # Dependencies
│   ├── src/main/
│   │   ├── AndroidManifest.xml     # App configuration
│   │   ├── java/com/finnvocab/app/
│   │   │   ├── MainActivity.kt     # Home screen
│   │   │   ├── AddWordActivity.kt  # Add word screen
│   │   │   ├── Word.kt             # Data model
│   │   │   ├── WordDao.kt          # Database operations
│   │   │   └── WordDatabase.kt     # Database setup
│   │   └── res/
│   │       ├── layout/             # UI XML files
│   │       │   ├── activity_main.xml
│   │       │   └── activity_add_word.xml
│   │       ├── values/
│   │       │   ├── colors.xml      # App colors
│   │       │   ├── strings.xml     # Text strings
│   │       │   └── themes.xml      # App theme
├── build.gradle                    # Project config
└── settings.gradle                 # Module settings
```

## 🔧 Key Technologies

- **Language**: Kotlin
- **UI**: Material Design Components
- **Database**: Room (SQLite wrapper)
- **Architecture**: MVVM pattern ready
- **Async**: Kotlin Coroutines
- **Min SDK**: Android 7.0 (API 24)
- **Target SDK**: Android 14 (API 34)

## 📝 Next Steps to Complete the App

### 1. Create CategoriesActivity
```kotlin
// Shows list of categories and words
class CategoriesActivity : AppCompatActivity() {
    // Use RecyclerView to display words grouped by category
}
```

### 2. Create QuizActivity
```kotlin
// Multiple choice quiz
class QuizActivity : AppCompatActivity() {
    // Show 4 options, check answer, track score
}
```

### 3. Create FlashcardsActivity
```kotlin
// Swipeable flashcards
class FlashcardsActivity : AppCompatActivity() {
    // Show Finnish, reveal English on tap
}
```

### 4. Create GrammarActivity
```kotlin
// Grammar reference with tabs
class GrammarActivity : AppCompatActivity() {
    // Verbs, Partitive, Location cases
}
```

## 🎨 Customization

### Change App Name
Edit `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">Your App Name</string>
```

### Change Colors
Edit `app/src/main/res/values/colors.xml`:
```xml
<color name="primary">#YOUR_COLOR</color>
```

### Add App Icon
1. Right-click `res` folder → New → Image Asset
2. Choose icon image
3. Generate all sizes automatically

## 🐛 Common Issues

### Gradle Sync Failed
**Solution**: File → Invalidate Caches → Restart

### SDK Not Found
**Solution**: Tools → SDK Manager → Install Android SDK 34

### Emulator Won't Start
**Solution**: Enable Virtualization in BIOS (Intel VT-x or AMD-V)

## 📚 Learning Resources

- **Kotlin**: https://kotlinlang.org/docs/getting-started.html
- **Android Dev**: https://developer.android.com/courses
- **Material Design**: https://material.io/develop/android
- **Room Database**: https://developer.android.com/training/data-storage/room

## 🤝 Need Help?

This is a **basic starter template**. To complete all features from the Python app, you'll need to:
1. Learn Kotlin basics (1-2 weeks)
2. Learn Android development (2-4 weeks)
3. Implement remaining screens (2-4 weeks)

**Estimate**: 1-2 months for full conversion

---

Good luck with your Android development journey! 🚀
