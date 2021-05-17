package com.wannagohome.viewty.ui.search_lens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.viewty.databinding.FragmentSearchBinding
import com.wannagohome.viewty.ui.lens_detail.LensDetailActivity
import com.wannagohome.viewty.ui.lens_detail.LensDetailActivity.Companion.DETAILED_LENS_ID
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TabSearch : Fragment() {
    companion object {
        fun newInstance() = TabSearch()
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val lensViewModel by activityViewModels<LensViewModel>()

    private val lensListAdapter = LensListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLensListRecyclerView()

        observeEvent()

        lensViewModel.getLensList()

    }

    private fun initLensListRecyclerView() {

        binding.lensListRecyclerView.run {
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(activity)

            lensListAdapter.onItemClick = { pos ->
                val lens = lensListAdapter.getItem(pos)

                val intent = Intent(activity, LensDetailActivity::class.java)
                intent.putExtra(DETAILED_LENS_ID, lens.lensId)
                activity?.startActivity(intent)
            }

            adapter = lensListAdapter
        }
    }

    private fun observeEvent() {
        lensViewModel.lensList.observe(activity!!, {
            lensListAdapter.items = it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("kkkkkkk")
    }
}