package com.cybonixsolutions.cybonixsolutionstrainer__cystr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * VideoAdapter is a RecyclerView adapter to display a list of videos.
 */
class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    // List of videos to be displayed
    private val videoList = mutableListOf<Video>()

    // Interface for click listener
    interface OnVideoClickListener {
        fun onVideoClick(position: Int)
    }

    var onVideoClickListener: OnVideoClickListener? = null

    /**
     * VideoViewHolder represents a single item view in the RecyclerView.
     * It holds references to the views to be populated with video data.
     */
    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val thumbnailImageView: ImageView = itemView.findViewById(R.id.thumbnailImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onVideoClickListener?.onVideoClick(adapterPosition)
        }
    }

    /**
     * Creates the view holder for the RecyclerView items.
     * @param parent The parent view group.
     * @param viewType The view type (not used in this adapter).
     * @return The created VideoViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(itemView)
    }

    /**
     * Binds the video data to the view holder's views.
     * @param holder The VideoViewHolder to bind the data to.
     * @param position The position of the video item in the list.
     */
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val currentVideo = videoList[position]

        // Load video thumbnail using Glide
        Glide.with(holder.itemView.context)
            .load(currentVideo.thumbnailUrl)
            .into(holder.thumbnailImageView)

        holder.titleTextView.text = currentVideo.title
        holder.descriptionTextView.text = currentVideo.description
    }

    /**
     * Gets the number of videos in the list.
     * @return The number of videos.
     */
    override fun getItemCount(): Int = videoList.size

    /**
     * Updates the video list and refreshes the RecyclerView.
     * @param newList The new list of videos to be displayed.
     */
    fun updateList(newList: List<Video>) {
        videoList.clear()
        videoList.addAll(newList)
        notifyDataSetChanged()
    }

    // Optionally: Add other methods to manipulate the list, e.g., add, remove, clear, etc.
}
