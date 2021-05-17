package com.wannagohome.viewty.ui.bulletin.review.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.databinding.FragmentWriteReviewBinding
import com.wannagohome.viewty.support.Utils
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class WriteReviewFragment : Fragment() {

    companion object {
        fun newInstance() = WriteReviewFragment()
    }

    private var _binding: FragmentWriteReviewBinding? = null
    private val binding: FragmentWriteReviewBinding
        get() = _binding!!

    private val writeReviewViewModel by activityViewModels<WriteReviewViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWriteReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addClickListener()

        observeEvents()
    }

    private fun addClickListener() {
        binding.titleBar.leftBtn.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                writeReviewViewModel.back()
            }

        binding.titleBar.rightBtn.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                writeReviewViewModel.writeReview(binding.titleEdit.text.toString(), binding.contentsEdit.text.toString())
            }
    }

    private fun observeEvents() {
        writeReviewViewModel.writeSuccess.observe(viewLifecycleOwner, {
            if (it) {
                requireActivity().finish()
            }
        })

        writeReviewViewModel.errMessageLiveData.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                Utils.showToast(it)
            }
        })


    }

}