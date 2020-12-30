package com.wannagohome.lens_review_android.ui.review.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.wannagohome.lens_review_android.databinding.FragmentSelectLensBinding
import com.wannagohome.lens_review_android.databinding.WriteReviewSelectLensDialogBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SelectLensFragment : Fragment() {

    companion object {
        val instance = SelectLensFragment()
    }

    private val writeReviewViewModel: WriteReviewViewModel by sharedViewModel()

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
            }
        binding.selectLensDone.clicks()
            .subscribe {
                writeReviewViewModel.next()
            }

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
            }
        dialogViewBinding.cancel.clicks()
            .subscribe {

                builder.dismiss()
            }
        dialogViewBinding.confirm.clicks()
            .subscribe {
                val selectedLensId = selectLensAdapter.items.first { it.selected }.lensId
                selectLensAdapter.items.first { it.selected }.selected = false
                writeReviewViewModel.selectLens(selectedLensId)
                builder.dismiss()
            }

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