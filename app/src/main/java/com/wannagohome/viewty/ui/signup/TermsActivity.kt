package com.wannagohome.viewty.ui.signup

import android.os.Bundle
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.BuildConfig
import com.wannagohome.viewty.databinding.ActivityTermsBinding
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.support.baseclass.BaseAppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsActivity : BaseAppCompatActivity() {

    companion object {
        const val TERMS_URL = "${BuildConfig.API_HOST}/terms.html"
    }

    lateinit var binding: ActivityTermsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTermsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.termsWebView.loadUrl(TERMS_URL)

        binding.confirmBtn.clicks()
            .subscribe {
                finish()
            }.addTo(compositeDisposable)
    }
}