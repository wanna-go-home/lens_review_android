package com.wannagohome.lens_review_android.ui.article.detail.comment

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.databinding.FragmentEditCommentBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class CommentEditFragment : DialogFragment() {
    companion object {
        const val TARGET_ID = "targetId"
        const val CONTENT = "content"
        fun newInstance(targetId: Int, content: String?): CommentEditFragment {
            val fragment = CommentEditFragment()
            val args = Bundle()
            args.putInt(TARGET_ID, targetId)
            args.putString(CONTENT, content)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var callback: OnClickListener
    private var _binding: FragmentEditCommentBinding? = null
    private val binding get() = _binding!!
    private var targetId = -1
    private var content = ""

    fun setOnClickListener(callback: OnClickListener) {
        this.callback = callback
    }

    interface OnClickListener {
        fun onClickModifyPostBtn(targetId: Int, content: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditCommentBinding.inflate(inflater, container, false)
        arguments?.getInt(TARGET_ID)?.let {
            targetId = it
        }
        arguments?.getString(CONTENT)?.let {
            content = it
        }
        binding.content.text = SpannableStringBuilder(content)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListener(targetId)
    }

    private fun addListener(targetId: Int) {
        binding.btnModify.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                val content = binding.content.text.toString()
                callback.onClickModifyPostBtn(targetId, content)
                dismiss()
            }
        binding.btnCancel.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                dismiss()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


}