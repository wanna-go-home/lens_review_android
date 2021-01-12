package com.wannagohome.lens_review_android.ui.article.write

import android.os.Bundle
import android.text.SpannableStringBuilder
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ActivityWriteArticleBinding
import com.wannagohome.lens_review_android.support.Utils
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class WriteArticleActivity : BaseAppCompatActivity() {

    companion object {
        const val ARTICLE_ID = "articleId"
    }
    private var articleId = -1
    private var isModify = false
    private val writeArticleViewModel: WriteArticleViewModel by viewModel { parametersOf(articleId) }

    private lateinit var binding: ActivityWriteArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        articleId = intent.getIntExtra(ARTICLE_ID, -1)
        if (articleId != -1) {
            isModify = true
        }

        setOnclickListener()

        observeEvent()

        writeArticleViewModel.getArticle()
    }

    private fun setOnclickListener(){
        binding.backBtn.clicks()
            .observeOn(AndroidSchedulers.mainThread())
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
                if (isModify){
                    writeArticleViewModel.modifyArticle(title, content)
                }
                else{
                    writeArticleViewModel.writeArticle(title, content)
                }
            }

    }
    private fun observeEvent() {
        writeArticleViewModel.writeSuccess.observe(this, {
            if (it) {
                finishActivityToRight()
            }
        })
        if (isModify){
            writeArticleViewModel.article.observe(this, {
                binding.title.text = SpannableStringBuilder(it.title)
                binding.content.text = SpannableStringBuilder(it.content)
            })
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishActivityToRight()
    }
}