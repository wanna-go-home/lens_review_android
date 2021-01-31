package com.wannagohome.lens_review_android.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wannagohome.lens_review_android.BuildConfig
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ActivityTermsBinding

class TermsActivity : AppCompatActivity() {

    companion object{
        const val TERMS_URL = "${BuildConfig.API_HOST}/terms.html"
    }

    lateinit var binding : ActivityTermsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTermsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.termsWebView.loadUrl(TERMS_URL)

    }
}