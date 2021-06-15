package com.jordroid.showcase.quote.presenter.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jordroid.showcase.databinding.QuoteItemBinding
import com.jordroid.showcase.databinding.QuoteItemHeaderBinding
import com.jordroid.showcase.quote.presenter.model.QuoteGenericItem
import com.jordroid.showcase.quote.presenter.model.QuoteItem
import com.jordroid.showcase.quote.presenter.model.QuoteItemHeader

private val diffItemUtils = object : DiffUtil.ItemCallback<QuoteGenericItem>() {

    override fun areItemsTheSame(oldItem: QuoteGenericItem, newItem: QuoteGenericItem) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: QuoteGenericItem, newItem: QuoteGenericItem) =
        oldItem == newItem
}

class QuoteAdapter(
    private val onItemClick: (quoteItem: QuoteItem) -> Unit,
) : ListAdapter<QuoteGenericItem, RecyclerView.ViewHolder>(diffItemUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            0 -> {
                QuoteViewHolder(
                    QuoteItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), onItemClick
                )
            }
            else -> {
                QuoteHeaderViewHolder(
                    QuoteItemHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder.itemViewType) {
            0 -> (holder as QuoteViewHolder).bind(getItem(position) as QuoteItem)
            else -> (holder as QuoteHeaderViewHolder).bind(
                getItem(position) as QuoteItemHeader
            )
        }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is QuoteItem -> 0
            else -> 1
        }
    }
}

class QuoteViewHolder(
    private val binding: QuoteItemBinding,
    onItemClick: (quoteItem: QuoteItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var item: QuoteItem

    init {
        binding.root.setOnClickListener {
            onItemClick(item)
        }
    }

    fun bind(quoteItem: QuoteItem) {
        item = quoteItem
        binding.quoteItem = quoteItem
    }
}

class QuoteHeaderViewHolder(
    private val binding: QuoteItemHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var header: QuoteItemHeader

    fun bind(quoteItemHeader: QuoteItemHeader) {
        header = quoteItemHeader
        binding.quoteItemHeader = quoteItemHeader
    }
}
