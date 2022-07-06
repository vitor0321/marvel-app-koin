package com.example.core.data.mapper

import com.example.core.data.StorageConstants
import com.example.core.domain.model.SortingType

interface SortingMapperUseCase {

    fun mapToPair(sorting: String): Pair<String, String>

    fun mapFromPair(sortingPair: Pair<String, String>): String
}

class SortingMapper : SortingMapperUseCase {

    override fun mapToPair(sorting: String): Pair<String, String> {
        val nameAscending =
            SortingType.ORDER_BY_NAME.value to SortingType.ORDER_BY_ASCENDING.value
        val nameDescending =
            SortingType.ORDER_BY_NAME.value to SortingType.ORDER_BY_DESCENDING.value
        val modifiedAscending =
            SortingType.ORDER_BY_MODIFIED.value to SortingType.ORDER_BY_ASCENDING.value
        val modifiedDescending =
            SortingType.ORDER_BY_MODIFIED.value to SortingType.ORDER_BY_DESCENDING.value

        return when (sorting) {
            StorageConstants.ORDER_BY_NAME_ASCENDING -> nameAscending
            StorageConstants.ORDER_BY_NAME_DESCENDING -> nameDescending
            StorageConstants.ORDER_BY_MODIFIED_ASCENDING -> modifiedAscending
            StorageConstants.ORDER_BY_MODIFIED_DESCENDING -> modifiedDescending
            else -> nameAscending
        }
    }

    override fun mapFromPair(sortingPair: Pair<String, String>): String {
        val orderBy = sortingPair.first
        val order = sortingPair.second

        return when (orderBy) {
            SortingType.ORDER_BY_NAME.value -> when (order) {
                SortingType.ORDER_BY_ASCENDING.value -> StorageConstants.ORDER_BY_NAME_ASCENDING
                SortingType.ORDER_BY_DESCENDING.value -> StorageConstants.ORDER_BY_NAME_DESCENDING
                else -> StorageConstants.ORDER_BY_NAME_ASCENDING
            }
            SortingType.ORDER_BY_MODIFIED.value -> when (order) {
                SortingType.ORDER_BY_ASCENDING.value -> StorageConstants.ORDER_BY_MODIFIED_ASCENDING
                SortingType.ORDER_BY_DESCENDING.value -> StorageConstants.ORDER_BY_MODIFIED_DESCENDING
                else -> StorageConstants.ORDER_BY_MODIFIED_ASCENDING
            }
            else -> StorageConstants.ORDER_BY_NAME_ASCENDING
        }
    }
}