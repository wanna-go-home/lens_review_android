package com.wannagohome.lens_review_android.support.di

import com.wannagohome.lens_review_android.ui.board.BoardViewModel
import com.wannagohome.lens_review_android.ui.lens_detail.LensDetailViewModel
import com.wannagohome.lens_review_android.ui.review.ReviewPreviewViewModel
import com.wannagohome.lens_review_android.ui.search_lens.LensViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { LensViewModel() }
    viewModel { BoardViewModel() }
    viewModel { LensDetailViewModel() }
    viewModel { ReviewPreviewViewModel() }
}