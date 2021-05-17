package com.wannagohome.viewty.ui.lens_detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.wannagohome.viewty.databinding.ActivityDetailedLensBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LensDetailActivity : AppCompatActivity() {

    companion object {
        const val DETAILED_LENS_ID = "lensId"
    }

    private val lensDetailViewModel by viewModels<LensDetailViewModel>()

    private lateinit var binding: ActivityDetailedLensBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedLensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lensId = intent.getIntExtra(DETAILED_LENS_ID, -1)

        if (lensId == -1) {
            Timber.d("lens id $lensId")
            //TODO error handling with UI

        }

        observeEvent()

        lensDetailViewModel.getLensDetail(lensId)

        //TODO 하드코딩 제거 혹은 api 부착
        binding.lensMaker.text = "메이커"

    }

    private fun observeEvent() {
        lensDetailViewModel.lensName.observe(this, {
            binding.lensName.text = it
        })

        lensDetailViewModel.lensPrice.observe(this, {
            binding.lensPrice.text = it
        })

        lensDetailViewModel.productImage.observe(this, {
            Glide.with(this).load(it).into(binding.lensProductImage)
        })

        lensDetailViewModel.lensGraphicDia.observe(this, {
            binding.lensGrahicDia.text = it
        })
    }
}