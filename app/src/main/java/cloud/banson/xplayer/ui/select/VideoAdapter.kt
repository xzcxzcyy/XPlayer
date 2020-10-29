package cloud.banson.xplayer.ui.select

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cloud.banson.xplayer.data.Video
import cloud.banson.xplayer.databinding.SelectVideoItemBinding

class VideoAdapter(private val onClick: (Uri) -> Unit) :
    ListAdapter<Video, VideoAdapter.ViewHolder>(VideoDiffCallback()) {
    class ViewHolder private constructor(
        private val binding: SelectVideoItemBinding,
        private val onClick: (Uri) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Video) {
            binding.apply {
                videoNameTextView.text = item.name
                thumbNail.setImageBitmap(item.thumbNail)
                thumbNail.setOnClickListener {
                    onClick(item.uri)
                }
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup, onClick: (Uri) -> Unit): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SelectVideoItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, onClick)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)
        }
    }
}

class VideoDiffCallback : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }
}
