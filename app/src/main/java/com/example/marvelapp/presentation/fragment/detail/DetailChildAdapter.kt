package com.example.marvelapp.presentation.fragment.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.databinding.ItemChildDetailBinding
import com.example.marvelapp.framework.imageloader.ImageLoader

class DetailChildAdapter(
    private val detailChildList: List<DetailChildVE>,
    private val imageLoad: ImageLoader
) : RecyclerView.Adapter<DetailChildAdapter.DetailsChildViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsChildViewHolder {
        return DetailsChildViewHolder.create(parent, imageLoad)
    }

    override fun onBindViewHolder(holder: DetailsChildViewHolder, position: Int) {
        holder.bind(detailChildList[position])
    }

    override fun getItemCount(): Int = detailChildList.size

    class DetailsChildViewHolder(
        itemBinding: ItemChildDetailBinding,
        private val imageLoad: ImageLoader
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val imageCategory: ImageView = itemBinding.imageItemCategory

        fun bind(detailChildVE: DetailChildVE) {
            imageLoad.load(imageCategory, detailChildVE.imageUrl)
        }

        companion object {
            fun create(
                parent: ViewGroup,
                imageLoad: ImageLoader
            ): DetailsChildViewHolder {
                val itemBinding = ItemChildDetailBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return DetailsChildViewHolder(itemBinding, imageLoad)
            }
        }
    }
}