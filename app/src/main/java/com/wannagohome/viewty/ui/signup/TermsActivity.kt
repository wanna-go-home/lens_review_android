package com.wannagohome.viewty.ui.signup

import android.os.Bundle
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.BuildConfig
import com.wannagohome.viewty.databinding.ActivityTermsBinding
import com.wannagohome.viewty.support.baseclass.BaseAppCompatActivity


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