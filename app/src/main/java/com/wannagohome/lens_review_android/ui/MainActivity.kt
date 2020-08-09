package com.wannagohome.lens_review_android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.lens_review_android.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {

    private val lensViewModel: LensViewModel by viewModel()

    private val lensListAdapter = LensListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLensListRecyclerView()

        observeEvent()

        lensViewModel.getLensList()
    }

    private fun initLensListRecyclerView() {
        lensListRecyclerView.run {
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = lensListAdapter
        }
    }

    private fun observeEvent() {
        lensViewModel.lensList.observe(this, Observer {
            lensListAdapter.lensList = ArrayList(it)
        })
    }
}