package com.wannagohome.lens_review_android.ui.board.article

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.Comment
import com.wannagohome.lens_review_android.ui.board.article.comment.CommentActivity
import kotlinx.android.synthetic.main.comment_list_item.view.*

const val COMMENT = 0
const val INNER_COMMENT = 1
class CommentMultiViewAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var commentList = ArrayList<Comment>()
        set(shops) {
            field = shops

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View?

        return when (viewType) {
            COMMENT -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.comment_list_item, parent, false)
                CommentViewHolder(parent, view)
            }
            INNER_COMMENT -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.child_comment_list_item, parent, false)
                ChildCommentViewHolder(view)
            }
            else -> throw RuntimeException("알 수 없는 뷰 타입 에러")
        }
    }

    override fun getItemCount() = commentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = commentList[position]
        when (obj.depth) {
            COMMENT -> (holder as CommentViewHolder).bind(obj)
            INNER_COMMENT -> (holder as ChildCommentViewHolder).bind(obj)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return commentList[position].depth
    }

    inner class CommentViewHolder(parent: ViewGroup, itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var currentComment : Comment
        var parent = parent
        init{

        }

        fun bind(comment: Comment) {
            currentComment = comment
            itemView.content.text = comment.content
            itemView.author.text = comment.authorId
            itemView.likes.text = comment.likes.toString()
            itemView.createdAt.text = comment.createdAt
            itemView.comments.setOnClickListener {
                // Handler code here.
                val intent = Intent(parent.context, CommentActivity::class.java)
                parent.context.startActivity(intent)
            }

            if (comment.childrenCnt > 3) {
                itemView.moreComment.visibility = VISIBLE
                itemView.moreComment.text = "+ 댓글 ${comment.childrenCnt-3}개 더 보기"
                itemView.moreComment.setOnClickListener {
                    // Handler code here.
                    val intent = Intent(parent.context, CommentActivity::class.java)
                    parent.context.startActivity(intent)
                }
            }

        }
    }
    inner class ChildCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var currentComment : Comment

        init{
        }

        fun bind(comment: Comment) {
            currentComment = comment
            itemView.content.text = comment.content
            itemView.author.text = comment.authorId
            itemView.createdAt.text = comment.createdAt
            itemView.likes.text = comment.likes.toString()
        }
    }
}