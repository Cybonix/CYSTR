package com.cybonixsolutions.cybonixsolutionstrainer__cystr

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val videoList = mutableListOf<Video>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var videoAdapter: VideoAdapter

    // Constants
    companion object {
        const val YOUTUBE_API_KEY = "YOUR_YOUTUBE_API_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeUI()
        fetchVideosFromYouTubeAPI()
    }

    /**
     * Initializes the UI components and sets the necessary listeners.
     */
    private fun initializeUI() {
        recyclerView = findViewById(R.id.recyclerView)
        val searchBar: SearchView = findViewById(R.id.searchBar)

        videoAdapter = VideoAdapter(videoList)
        recyclerView.adapter = videoAdapter

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchVideos(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optional: You can implement real-time search here if you wish
                return true
            }
        })
    }

    /**
     * Fetches video data from the YouTube API using the specified API key.
     * Handles rate limit and network errors, updates the videoList with fetched videos,
     * and updates the videoAdapter with the updated videoList.
     */
    private fun fetchVideosFromYouTubeAPI() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // TODO: Implement the logic to fetch video data from YouTube API using YOUTUBE_API_KEY.
                val fetchedVideos = mutableListOf<Video>()  // Example: fetched from API.

                withContext(Dispatchers.Main) {
                    updateVideos(fetchedVideos)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error fetching videos: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Updates the current video list and notifies the adapter.
     * @param newVideos The list of new videos to be added.
     */
    private fun updateVideos(newVideos: List<Video>) {
        videoList.clear()
        videoList.addAll(newVideos)
        videoAdapter.notifyDataSetChanged()
    }

    /**
     * Filters the videoList based on the provided search query and updates the videoAdapter.
     * @param query The search query to filter the videoList.
     */
    private fun searchVideos(query: String) {
        val filteredList = videoList.filter { it.title.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true) }
        videoAdapter.updateList(filteredList) // Ensure that VideoAdapter has an updateList method
    }
}
