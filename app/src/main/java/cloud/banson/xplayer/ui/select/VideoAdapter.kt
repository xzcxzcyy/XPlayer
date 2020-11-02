package cloud.banson.xplayer.ui.select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cloud.banson.xplayer.data.VideoInfo
import cloud.banson.xplayer.databinding.SelectVideoItemBinding

class VideoAdapter(private val onClick: (VideoInfo) -> Unit) :
    ListAdapter<VideoInfo, VideoAdapter.ViewHolder>(VideoInfoDiffCallback()) {
    class ViewHolder private constructor(
        private val binding: SelectVideoItemBinding,
        private val onClick: (VideoInfo) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoInfo) {
            binding.apply {
                videoNameTextView.text = item.name
                thumbNail.setImageBitmap(item.thumbNail)
                thumbNail.setOnClickListener {
                    onClick(item)
                }
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup, onClick: (VideoInfo) -> Unit): ViewHolder {
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

class VideoInfoDiffCallback : DiffUtil.ItemCallback<VideoInfo>() {
    override fun areItemsTheSame(oldItem: VideoInfo, newItem: VideoInfo): Boolean {
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: VideoInfo, newItem: VideoInfo): Boolean {
        return oldItem == newItem
    }
}
