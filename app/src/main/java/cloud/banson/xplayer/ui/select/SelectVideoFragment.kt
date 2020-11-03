package cloud.banson.xplayer.ui.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import cloud.banson.xplayer.R
import cloud.banson.xplayer.databinding.SelectVideoFragmentBinding

class SelectVideoFragment : Fragment() {

    companion object {
        private const val TAG = "SelectVideoFragment"
    }

    private lateinit var viewModel: SelectVideoViewModel
    private lateinit var binding: SelectVideoFragmentBinding
    private lateinit var myAdapter: VideoInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        initView(inflater, container)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.select_video_fragment, container, false)

        myAdapter = VideoInfoAdapter {
            viewModel.onItemClick(it)
            this.findNavController()
                .navigate(SelectVideoFragmentDirections.actionSelectVideoFragmentToListFragment())
        }

        binding.selectRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = myAdapter
        }
    }

    private fun initViewModel() {
        val viewModelFactory = SelectVideoViewModelFactory(activity!!.applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SelectVideoViewModel::class.java)
        viewModel.videoListLive.observe(viewLifecycleOwner) {
            myAdapter.submitList(it)
        }
    }

}
