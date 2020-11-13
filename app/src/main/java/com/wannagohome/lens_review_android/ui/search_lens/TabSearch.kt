package com.wannagohome.lens_review_android.ui.search_lens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.Lens
import com.wannagohome.lens_review_android.network.model.LensPreview
import com.wannagohome.lens_review_android.ui.lens_detail.LensDetailActivity
import com.wannagohome.lens_review_android.ui.lens_detail.LensDetailActivity.Companion.DETAILED_LENS_ID
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.KoinComponent
import timber.log.Timber

class TabSearch : Fragment(), KoinComponent {
    companion object {
        val instance = TabSearch()
    }

    private val lensViewModel: LensViewModel by sharedViewModel()

    private val lensListAdapter = LensListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLensListRecyclerView()

        observeEvent()

        lensViewModel.getLensList()

    }

    private fun initLensListRecyclerView() {

        lensListRecyclerView.run {
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(activity)

            lensListAdapter.onItemClick = {pos ->
                val lens = lensListAdapter.getItem(pos)

                val intent = Intent(activity, LensDetailActivity::class.java)
                intent.putExtra(DETAILED_LENS_ID, lens.lensId)
                activity?.startActivity(intent)
            }

            adapter = lensListAdapter
        }
    }

    private fun observeEvent() {
        lensViewModel.lensList.observe(activity!!, Observer {
            lensListAdapter.items = it
        })
    }

}