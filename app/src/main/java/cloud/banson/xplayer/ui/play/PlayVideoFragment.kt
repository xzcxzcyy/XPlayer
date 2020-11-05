package cloud.banson.xplayer.ui.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cloud.banson.xplayer.R
import cloud.banson.xplayer.databinding.PlayVideoFragmentBinding

class PlayVideoFragment : Fragment() {

    companion object {
        private const val TAG = "PLAY_VIDEO_FRAGMENT"
    }

    private lateinit var viewModel: PlayVideoViewModel
    private lateinit var binding: PlayVideoFragmentBinding
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initView(inflater, container)
        setUpData()
        return binding.root
    }

    private fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        val viewModelFactory = PlayVideoViewModelFactory(context!!.applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayVideoViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.play_video_fragment, container, false)
    }

    private fun setUpData() {
        videoAdapter = VideoAdapter()
        binding.viewPagerVideos.apply {
            adapter = videoAdapter
        }
        viewModel.playList.observe(viewLifecycleOwner, {
            videoAdapter.submitList(it)
        })
    }
}