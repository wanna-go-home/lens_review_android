package com.wannagohome.viewty.ui.bulletin.article.write

import android.os.Bundle
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.R
import com.wannagohome.viewty.databinding.ActivityWriteArticleBinding
import com.wannagohome.viewty.support.Utils
import com.wannagohome.viewty.support.baseclass.BaseAppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class WriteArticleActivity : BaseAppCompatActivity() {

    companion object {
        const val ARTICLE_ID = "articleId"
    }
    private var articleId = -1
    private var isModify = false
//    private val writeArticleViewModel: WriteArticleViewModel by viewModel { parametersOf(articleId) }

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

//        writeArticleViewModel.getArticle()
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
//                if (isModify){
//                    writeArticleViewModel.modifyArticle(title, content)
//                }
//                else{
//                    writeArticleViewModel.writeArticle(title, content)
//                }
            }

    }
    private fun observeEvent() {
//        writeArticleViewModel.writeSuccess.observe(this, {
//            if (it) {
//                finishActivityToRight()
//            }
//        })
//        if (isModify){
//            writeArticleViewModel.article.observe(this, {
//                binding.title.setText(it.title)
//                binding.content.setText(it.content)
//            })
//        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishActivityToRight()
    }
}