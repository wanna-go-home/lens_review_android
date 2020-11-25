package com.wannagohome.lens_review_android.ui.board.article.comment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.Comment
import com.wannagohome.lens_review_android.ui.board.article.comment.CommentMultiViewAdapter
import com.wannagohome.lens_review_android.ui.board.article.comment.CommentViewModel
import kotlinx.android.synthetic.main.activity_comment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CommentActivity : AppCompatActivity() {

    companion object {
        const val COMMENT_ID = "commentId"
    }
    //todo : remove after server api enabled
    var testComment = ArrayList<Comment>()
    private val commentViewModel : CommentViewModel by viewModel()

    private val commentAdapter = CommentMultiViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val commentId = intent.getIntExtra(COMMENT_ID, -1)

        if (commentId == -1) {
            Timber.d("comment Id $commentId")
            //TODO error handling with UI

        }
        initCommentRecyclerView()

        //todo : remove after server api enabled
        testComment.add(Comment(2,"쓰니2",1,5,"두번째 댓글입니다",1,"3일전",0,2))
        testComment.add(Comment(5,"쓰니5",1,0,"다섯번째 댓글입니다",65,"4일전",1,2))
        testComment.add(Comment(6,"쓰니6",1,0,"여섯번째 댓글입니다",651,"4일전",1,2))
        testComment.add(Comment(7,"쓰니7",1,0,"일곱번째 댓글입니다",52,"4일전",1,2))
        testComment.add(Comment(8,"쓰니8",1,0,"여덞번째 댓글입니다",56,"5일전",1,2))
        testComment.add(Comment(9,"쓰니9",1,0,"아홉번째 댓글입니다",25,"5일전",1,2))
        commentAdapter.commentList = testComment

//        observeEvent()
//        commentViewModel.getComments(commentId)
    }
    private fun initCommentRecyclerView() {
        commentRecyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
        }
    }
    private fun observeEvent(){
        commentViewModel.comments.observe(this, Observer {
            commentAdapter.commentList = ArrayList(it)
        })
    }
}