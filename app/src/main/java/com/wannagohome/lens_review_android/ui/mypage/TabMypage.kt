package com.wannagohome.lens_review_android.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.FragmentMypageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class TabMypage : Fragment() {

    private var _binding: FragmentMypageBinding? = null

    private val binding
        get() = _binding!!

    private val mypageViewModel: MypageViewModel by viewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)

        observeEvents()

        return binding.root
    }

    private fun observeEvents() {
        mypageViewModel.myReviewCount.observe(viewLifecycleOwner, {
            binding.myReviewCount.text = it.toString()
        })

        mypageViewModel.myArticleCount.observe(viewLifecycleOwner, {
            binding.myArticleCount.text = it.toString()
        })

        mypageViewModel.myNickname.observe(viewLifecycleOwner, {
            binding.nickname.text = it
        })

        mypageViewModel.myCommentCount.observe(viewLifecycleOwner, {
            binding.myCommentCount.text = it.toString()
        })
    }

    override fun onResume() {
        super.onResume()

        mypageViewModel.fetchMyInfo()
    }

    companion object {
        val instance = TabMypage()

    }
}