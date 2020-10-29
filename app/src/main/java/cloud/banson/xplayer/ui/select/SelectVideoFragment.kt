package cloud.banson.xplayer.ui.select

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import cloud.banson.xplayer.R
import cloud.banson.xplayer.databinding.SelectVideoFragmentBinding

class SelectVideoFragment : Fragment() {

    companion object {
        fun newInstance() = SelectVideoFragment()
    }

    private lateinit var viewModel: SelectVideoViewModel
    private lateinit var binding: SelectVideoFragmentBinding
    private val myAdapter = VideoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initView(inflater, container)
        initData()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.select_video_fragment, container, false)
        binding.selectRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = myAdapter
        }
    }

    private fun initData() {
        val viewModelFactory = SelectVideoViewModelFactory(activity!!.applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SelectVideoViewModel::class.java)
        viewModel.videoListLive.observe(viewLifecycleOwner) {
            myAdapter.submitList(it)
        }
    }
}