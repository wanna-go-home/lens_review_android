package com.wannagohome.lens_review_android.ui.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.lens_review_android.databinding.FragmentBoardBinding
import com.wannagohome.lens_review_android.ui.article.article.ArticleActivity
import com.wannagohome.lens_review_android.ui.article.article.write.WriteArticleActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

class TabArticle : Fragment(), KoinComponent {

    companion object {
        val instance = TabArticle()
    }

    private var _binding: FragmentBoardBinding? = null
    private val binding get() = _binding!!

    private val boardViewModel: BoardViewModel by viewModel()

    private val boardListAdapter = ArticleListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBoardListRecyclerView()
        addWriteBtnListener()
        addOnRefreshListener()
        observeEvent()
    }
    override fun onStart(){
        super.onStart()
        boardViewModel.getArticleList()
    }
    private fun addWriteBtnListener() {
        binding.writeBtn.setOnClickListener {
            val intent = Intent(requireContext(), WriteArticleActivity::class.java)
            startActivity(intent)
        }
    }
    private fun addOnRefreshListener() {
        binding.swiperefresh.setOnRefreshListener{
            boardViewModel.refreshArticleList()
        }
    }
    private fun initBoardListRecyclerView() {
        binding.articleListRecyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)

            boardListAdapter.onItemClick = { pos ->
                val clickedArticle = boardListAdapter.getItem(pos)

                val intent = Intent(activity, ArticleActivity::class.java)
                intent.putExtra(ArticleActivity.ARTICLE_ID, clickedArticle.articleId)
                activity?.startActivity(intent)
            }

            adapter = boardListAdapter
        }
    }

    private fun observeEvent() {
        boardViewModel.articleList.observe(viewLifecycleOwner, {
            boardListAdapter.items = it
        })
        boardViewModel.refreshSuccess.observe(viewLifecycleOwner, {
            if (it) {
                binding.swiperefresh.isRefreshing = false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}