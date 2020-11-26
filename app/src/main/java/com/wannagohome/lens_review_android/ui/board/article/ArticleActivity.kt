package com.wannagohome.lens_review_android.ui.board.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.Comment
import com.wannagohome.lens_review_android.network.model.helper.dateHelper
import kotlinx.android.synthetic.main.activity_article.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ArticleActivity : AppCompatActivity() {

    companion object {
        const val ARTICLE_ID = "articleId"
    }
    //todo : remove after server api enabled
    var testComment = ArrayList<Comment>()
    private val articleViewModel : ArticleViewModel by viewModel()

    private val commentAdapter = CommentMultiViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val articleId = intent.getIntExtra(ARTICLE_ID, -1)

        if (articleId == -1) {
            Timber.d("article Id $articleId")
            //TODO error handling with UI

        }
        initCommentRecyclerView()
        observeEvent()
        articleViewModel.getArticle(articleId)
//        articleViewModel.getComments(articleId)
        //todo : remove after server api enabled
        testComment.add(Comment(1,"쓰니1",1,0,"첫번째 댓글입니다",5,"3일전",0,1))
        testComment.add(Comment(2,"쓰니2",1,5,"두번째 댓글입니다",1,"3일전",0,2))
        testComment.add(Comment(5,"쓰니5",1,0,"다섯번째 댓글입니다",65,"4일전",1,2))
        testComment.add(Comment(6,"쓰니6",1,0,"여섯번째 댓글입니다",651,"4일전",1,2))
        testComment.add(Comment(7,"쓰니7",1,0,"일곱번째 댓글입니다",52,"4일전",1,2))
//        testComment.add(Comment(8,"쓰니8",1,0,"여덞번째 댓글입니다",56,"5일전",1,2))
//        testComment.add(Comment(9,"쓰니9",1,0,"아홉번째 댓글입니다",25,"5일전",1,2))
        testComment.add(Comment(3,"쓰니3",1,3,"세번째 댓글입니다",53,"3일전",0,3))
        testComment.add(Comment(11,"쓰니11",1,0,"11 댓글입니다",51,"6일전",1,3))
        testComment.add(Comment(12,"쓰니12",1,0,"12 댓글입니다",51,"6일전",1,3))
//        testComment.add(Comment(4,"쓰니4",1,0,"네번째 댓글입니다",4,"3일전",0,4))
//        testComment.add(Comment(10,"쓰니10",1,0,"열번째 댓글입니다",51,"5일전",0,10))
        commentAdapter.commentList = testComment
    }
    private fun initCommentRecyclerView() {
        commentRecyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
        }
    }
    private fun observeEvent(){
        articleViewModel.article.observe(this, Observer {

            articleTitle.text = it.title
            content.text = it.content
            author.text = it.author
            likes.text = it.likes.toString()
            createdAt.text = dateHelper.calcCreatedBefore(it.createdAt) ?: ""
        })
        articleViewModel.comments.observe(this, Observer {
            commentAdapter.commentList = ArrayList(it)
        })
    }
}