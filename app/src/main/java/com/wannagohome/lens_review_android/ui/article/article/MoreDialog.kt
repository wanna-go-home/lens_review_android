package com.wannagohome.lens_review_android.ui.article.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wannagohome.lens_review_android.databinding.FragmentDialogOptionBinding
import com.wannagohome.lens_review_android.ui.article.article.modify.ModifyArticleActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoreDialog : DialogFragment() {

    companion object {
        const val ARTICLE_ID = "articleId"
        fun newInstance(articleId: Int): MoreDialog {
            val fragment = MoreDialog()
            val args = Bundle()
            args.putInt(ARTICLE_ID, articleId)
            fragment.setArguments(args)
            return fragment
        }
    }
    private val articleViewModel: ArticleViewModel by sharedViewModel()
    private var _binding: FragmentDialogOptionBinding? = null
    private val binding get() = _binding!!
    private var articleId = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDialogOptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(ARTICLE_ID)?.let {
            articleId = it
        }
        addListener()
    }

    private fun addListener() {
        binding.btnModify.setOnClickListener {
            val intent = Intent(requireContext(), ModifyArticleActivity::class.java)
            intent.putExtra(ARTICLE_ID, articleId)
            startActivity(intent)
            dismiss()
        }
        binding.btnDelete.setOnClickListener {
            articleViewModel.deleteArticle(articleId)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


}