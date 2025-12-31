🚀 Setup Instructions

Follow these steps to run the project locally:

Prerequisites

Android Studio (Giraffe / Hedgehog / newer)

Android SDK 26+

Internet connection (for music search & streaming)

Steps to Run

Clone the repository

git clone https://github.com/<your-username>/Lokal-Music-Player.git


Open in Android Studio

Open Android Studio

Click Open → select the project folder

Sync Gradle

Android Studio will auto-sync

Wait until build completes successfully

Run the app

Connect a real Android device or start an emulator

Click Run (▶) or Run without Debugger

Build APK (Optional)

To generate a shareable APK:

./gradlew assembleDebug


APK location:

app/build/outputs/apk/debug/app-debug.apk

🏗 Architecture Overview

The app follows the MVVM (Model–View–ViewModel) architecture to ensure scalability and clean separation of concerns.

Layers
UI Layer (Jetpack Compose)

HomeScreen.kt – Song search & listing

MiniPlayer.kt – Persistent playback controls

QueueScreen.kt – Full-screen queue management

The UI is state-driven, automatically updating based on ViewModel state.

ViewModel Layer

HomeViewModel – Handles song search and API interaction

PlayerViewModel – Manages playback state, queue logic, and navigation

ViewModels expose observable state using Compose State, ensuring reactive UI updates.

Playback Layer

MusicPlayerManager – Wrapper around ExoPlayer for audio playback

MusicPlaybackService – Foreground service enabling background playback

This layer ensures uninterrupted music playback across:

App minimization

Screen lock

Navigation between screens

Data Flow (Simplified)
UI → ViewModel → PlayerManager → ExoPlayer
↑                                    ↓
UI ←──────────── State Updates ───────

⚖️ Assumptions & Trade-offs
Assumptions

User has a stable internet connection

Songs are streamed online (no offline support yet)

API provides valid audio URLs

Trade-offs
Decision	Trade-off
Foreground Service for playback	Slight battery usage increase
Streaming instead of offline	No offline playback support
Simple queue reorder (↑ ↓)	No drag & drop gestures
Debug APK for sharing	Play Protect warnings may appear
Design Choices

Jetpack Compose chosen for modern UI & declarative state handling

MVVM chosen for maintainability and testability

ExoPlayer (Media3) used for robust media playback

Foreground Service used to comply with Android background execution limits

🔮 Future Improvements

Media notification playback controls

Full-screen player with seek bar

Queue persistence across app restarts

Offline caching support

Drag & drop queue reordering

👨‍💻 Author

Shashank Verma
