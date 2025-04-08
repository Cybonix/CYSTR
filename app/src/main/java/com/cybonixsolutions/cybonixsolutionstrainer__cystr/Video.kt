package com.cybonixsolutions.cybonixsolutionstrainer__cystr

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a single video entity with properties.
 * Can store video data fetched from YouTube or locally.
 */
@Parcelize
data class Video(
    /** Unique identifier of the video. */
    val id: String,

    /** Title of the video. */
    val title: String,

    /** Description or summary of the video. */
    val description: String,

    /** URL of the video's thumbnail image. */
    val thumbnailUrl: String,

    /** Direct URL to stream the video. */
    val videoUrl: String,

    /** Local path for offline playback. Null if not downloaded. */
    var localVideoPath: String? = null
) : Parcelable
