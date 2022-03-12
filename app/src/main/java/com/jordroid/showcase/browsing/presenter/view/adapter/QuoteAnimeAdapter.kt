package com.jordroid.showcase.browsing.presenter.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jordroid.showcase.browsing.presenter.model.AnimeUi
import com.jordroid.showcase.browsing.presenter.model.AnimeUi.AnimeHeaderUi
import com.jordroid.showcase.databinding.BrowsingAnimeItemBinding
import com.jordroid.showcase.databinding.BrowsingAnimeItemHeaderBinding

private val diffItemUtils = object : DiffUtil.ItemCallback<AnimeUi>() {

    override fun areItemsTheSame(oldItem: AnimeUi, newItem: AnimeUi): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AnimeUi, newItem: AnimeUi): Boolean {
        return when {
            oldItem is AnimeUi.AnimeItemUi && newItem is AnimeUi.AnimeItemUi -> oldItem.label == newItem.label
            oldItem is AnimeHeaderUi && newItem is AnimeHeaderUi -> oldItem.amount == newItem.amount
            else -> oldItem == newItem
        }
    }
}

/**
 * Anime adapter to display item and or header
 */
class QuoteAnimeAdapter : ListAdapter<AnimeUi, RecyclerView.ViewHolder>(diffItemUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            AnimeViewType.ROW.viewType -> {
                AnimeViewHolder(
                    BrowsingAnimeItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            AnimeViewType.HEADER.viewType -> {
                AnimeHeaderViewHolder(
                    BrowsingAnimeItemHeaderBinding.inflate(
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
            AnimeViewType.ROW.viewType -> (holder as AnimeViewHolder).bind(getItem(position) as AnimeUi.AnimeItemUi)
            AnimeViewType.HEADER.viewType -> (holder as AnimeHeaderViewHolder).bind(getItem(position) as AnimeHeaderUi)
            else -> throw IllegalStateException("Wrong view type received ${holder.itemView}")
        }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is AnimeUi.AnimeItemUi -> AnimeViewType.ROW.viewType
            is AnimeHeaderUi -> AnimeViewType.HEADER.viewType
        }
    }
}

class AnimeViewHolder(
    private val binding: BrowsingAnimeItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(animeItemUi: AnimeUi.AnimeItemUi) {
        binding.animeItemText.text = animeItemUi.label
    }
}

class AnimeHeaderViewHolder(
    private val binding: BrowsingAnimeItemHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var header: AnimeHeaderUi

    fun bind(animeItemHeaderUi: AnimeHeaderUi) {
        header = animeItemHeaderUi
        binding.animeItemHeader = header
    }
}

/**
 * Enumeration that show the different type of view the adapter must handle
 */
enum class AnimeViewType(val viewType: Int) {
    ROW(0),
    HEADER(1)
}
