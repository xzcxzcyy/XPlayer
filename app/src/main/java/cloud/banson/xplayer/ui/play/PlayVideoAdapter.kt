package cloud.banson.xplayer.ui.play

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.*
import android.widget.SeekBar
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cloud.banson.xplayer.data.Video
import cloud.banson.xplayer.databinding.PlayVideoItemBinding
import cloud.banson.xplayer.util.MyApplication
import cloud.banson.xplayer.util.VideoDiffCallback
import io.flutter.embedding.android.FlutterActivity


class PlayVideoAdapter(
    private val onCompleteListener: MediaPlayer.OnCompletionListener
) :
    ListAdapter<Video, PlayVideoAdapter.ViewHolder>(VideoDiffCallback()) {

    companion object {
        private const val TAG = "PLAY_VIDEO_ADAPTER"
    }

    class ViewHolder private constructor(
        private val binding: PlayVideoItemBinding,
        private val onCompleteListener: MediaPlayer.OnCompletionListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(
                parent: ViewGroup,
                onCompleteListener: MediaPlayer.OnCompletionListener,
            ): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PlayVideoItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, onCompleteListener)
            }
        }

        var mediaPlayer: MediaPlayer? = null

        @SuppressLint("ClickableViewAccessibility")
        fun bind(video: Video) {
            val refreshSeekBar: Runnable
            binding.apply {
                refreshSeekBar = object : Runnable {
                    override fun run() {
                        seekBar.progress =
                            100 * videoView.currentPosition / videoView.duration
                        if (videoView.isPlaying) {
                            seekBar.postDelayed(this, 100)
                        }
                    }
                }
            }
            val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
                override fun onDown(e: MotionEvent?): Boolean {
                    return true
                }

                override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                    mediaPlayer?.let {
                        if (it.isPlaying) {
                            it.pause()
                        } else {
                            it.start()
                            binding.seekBar.postDelayed(refreshSeekBar, 100)
                        }
                    }
                    return true
                }

                override fun onSingleTapUp(e: MotionEvent?): Boolean {
                    return true
                }

                override fun onDoubleTap(e: MotionEvent?): Boolean {
                    binding.videoView.context.startActivity(
                        FlutterActivity
                            .withCachedEngine(MyApplication.flutterEngineId)
                            .build(binding.videoView.context)
                    )
                    return true
                }
            }
            val gestureDetector =
                GestureDetector(MyApplication.context, gestureListener)
            val touchListener: View.OnTouchListener = View.OnTouchListener { v, event ->
                gestureDetector.onTouchEvent(event)
            }
            binding.textTitle.text = video.name
            binding.videoView.apply {
                setVideoURI(Uri.parse(video.uriString))
                setOnPreparedListener {
                    mediaPlayer = it
                    it.start()
                    binding.controllerLinear.bringToFront()
                    binding.seekBar.postDelayed(refreshSeekBar, 100)
                }
                setOnCompletionListener(onCompleteListener)
                setOnTouchListener(touchListener)
            }
            binding.seekBar.apply {
                max = 100
                progress = 0
                setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        seekBar?.let {
                            val nowProgress = binding.videoView.duration * it.progress / 100
                            binding.videoView.seekTo(nowProgress)
                        }
                    }

                })
            }
        }

        fun stopOnLeaving() {
            binding.videoView.pause()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, onCompleteListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)
        }
        Log.d(TAG, "onBindViewHolder: Auto Display...")
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.stopOnLeaving()
    }
}
