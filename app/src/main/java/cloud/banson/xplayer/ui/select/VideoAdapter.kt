package cloud.banson.xplayer.ui.select

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cloud.banson.xplayer.data.Video
import cloud.banson.xplayer.databinding.SelectVideoItemBinding

class VideoAdapter: ListAdapter<Video,VideoAdapter.ViewHolder>(VideoDiffCallback()) {
    class ViewHolder(binding: SelectVideoItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

class VideoDiffCallback(): DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        TODO("Not yet implemented")
    }
}
