package com.jordroid.showcase.quote.all.presenter.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jordroid.showcase.databinding.QuoteItemBinding
import com.jordroid.showcase.databinding.QuoteItemHeaderBinding
import com.jordroid.showcase.quote.all.presenter.model.QuoteUi
import com.jordroid.showcase.quote.all.presenter.model.QuoteUi.QuoteHeaderUi
import com.jordroid.showcase.quote.all.presenter.model.QuoteUi.QuoteItemUi
import com.jordroid.showcase.quote.all.presenter.view.adapter.QuoteViewType.*
import java.lang.IllegalStateException
import java.lang.RuntimeException

private val diffItemUtils = object : DiffUtil.ItemCallback<QuoteUi>() {

    override fun areItemsTheSame(oldItem: QuoteUi, newItem: QuoteUi) =
        oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: QuoteUi, newItem: QuoteUi): Boolean {
        return when {
            oldItem is QuoteItemUi && newItem is QuoteItemUi -> oldItem.character == newItem.character && oldItem.quote == newItem.quote
            oldItem is QuoteHeaderUi && newItem is QuoteHeaderUi -> oldItem.animeName == newItem.animeName
            else -> oldItem == newItem
        }
    }
}

class QuoteAdapter(
    private val onItemClick: (quoteUi: QuoteItemUi, view: View) -> Unit,
) : ListAdapter<QuoteUi, RecyclerView.ViewHolder>(diffItemUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            ROW.viewType -> {
                QuoteViewHolder(
                    QuoteItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), onItemClick
                )
            }
            HEADER.viewType -> {
                QuoteHeaderViewHolder(
                    QuoteItemHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw RuntimeException("Wrong view type received $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder.itemViewType) {
            ROW.viewType -> (holder as QuoteViewHolder).bind(getItem(position) as QuoteItemUi)
            HEADER.viewType -> (holder as QuoteHeaderViewHolder).bind(getItem(position) as QuoteHeaderUi)
            else -> throw RuntimeException("Wrong view type received ${holder.itemView}")
        }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is QuoteItemUi -> ROW.viewType
            is QuoteHeaderUi -> HEADER.viewType
        }
    }
}

class QuoteViewHolder(
    private val binding: QuoteItemBinding,
    onItemClick: (quoteUi: QuoteItemUi, view: View) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var ui: QuoteItemUi

    init {
        binding.root.setOnClickListener {
            onItemClick(ui, itemView)
        }
    }

    fun bind(quoteUi: QuoteItemUi) {
        ui = quoteUi
        binding.quoteItem = quoteUi
    }
}

class QuoteHeaderViewHolder(
    private val binding: QuoteItemHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var header: QuoteHeaderUi

    fun bind(quoteItemHeader: QuoteHeaderUi) {
        header = quoteItemHeader
        binding.quoteItemHeader = quoteItemHeader
    }
}

enum class QuoteViewType(val viewType: Int) {
    ROW(0),
    HEADER(1)
}
