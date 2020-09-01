package com.wannagohome.lens_review_android.ui.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.ui.BoardViewModel
import kotlinx.android.synthetic.main.fragment_board.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

class TabBoard : Fragment(), KoinComponent {
    companion object{
        val instance = TabBoard()
    }

    private val boardViewModel: BoardViewModel by viewModel()

    private val boardListAdapter = BoardListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBoardListRecyclerView()

        observeEvent()

        boardViewModel.getArticleList()

    }

    private fun initBoardListRecyclerView() {
        articleListRecyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = boardListAdapter
        }
    }

    private fun observeEvent() {
        boardViewModel.articleList.observe(viewLifecycleOwner, Observer {
            boardListAdapter.articleList = ArrayList(it)
        })
    }

}