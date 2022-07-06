package com.example.marvelapp.presentation.sort

import android.os.Bundle
import android.view.View
import androidx.core.view.forEach
import com.example.core.domain.model.SortingType
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentSortBinding
import com.example.marvelapp.presentation.common.extensions.toast
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel

class SortFragment : BaseBottomSheetDialogFragment<FragmentSortBinding>() {

    override fun getViewBinding(): FragmentSortBinding =
        FragmentSortBinding.inflate(layoutInflater)

    private val viewModel: SortViewModel by viewModel()

    private var orderBy = SortingType.ORDER_BY_NAME.value
    private var order = SortingType.ORDER_BY_ASCENDING.value

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setChipGroupListeners()
        observerUiState()
    }

    private fun setChipGroupListeners() {
        binding.chipGroupOrderBy.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            orderBy = getOrderByValue(chip.id)
        }
        binding.chipGroupOrder.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            order = getOrderValue(chip.id)
        }
        binding.buttonApplySort.setOnClickListener {
            viewModel.applySorting(orderBy, order)
        }
    }

    private fun observerUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is SortViewModel.UiState.SortingResult -> {
                    val orderBy = uiState.storageSorting.first
                    val order = uiState.storageSorting.second
                    binding.chipGroupOrderBy.forEach {
                        val chip = it as Chip
                        if (getOrderByValue(chip.id) == orderBy) {
                            chip.isChecked = true
                        }
                    }
                    binding.chipGroupOrder.forEach {
                        val chip = it as Chip
                        if (getOrderValue(chip.id) == order) {
                            chip.isChecked = true
                        }
                    }
                }
                is SortViewModel.UiState.ApplyState.Loading ->
                    binding.flipperApply.displayedChild = FLIPPER_CHILD_LOADING
                is SortViewModel.UiState.ApplyState.Success -> {
                    binding.apply {
                        buttonApplySort.text = requireContext().getString(R.string.sort_apply)
                        binding.flipperApply.displayedChild = FLIPPER_CHILD_BUTTON
                    }
                }
                is SortViewModel.UiState.ApplyState.Error -> {
                    binding.apply {
                        toast(requireContext().getString(R.string.sort_apply_something_wrong))
                        buttonApplySort.text =
                            requireContext().getString(R.string.sort_apply_try_again)
                        flipperApply.displayedChild = FLIPPER_CHILD_ERROR
                    }
                }
            }

        }
    }

    private fun getOrderByValue(chipId: Int): String = when (chipId) {
        R.id.chip_name -> SortingType.ORDER_BY_NAME.value
        R.id.chip_modified -> SortingType.ORDER_BY_MODIFIED.value
        else -> SortingType.ORDER_BY_NAME.value
    }

    private fun getOrderValue(chipId: Int): String = when (chipId) {
        R.id.chip_ascending -> SortingType.ORDER_BY_ASCENDING.value
        R.id.chip_descending -> SortingType.ORDER_BY_DESCENDING.value
        else -> SortingType.ORDER_BY_ASCENDING.value
    }

    companion object {
        const val FLIPPER_CHILD_BUTTON = 0
        const val FLIPPER_CHILD_LOADING = 1
        const val FLIPPER_CHILD_ERROR = 1
    }
}