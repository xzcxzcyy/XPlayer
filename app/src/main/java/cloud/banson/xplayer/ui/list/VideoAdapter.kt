package cloud.banson.xplayer.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cloud.banson.xplayer.data.Video
import cloud.banson.xplayer.databinding.ExistListItemBinding

class VideoAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<Video, VideoAdapter.ViewHolder>(VideoDiffCallback()) {
    class ViewHolder private constructor(
        private val binding: ExistListItemBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup, onClick: (String) -> Unit): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ExistListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, onClick)
            }
        }

        fun bind(item: Video) {
            binding.itemName.text = item.name
            binding.root.setOnClickListener {
                onClick(item.uri)
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
    override fun areItemsTheSame(oldItem: Video, newItem: Video) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Video, newItem: Video) = oldItem == newItem
}
