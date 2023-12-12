# PeladApp ⚽

PeladApp is a soccer management application designed to facilitate organizing matches among friends. It provides intuitive features for creating matches, managing player participation, and keeping track of match details.

## Features 🚀

### 1. Match Preparation 🏟️
Easily set up a new match or join an existing one:
- **Create Match:** Initiate a new match by specifying the total number of players expected and the number of players per team.
- **Join Match:** Seamlessly join an existing match using a unique code provided by the organizer.

### 2. Match Room 📊
Effortlessly manage the ongoing match and participants:
- **Comprehensive Match Details:** Access detailed information about the match, including the unique code, total number of players, and players per team.
- **Player Management:** Confirm player participation, organize waiting lists, and handle players who are temporarily out.

### 3. In-Match ⚽
Enjoy a smooth and dynamic in-match experience:
- **Timer Control:** Start and stop the match timer with a single tap, ensuring accurate recording of match duration.
- **Team Assignments:** Assign players to specific teams, fostering a fair and competitive environment.
- **Goal Tracking:** Record goals in real-time, keeping the score updated throughout the match.

### 4. User Profile 🧑‍💻
Efficiently manage user details and account settings:
- **User Details:** Conveniently view the user's current details, including the display name.
- **Profile Editing:** Edit user information directly from the profile screen, ensuring up-to-date and accurate data.
- **Logout Functionality:** Log out securely from the application with a single tap, ensuring account privacy.

## Technologies and Libraries 🚀

### Technologies Used
- **Android:** The app is built using the Android platform, making use of its native features and capabilities.

### Architecture
- **MVVM (Model-View-ViewModel):** The app follows the MVVM architecture pattern to separate concerns, making it easier to maintain and test.

### Libraries Used
- **Firebase:** Utilized for authentication, real-time database, and cloud storage.
- **ViewModel:** Android Architecture Component for managing UI-related data in a lifecycle-conscious way.
- **LiveData:** Part of the Android Architecture Components, it's used to build data objects that notify views when the underlying database changes.
- **RecyclerView:** A flexible view for providing a limited window into a large data set of items.
- **AlertDialog:** Used for displaying important messages or actions to the user.
- **Chronometer:** Android widget for displaying a count-up/count-down timer.

### Dependency Injection
- **ViewModel and LiveData:** Used for managing UI-related data in a lifecycle-aware way, without any direct reference to the user interface.

### Gradle Plugins
- **ViewModel and LiveData:** Used for managing UI-related data in a lifecycle-aware way, without any direct reference to the user interface.


## Supported Languages 🌐
- **Portuguese (Default):** O aplicativo é inicialmente configurado para o idioma português.
- **English:** The app is also available in English.


## Getting Started 🚀

To get started with PeladApp, follow these steps:

### Prerequisites 🛠️

Before you begin, ensure you have the following installed:

- [Android Studio](https://developer.android.com/studio) - for Android app development.
- [Firebase Account](https://firebase.google.com/) - to use Firebase services for backend functionality.

### Installation 📲

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/peladapp.git
    ```

2. Open the project in Android Studio.

3. Connect the app to Firebase:

    - Create a new project on the [Firebase Console](https://console.firebase.google.com/).
    - Add an Android app to your Firebase project, following the setup instructions.
    - Download the `google-services.json` file and place it in the `app` directory of your Android Studio project.

4. Build and run the app on an Android emulator or physical device.

### Configuration ⚙️

Modify the Firebase configurations, API keys, or any other settings as needed for your environment.

### Usage 🎮

Explore the different features of PeladApp, create matches, join existing ones, and enjoy managing soccer matches with your friends!


