package com.jordroid.sampleinjection.quote.presenter.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jordroid.sampleinjection.databinding.QuoteItemBinding
import com.jordroid.sampleinjection.quote.presenter.model.QuoteItem

private val diffItemUtils = object : DiffUtil.ItemCallback<QuoteItem>() {

    override fun areItemsTheSame(oldItem: QuoteItem, newItem: QuoteItem) =
        oldItem.content == newItem.content

    override fun areContentsTheSame(oldItem: QuoteItem, newItem: QuoteItem) =
        oldItem.content == newItem.content && oldItem.category == newItem.category
}

class QuoteAdapter(
    private val onItemClick: (quoteItem: QuoteItem) -> Unit,
) : ListAdapter<QuoteItem, QuoteViewHolder>(diffItemUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        return QuoteViewHolder(
            QuoteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        return holder.bind(getItem(position))
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
