package com.cybonixsolutions.cybonixsolutionstrainer__cystr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchResult
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
        const val YOUTUBE_API_KEY = BuildConfig.YOUTUBE_API_KEY
        private const val MAX_RESULTS = 25
        private const val TAG = "MainActivity"
        
        // Query to fetch computer troubleshooting videos
        private const val DEFAULT_QUERY = "computer troubleshooting tutorial"
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

        // Set up RecyclerView with layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        // Initialize adapter with click listener
        videoAdapter = VideoAdapter(videoList)
        videoAdapter.onVideoClickListener = object : VideoAdapter.OnVideoClickListener {
            override fun onVideoClick(position: Int) {
                val video = videoList[position]
                navigateToVideoPlayer(video)
            }
        }
        
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
     * Navigate to the VideoPlayerActivity with the selected video.
     * @param video The video to be played.
     */
    private fun navigateToVideoPlayer(video: Video) {
        val intent = Intent(this, VideoPlayerActivity::class.java)
        intent.putExtra("selected_video", video)
        startActivity(intent)
    }

    /**
     * Fetches video data from the YouTube API using the specified API key.
     * Handles rate limit and network errors, updates the videoList with fetched videos,
     * and updates the videoAdapter with the updated videoList.
     */
    private fun fetchVideosFromYouTubeAPI() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Initialize the YouTube object
                val youtube = YouTube.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    null
                ).setApplicationName("CYSTR").build()
                
                // Build the search request
                val searchRequest = youtube.search().list(listOf("snippet"))
                searchRequest.key = YOUTUBE_API_KEY
                searchRequest.q = DEFAULT_QUERY
                searchRequest.type = listOf("video")
                searchRequest.maxResults = MAX_RESULTS.toLong()
                searchRequest.fields = "items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/high/url)"
                
                // Execute the search request
                val searchResponse = searchRequest.execute()
                val searchResults = searchResponse.items
                
                // Convert search results to Video objects
                val fetchedVideos = if (searchResults.isNullOrEmpty()) {
                    // Fallback to sample videos if no results or API key issues
                    createSampleVideos()
                } else {
                    convertToVideoList(searchResults)
                }
                
                withContext(Dispatchers.Main) {
                    updateVideos(fetchedVideos)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching videos: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Loading sample videos due to API error: ${e.message}", Toast.LENGTH_SHORT).show()
                    // Load sample videos as fallback
                    updateVideos(createSampleVideos())
                }
            }
        }
    }
    
    /**
     * Converts a list of YouTube search results to a list of Video objects.
     * @param searchResults The search results from YouTube API.
     * @return A list of Video objects.
     */
    private fun convertToVideoList(searchResults: List<SearchResult>): List<Video> {
        return searchResults.map { searchResult ->
            val videoId = searchResult.id.videoId
            val title = searchResult.snippet.title
            val description = searchResult.snippet.description
            val thumbnailUrl = searchResult.snippet.thumbnails.high.url
            val videoUrl = "https://www.youtube.com/watch?v=$videoId"
            
            Video(
                id = videoId,
                title = title,
                description = description,
                thumbnailUrl = thumbnailUrl,
                videoUrl = videoUrl
            )
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
     * Creates sample videos for testing when YouTube API is not available.
     * @return A list of sample Video objects.
     */
    private fun createSampleVideos(): List<Video> {
        return listOf(
            Video(
                id = "sample1",
                title = "How to Fix a Slow Computer",
                description = "Learn how to speed up your computer with these simple tips and tricks.",
                thumbnailUrl = "https://i.ytimg.com/vi/dummyvideo1/maxresdefault.jpg",
                videoUrl = "https://www.youtube.com/watch?v=dummyvideo1"
            ),
            Video(
                id = "sample2",
                title = "Computer Won't Boot? Here's How to Fix It",
                description = "Troubleshooting guide for when your computer won't start up properly.",
                thumbnailUrl = "https://i.ytimg.com/vi/dummyvideo2/maxresdefault.jpg",
                videoUrl = "https://www.youtube.com/watch?v=dummyvideo2"
            ),
            Video(
                id = "sample3",
                title = "Fixing Blue Screen of Death Errors",
                description = "Step-by-step guide to diagnose and fix the dreaded Blue Screen of Death.",
                thumbnailUrl = "https://i.ytimg.com/vi/dummyvideo3/maxresdefault.jpg",
                videoUrl = "https://www.youtube.com/watch?v=dummyvideo3"
            ),
            Video(
                id = "sample4",
                title = "How to Remove Malware from Your PC",
                description = "Complete guide to identifying and removing malicious software from your computer.",
                thumbnailUrl = "https://i.ytimg.com/vi/dummyvideo4/maxresdefault.jpg",
                videoUrl = "https://www.youtube.com/watch?v=dummyvideo4"
            ),
            Video(
                id = "sample5",
                title = "Upgrading Your Computer's RAM",
                description = "Learn how to upgrade your computer's memory for better performance.",
                thumbnailUrl = "https://i.ytimg.com/vi/dummyvideo5/maxresdefault.jpg",
                videoUrl = "https://www.youtube.com/watch?v=dummyvideo5"
            )
        )
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
