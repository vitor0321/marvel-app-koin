package com.example.marvelapp.presentation.fragment.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.databinding.ItemParentDetailBinding
import com.example.marvelapp.framework.imageloader.ImageLoader

class DetailParentAdapter(
    private val detailParentList: List<DetailParentVE>,
    private val imageLoad: ImageLoader
) : RecyclerView.Adapter<DetailParentAdapter.DetailsParentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsParentViewHolder {
        return DetailsParentViewHolder.create(parent, imageLoad)
    }

    override fun onBindViewHolder(holder: DetailsParentViewHolder, position: Int) {
        holder.bind(detailParentList[position])
    }

    override fun getItemCount(): Int = detailParentList.size

    class DetailsParentViewHolder(
        itemBinding: ItemParentDetailBinding,
        private val imageLoad: ImageLoader
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val textItemCategory: TextView = itemBinding.textItemCategory
        private val recyclerChildDetail: RecyclerView = itemBinding.recyclerChildDetail

        fun bind(detailParentVE: DetailParentVE) {
            textItemCategory.text = itemView.context.getString(detailParentVE.categoryStringResId)
            recyclerChildDetail.run {
                setHasFixedSize(true)
                adapter = DetailChildAdapter(detailParentVE.detailChildList, imageLoad)
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                imageLoad: ImageLoader
            ): DetailsParentViewHolder {
                val itemBinding = ItemParentDetailBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return DetailsParentViewHolder(itemBinding, imageLoad)
            }
        }
    }
}