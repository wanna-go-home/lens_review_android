package com.wannagohome.lens_review_android.ui.lens_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.wannagohome.lens_review_android.R
import kotlinx.android.synthetic.main.activity_detailed_lens.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class LensDetailActivity : AppCompatActivity() {

    companion object {
        const val DETAILED_LENS_ID = "lensId"
    }

    private val lensDetailViewModel : LensDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_lens)

        val lensId = intent.getIntExtra(DETAILED_LENS_ID, -1)

        if (lensId == -1) {
            Timber.d("lens id $lensId")
            //TODO error handling with UI

        }

        observeEvent()

        lensDetailViewModel.getLensDetail(lensId)
    }

    private fun observeEvent(){
        lensDetailViewModel.detailedLens.observe(this, Observer {
            lensName.text = it.name
        })
    }
}