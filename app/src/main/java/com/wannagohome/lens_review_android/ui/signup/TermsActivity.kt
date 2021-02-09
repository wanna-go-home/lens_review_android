package com.wannagohome.lens_review_android.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.BuildConfig
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ActivityTermsBinding
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity


class TermsActivity : BaseAppCompatActivity() {

    companion object {
        const val TERMS_URL = "${BuildConfig.API_HOST}/terms.html"
    }

    lateinit var binding: ActivityTermsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTermsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.titleBar.leftBtn.clicks()
            .subscribe{
                finishActivityToRight()
            }

        binding.termsWebView.loadUrl(TERMS_URL)
    }
}