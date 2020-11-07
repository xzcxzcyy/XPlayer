package cloud.banson.xplayer.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cloud.banson.xplayer.data.Video
import cloud.banson.xplayer.databinding.ExistListItemBinding
import cloud.banson.xplayer.util.VideoDiffCallback

class VideoAdapter(private val onClick: (Long) -> Unit) :
    ListAdapter<Video, VideoAdapter.ViewHolder>(VideoDiffCallback()) {
    class ViewHolder private constructor(
        private val binding: ExistListItemBinding,
        private val onClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup, onClick: (Long) -> Unit): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ExistListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, onClick)
            }
        }

        fun bind(item: Video) {
            binding.itemName.text = item.name
            binding.root.setOnClickListener {
                onClick(item.id)
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
