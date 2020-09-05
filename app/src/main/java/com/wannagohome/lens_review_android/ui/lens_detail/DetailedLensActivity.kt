package com.wannagohome.lens_review_android.ui.lens_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wannagohome.lens_review_android.R
import timber.log.Timber

class DetailedLensActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_lens)

        val lensId = intent.getIntExtra("lensId", -1)

        if (lensId != -1) {
            Timber.d("lens id $lensId")
        }
    }
}