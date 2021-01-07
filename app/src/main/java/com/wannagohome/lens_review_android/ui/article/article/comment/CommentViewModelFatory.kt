package com.wannagohome.lens_review_android.ui.article.article.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CommentViewModelFactory(private val articleId: Int, private val parentCommentId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
            return CommentViewModel(articleId, parentCommentId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}