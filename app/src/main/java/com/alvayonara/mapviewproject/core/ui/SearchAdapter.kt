package com.alvayonara.mapviewproject.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.alvayonara.mapviewproject.core.base.BaseAdapter
import com.alvayonara.mapviewproject.core.base.BaseViewHolder
import com.alvayonara.mapviewproject.core.data.source.remote.response.Autocomplete
import com.alvayonara.mapviewproject.core.data.source.remote.response.PredictionsItem
import com.alvayonara.mapviewproject.databinding.ItemRowSearchBinding

class SearchAdapter : BaseAdapter<PredictionsItem, SearchAdapter.SearchViewHolder>(diffCallBack) {

    var onItemClick: ((PredictionsItem) -> Unit)? = null

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<PredictionsItem>() {
            override fun areItemsTheSame(
                oldItem: PredictionsItem,
                newItem: PredictionsItem
            ): Boolean {
                return oldItem.place_id == newItem.place_id
            }

            override fun areContentsTheSame(
                oldItem: PredictionsItem,
                newItem: PredictionsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemRowSearchBinding
            .inflate(inflater, parent, false)
        return SearchViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val search = getItem(position)
        if (search != null)
            holder.bindView(search)

        holder.binding.rowSearch.setOnClickListener {
            onItemClick?.invoke(search)
        }
    }

    inner class SearchViewHolder(
        root: View,
        val binding: ItemRowSearchBinding
    ) : BaseViewHolder<PredictionsItem>(root) {
        override fun bindView(element: PredictionsItem) {
            binding.apply {
                tvSearchMainAddress.text = element.structured_formatting?.main_text ?: "-"
                tvSearchSecondaryAddress.text = element.structured_formatting?.secondary_text ?: "-"
            }
        }
    }
}