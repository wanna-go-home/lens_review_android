package com.wannagohome.lens_review_android.ui.mypage

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.DialogModifyNicknameBinding
import com.wannagohome.lens_review_android.databinding.FragmentMypageBinding
import com.wannagohome.lens_review_android.support.Utils
import com.wannagohome.lens_review_android.support.baseclass.BaseFragment
import com.wannagohome.lens_review_android.ui.login.LoginActivity
import com.wannagohome.lens_review_android.ui.mypage.myarticle.MyArticleActivity
import com.wannagohome.lens_review_android.ui.mypage.myreview.MyReviewActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel


class TabMypage : BaseFragment() {

    private var _binding: FragmentMypageBinding? = null

    private val binding
        get() = _binding!!

    private val mypageViewModel: MypageViewModel by viewModel()

    private lateinit var nicknameModifyDialogContents: DialogModifyNicknameBinding
    private lateinit var dialogBuilder: AlertDialog.Builder
    private var visibleDialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)

        initListener()

        observeEvents()

        return binding.root
    }

    private fun initListener() {
        binding.myReviewListMenu.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startActivityFromRight(requireActivity(), MyReviewActivity::class.java)
            }
        binding.myArticleListMenu.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startActivityFromRight(requireActivity(), MyArticleActivity::class.java)
            }
        binding.leaveMenu.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showLeaveDialog()
            }
        binding.changeNickname.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showModifyNicknameDialog()
            }

    }


    private fun showModifyNicknameDialog() {
        nicknameModifyDialogContents = DialogModifyNicknameBinding.inflate(layoutInflater, null, false)
        nicknameModifyDialogContents.nickname.setText(mypageViewModel.myNickname.value)

        dialogBuilder = AlertDialog.Builder(requireContext()).apply {
            setView(nicknameModifyDialogContents.root)
            setPositiveButton(Utils.getString(R.string.mypage_modify_nickname_confirm), null)
            setNegativeButton(Utils.getString(R.string.mypage_modify_nickname_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
        }

        visibleDialog = dialogBuilder.show()
        visibleDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val newNickname = nicknameModifyDialogContents.nickname.text.toString()

                mypageViewModel.modifyNickname(newNickname)

            }
    }

    private fun showLeaveDialog() {
        val builder = AlertDialog.Builder(context).apply {
            setTitle(Utils.getString(R.string.mypage_leave_dialog_title))

            setMessage(Utils.getString(R.string.mypage_leave_dialog_message))

            setPositiveButton(Utils.getString(R.string.mypage_leave_dialog_positive)) { _, _ ->
                mypageViewModel.leave()
            }

            setNegativeButton(Utils.getString(R.string.mypage_leave_dialog_negative)) { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun observeEvents() {
        mypageViewModel.myReviewCount.observe(viewLifecycleOwner, {
            binding.myReviewCount.text = it.toString()
        })

        mypageViewModel.myArticleCount.observe(viewLifecycleOwner, {
            binding.myArticleCount.text = it.toString()
        })

        mypageViewModel.myNickname.observe(viewLifecycleOwner, {
            binding.nickname.text = it
        })

        mypageViewModel.successModifyNickname.observe(viewLifecycleOwner, {
            visibleDialog?.dismiss()
        })

        mypageViewModel.myCommentCount.observe(viewLifecycleOwner, {
            binding.myCommentCount.text = it.toString()
        })

        mypageViewModel.successLeave.observe(viewLifecycleOwner, {
            requireActivity().finish()

            startActivity(requireActivity(), LoginActivity::class.java)

            Utils.showToast(Utils.getString(R.string.mypage_leave_success_result))
        })
    }

    override fun onResume() {
        super.onResume()

        mypageViewModel.fetchMyInfo()
    }

    companion object {
        val instance = TabMypage()

    }
}