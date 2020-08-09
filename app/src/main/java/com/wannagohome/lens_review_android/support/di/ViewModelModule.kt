package com.wannagohome.lens_review_android.support.di

import com.wannagohome.lens_review_android.ui.LensViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { LensViewModel() }
}