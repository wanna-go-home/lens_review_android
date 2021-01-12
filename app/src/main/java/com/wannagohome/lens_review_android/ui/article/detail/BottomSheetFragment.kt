package com.wannagohome.lens_review_android.ui.article.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.databinding.FragmentBottomsheetDialogBinding
import com.wannagohome.lens_review_android.extension.gone
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class BottomSheetFragment : BottomSheetDialogFragment() {
    companion object {
        const val TARGET_ID = "targetId"
        const val IS_AUTHOR = "isAuthor"
        fun newInstance(targetId: Int, isAuthor: Boolean): BottomSheetFragment {
            val fragment = BottomSheetFragment()
            val args = Bundle()
            args.putInt(TARGET_ID, targetId)
            args.putBoolean(IS_AUTHOR, isAuthor)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var callback: OnClickListener
    private var _binding: FragmentBottomsheetDialogBinding? = null
    private val binding get() = _binding!!
    private var targetId = -1
    private var isAuthor = false

    fun setOnClickListener(callback: OnClickListener) {
        this.callback = callback
    }

    interface OnClickListener {
        fun onClickDeleteBtn(targetId: Int)
        fun onClickModifyBtn(targetId: Int)
        fun onClickReportBtn(targetId: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBottomsheetDialogBinding.inflate(inflater, container, false)
        arguments?.getInt(TARGET_ID)?.let {
            targetId = it
        }
        arguments?.getBoolean(IS_AUTHOR)?.let {
            isAuthor = it
        }
        if (isAuthor) {
            binding.btnReport.gone()
        } else {
            binding.btnModify.gone()
            binding.btnDelete.gone()
        }
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
                callback.onClickModifyBtn(targetId)
                dismiss()
            }
        binding.btnDelete.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                callback.onClickDeleteBtn(targetId)
                dismiss()
            }
        binding.btnReport.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                callback.onClickReportBtn(targetId)
                dismiss()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


}