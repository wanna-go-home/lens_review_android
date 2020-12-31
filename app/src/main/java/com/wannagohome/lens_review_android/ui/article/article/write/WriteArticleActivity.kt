package com.wannagohome.lens_review_android.ui.article.article.write

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ActivityWriteArticleBinding
import com.wannagohome.lens_review_android.support.Utils
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class WriteArticleActivity : BaseAppCompatActivity() {

    private val writeArticleViewModel: WriteArticleViewModel by viewModel()

    private lateinit var binding: ActivityWriteArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                finishActivityToRight()
            }

        binding.writeBtn.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                val title = binding.title.text.toString()
                val content = binding.content.text.toString()
                if (content.isEmpty() || title.isEmpty()) {
                    Utils.showToast(getString(R.string.write_need_content))
                    return@subscribe
                }
                writeArticleViewModel.writeArticle(title, content)
            }

        writeArticleViewModel.writeSuccess.observe(this, {
            if (it) {
                finishActivityToRight()
            }
        })
    }
    override fun onBackPressed() {
        super.onBackPressed()

        finishActivityToRight()

    }
}