package com.example.marvelapp.presentation.common

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.marvelapp.presentation.common.adapterGeneric.GenericDiffCallback
import com.example.marvelapp.presentation.common.adapterGeneric.GenericViewHolder
import com.example.marvelapp.presentation.common.adapterGeneric.ListItem

inline fun <T : ListItem, VH : GenericViewHolder<T>> getGenericAdapterOf(
    crossinline createViewHolder: (ViewGroup) -> VH
): ListAdapter<T, VH> {

    val diff = GenericDiffCallback<T>()

    return object : ListAdapter<T, VH>(diff) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return createViewHolder(parent)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind(getItem(position))
        }
    }
}