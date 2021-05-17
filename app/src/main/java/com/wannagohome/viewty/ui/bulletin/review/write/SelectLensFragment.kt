package com.wannagohome.viewty.ui.bulletin.review.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.wannagohome.viewty.databinding.FragmentSelectLensBinding
import com.wannagohome.viewty.databinding.WriteReviewSelectLensDialogBinding
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.support.baseclass.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
@AndroidEntryPoint
class SelectLensFragment : BaseFragment() {

    companion object {
        fun newInstance() = SelectLensFragment()
    }

    private val writeReviewViewModel: WriteReviewViewModel by lazy { ViewModelProvider(requireActivity()).get(WriteReviewViewModel::class.java) }

    private var _binding: FragmentSelectLensBinding? = null

    private val binding: FragmentSelectLensBinding
        get() = _binding!!

    private val selectLensAdapter = SelectLensAdapter()

    private var selectedLensId = -1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSelectLensBinding.inflate(inflater, container, false)
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
            }.addTo(compositeDisposable)
        binding.selectLensDone.clicks()
            .subscribe {
                writeReviewViewModel.next()
            }.addTo(compositeDisposable)

        initSelectLensAdapter()
    }

    private fun initSelectLensAdapter() {
        selectLensAdapter.onItemClick = { pos ->
            selectLensAdapter.items.firstOrNull { it.selected }?.selected = false
            selectLensAdapter.items[pos].selected = true
            selectLensAdapter.notifyDataSetChanged()
        }
    }

    private fun showSelectLensDialog() {
        val dialogViewBinding = WriteReviewSelectLensDialogBinding.inflate(layoutInflater, null, false)

        dialogViewBinding.selectLensListRecyclerView.run {
            adapter = selectLensAdapter

            layoutManager = LinearLayoutManager(requireContext())

            addOnItemTouchListener(RecyclerItemTouchListener(requireContext(), object : RecyclerItemTouchListener.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    selectLensAdapter.onItemClick?.invoke(position)
                }
            }))
        }

        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .create()

        dialogViewBinding.searchLensText.textChanges()
            .subscribe {
                writeReviewViewModel.searchLens(it.toString())
            }.addTo(compositeDisposable)
        dialogViewBinding.cancel.clicks()
            .subscribe {

                builder.dismiss()
            }.addTo(compositeDisposable)
        dialogViewBinding.confirm.clicks()
            .subscribe {
                val selectedLensId = selectLensAdapter.items.first { it.selected }.lensId
                selectLensAdapter.items.first { it.selected }.selected = false
                writeReviewViewModel.selectLens(selectedLensId)
                builder.dismiss()
            }.addTo(compositeDisposable)

        builder.setView(dialogViewBinding.root)
        builder.show()
    }

    private fun observeEvents() {
        writeReviewViewModel.lensListLiveData.observe(viewLifecycleOwner, {
            selectLensAdapter.items = it.toMutableList()
            selectLensAdapter.refreshAdapter(selectedLensId)
        })

        writeReviewViewModel.selectedLensLiveData.observe(viewLifecycleOwner, { selectedLens ->
            Glide.with(this).load(selectedLens.productImages[0]).into(binding.selectedLensImage)
            binding.selectedLensName.text = selectedLens.name

            selectedLensId = selectedLens.lensId
        })
    }


}