package com.jordroid.showcase.quotes.presenter.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jordroid.showcase.databinding.QuoteItemBinding
import com.jordroid.showcase.databinding.QuoteItemHeaderBinding
import com.jordroid.showcase.quotes.presenter.model.QuoteUi
import com.jordroid.showcase.quotes.presenter.model.QuoteUi.QuoteHeaderUi
import com.jordroid.showcase.quotes.presenter.model.QuoteUi.QuoteItemUi
import com.jordroid.showcase.quotes.presenter.view.adapter.QuoteViewType.HEADER
import com.jordroid.showcase.quotes.presenter.view.adapter.QuoteViewType.ROW

/**
 * DiffUtils used for detect change in list
 */
private val diffItemUtils = object : DiffUtil.ItemCallback<QuoteUi>() {
    override fun areItemsTheSame(oldItem: QuoteUi, newItem: QuoteUi) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: QuoteUi, newItem: QuoteUi): Boolean {
        return when {
            oldItem is QuoteItemUi && newItem is QuoteItemUi -> oldItem.character == newItem.character && oldItem.quote == newItem.quote
            oldItem is QuoteHeaderUi && newItem is QuoteHeaderUi -> oldItem.animeName == newItem.animeName
            else -> oldItem == newItem
        }
    }
}

/**
 * Quote adapter to display item and or header
 * @param onItemClick action to make when click on item
 */
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
            else -> throw IllegalStateException("Wrong view type received $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder.itemViewType) {
            ROW.viewType -> (holder as QuoteViewHolder).bind(getItem(position) as QuoteItemUi)
            HEADER.viewType -> (holder as QuoteHeaderViewHolder).bind(getItem(position) as QuoteHeaderUi)
            else -> throw IllegalStateException("Wrong view type received ${holder.itemView}")
        }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is QuoteItemUi -> ROW.viewType
            is QuoteHeaderUi -> HEADER.viewType
        }
    }
}

/**
 * Quote view holder class
 */
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
        binding.quoteItem = ui
    }
}

/**
 * Quote header view holder class
 */
class QuoteHeaderViewHolder(
    private val binding: QuoteItemHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var header: QuoteHeaderUi

    fun bind(quoteItemHeader: QuoteHeaderUi) {
        header = quoteItemHeader
        binding.quoteItemHeader = quoteItemHeader
    }
}

/**
 * Enumeration that show the different type of view the adapter must handle
 */
enum class QuoteViewType(val viewType: Int) {
    ROW(0),
    HEADER(1)
}
