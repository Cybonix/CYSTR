# Cybonix Solutions Trainer (CYSTR)

The Cybonix Solutions Trainer (CYSTR) is an Android application designed to provide users with easy access to do-it-yourself (DIY) computer troubleshooting videos. The app fetches video content from the YouTube API and presents it in a simple, user-friendly interface.

## Features

*   **Video Playback**: Watch computer troubleshooting tutorials directly within the app.
*   **Search Functionality**: Quickly find specific videos by searching for keywords.
*   **Dynamic Content**: Video content is fetched from the YouTube API, ensuring an up-to-date library of tutorials.
*   **Sample Videos**: Includes a set of sample videos for offline use or when the YouTube API is unavailable.
*   **Playback Controls**: Standard video controls, including play, pause, rewind, and fast-forward.

## Technology Stack

*   **Language**: [Kotlin](https://kotlinlang.org/)
*   **Platform**: [Android](https://www.android.com/)
*   **API**: [YouTube Data API v3](https://developers.google.com/youtube/v3)
*   **Libraries**:
    *   [Google API Client Library for Java](https://github.com/googleapis/google-api-java-client)
    *   [AndroidX AppCompat](https://developer.android.com/jetpack/androidx/releases/appcompat)
    *   [AndroidX RecyclerView](https://developer.android.com/jetpack/androidx/releases/recyclerview)
    *   [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) for asynchronous programming.
    *   [Jackson](https://github.com/FasterXML/jackson) for JSON parsing.

## How to Build and Run

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/Cybonix-Solutions-Trainer---CYSTR.git
    ```
2.  **Open in Android Studio**:
    *   Open Android Studio and select "Open an existing Android Studio project".
    *   Navigate to the cloned repository and select the `build.gradle` file.
3.  **Set up YouTube API Key**:
    *   Create a file named `local.properties` in the root directory of the project.
    *   Add your YouTube API key to this file:
        ```
        YOUTUBE_API_KEY="YOUR_API_KEY"
        ```
    *   Replace `YOUR_API_KEY` with your actual YouTube Data API key.
4.  **Build and Run**:
    *   Build the project using the "Build" menu in Android Studio.
    *   Run the app on an Android emulator or a physical device.

## Security Note

The YouTube API key is loaded from a `local.properties` file, which is included in the `.gitignore` file and should not be checked into version control. This prevents the API key from being exposed in the client-side code.

## Future Improvements

*   **Secure API Key Storage**: The API key is now stored in `local.properties`, which is a good practice for development. For production apps, consider using more advanced techniques like storing the key in a secure backend or using obfuscation with ProGuard/R8.
*   **Enhanced Video Player**: Replace the `VideoView` with a more robust video player library. Previously, [ExoPlayer](https.github.com/google/ExoPlayer) was a recommended option, but it is now deprecated and has been merged into [AndroidX Media3](https://github.com/androidx/media). All new development should use AndroidX Media3 for better support and more features.
*   **Improved UI/UX**: Enhance the user interface with better layouts, animations, and a more modern design.
*   **Pagination**: Implement pagination for loading more videos as the user scrolls, instead of loading a fixed number of videos at once.
*   **Error Handling**: Improve error handling for network requests and video playback to provide more informative feedback to the user.
*   **User Accounts**: Add user accounts to allow users to save their favorite videos or create playlists.
