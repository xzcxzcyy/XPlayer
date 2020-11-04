package cloud.banson.xplayer.util

import androidx.recyclerview.widget.DiffUtil
import cloud.banson.xplayer.data.Video

class VideoDiffCallback : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Video, newItem: Video) = oldItem == newItem
}