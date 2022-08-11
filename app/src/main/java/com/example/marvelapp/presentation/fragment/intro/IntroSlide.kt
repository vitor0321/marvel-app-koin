package com.example.marvelapp.presentation.fragment.intro

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.presentation.common.extensions.inflate

data class IntroSlide(
    @DrawableRes val icon: Int,
    @StringRes val title: Int?,
    @StringRes val description: Int?
)

class IntroSlideAdapter(items: List<IntroSlide>) : BaseAdapter<IntroSlide>() {

    init {
        setItems(items)
    }

    inner class IntroSlideViewHolder(view: View) : BaseViewHolder<IntroSlide>(view) {
        override fun bindView(item: IntroSlide) {
            with(itemView) {
                findViewById<ImageView>(R.id.intro_slide_icon).setImageResource(item.icon)
                item.title?.let { findViewById<TextView>(R.id.intro_slide_title).setText(it) }
                item.description?.let {
                    findViewById<TextView>(R.id.intro_slide_description).setText(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<IntroSlide> {
        val itemView = parent.inflate(viewType)
        return when (viewType) {
            R.layout.item_intro_slide -> IntroSlideViewHolder(itemView)
            else -> throw IllegalStateException("unknown view type: $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.item_intro_slide
            items.size - 1 -> R.layout.item_intro_slide
            else -> R.layout.item_intro_slide
        }
    }
}

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>() {

    protected val items: MutableList<T> = mutableListOf()

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) =
        holder.bindView(items[position])

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    open fun setItems(newItems: List<T>) {
        getDiffUtilCallback(items.toList(), newItems)
            .also { items.clear() }
            .also { items.addAll(newItems) }
            ?.let { DiffUtil.calculateDiff(it).dispatchUpdatesTo(this) }
            ?: notifyDataSetChanged()
    }

    open fun getDiffUtilCallback(oldItems: List<T>, newItems: List<T>): DiffUtil.Callback? = null

    abstract class BaseViewHolder<V>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bindView(item: V)
    }
}
