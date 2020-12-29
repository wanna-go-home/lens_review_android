package com.wannagohome.lens_review_android.ui.review.write

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.databinding.SelectLensFragmentBinding
import com.wannagohome.lens_review_android.databinding.WriteReviewSelectLensDialogBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import okhttp3.internal.addHeaderLenient
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class SelectLensFragment : Fragment() {

    companion object {
        val instance = SelectLensFragment()
    }

    private val writeReviewViewModel: WriteReviewViewModel by sharedViewModel()

    private var _binding: SelectLensFragmentBinding? = null

    private val binding: SelectLensFragmentBinding
        get() = _binding!!

    private val selectLensAdapter = SelectLensAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = SelectLensFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEvents()

        writeReviewViewModel.getLensList()

        binding.selectLensConstainer.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showSelectLensDialog()
            }

        initSelectLensAdapter()
    }

    private fun initSelectLensAdapter() {

    }

    private fun showSelectLensDialog() {
        val dialogViewBinding = WriteReviewSelectLensDialogBinding.inflate(layoutInflater, null, false)

        dialogViewBinding.selectLensListRecyclerView.run {
            adapter = selectLensAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .create()

        builder.setView(dialogViewBinding.root)
        builder.show()
    }

    private fun observeEvents() {
        writeReviewViewModel.lensListLiveData.observe(viewLifecycleOwner, {
            selectLensAdapter.items = it
        })

        writeReviewViewModel.selectedLensLiveData.observe(viewLifecycleOwner, {selectedLens->
            Glide.with(this).load(selectedLens.productImages[0]).into(binding.selectedLensImage)
            binding.selectedLensName.text = selectedLens.name

            selectLensAdapter.items.first{it.lensId == selectedLens.lensId}.selected = true
            selectLensAdapter.notifyDataSetChanged()

            Timber.d("selected live " + selectedLens.lensId)

        })

        writeReviewViewModel.previousSelectedLensLiveData.observe(viewLifecycleOwner, {deSelectedLens->
            selectLensAdapter.items.first{deSelectedLens.lensId == it.lensId}.selected = false
            selectLensAdapter.notifyDataSetChanged()

            Timber.d("de selected live " + deSelectedLens.lensId)
        })

    }


}