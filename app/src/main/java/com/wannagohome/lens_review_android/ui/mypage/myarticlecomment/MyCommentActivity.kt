package com.wannagohome.lens_review_android.ui.mypage.myarticlecomment

import android.os.Bundle
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity

class MyCommentActivity : BaseAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_comment)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finishActivityToRight()
    }
}