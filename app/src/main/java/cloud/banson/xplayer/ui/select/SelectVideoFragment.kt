package cloud.banson.xplayer.ui.select

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import cloud.banson.xplayer.R
import cloud.banson.xplayer.databinding.SelectVideoFragmentBinding

class SelectVideoFragment : Fragment() {

    companion object {
        fun newInstance() = SelectVideoFragment()
    }

    private lateinit var viewModel: SelectVideoViewModel
    private lateinit var binding: SelectVideoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initView(inflater, container)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SelectVideoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.select_video_fragment, container, false)
        val myAdapter = VideoAdapter()
        binding.selectRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = myAdapter
        }
    }

}