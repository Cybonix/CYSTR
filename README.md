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
    *   The project uses a hardcoded API key which has been provided for demonstration purposes. For production use, it is highly recommended to replace this with a secure method of storing your API key.
    *   The API key is located in `app/src/main/java/com/cybonixsolutions/cybonixsolutionstrainer__cystr/MainActivity.kt`.
4.  **Build and Run**:
    *   Build the project using the "Build" menu in Android Studio.
    *   Run the app on an Android emulator or a physical device.

## Security Note

The YouTube API key is hardcoded in `MainActivity.kt`. This is a security risk as it exposes the key in the client-side code. It is strongly recommended to store the API key securely, for example, by adding it to a `local.properties` file and accessing it through `BuildConfig`.

## Future Improvements

*   **Secure API Key Storage**: Implement a secure method for storing the YouTube API key, such as using `local.properties` and `BuildConfig`.
*   **Enhanced Video Player**: Replace the `VideoView` with a more robust video player library like [ExoPlayer](https://github.com/google/ExoPlayer) to support more video formats and provide a better user experience.
*   **Improved UI/UX**: Enhance the user interface with better layouts, animations, and a more modern design.
*   **Pagination**: Implement pagination for loading more videos as the user scrolls, instead of loading a fixed number of videos at once.
*   **Error Handling**: Improve error handling for network requests and video playback to provide more informative feedback to the user.
*   **User Accounts**: Add user accounts to allow users to save their favorite videos or create playlists.
