package com.jordroid.showcase.quote.anime.presenter.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jordroid.showcase.databinding.QuoteAnimeItemBinding
import com.jordroid.showcase.quote.anime.presenter.model.QuoteAnimeItem

private val diffItemUtils = object : DiffUtil.ItemCallback<QuoteAnimeItem>() {

    override fun areItemsTheSame(oldItem: QuoteAnimeItem, newItem: QuoteAnimeItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: QuoteAnimeItem, newItem: QuoteAnimeItem): Boolean {
        return oldItem == newItem
    }
}

class QuoteAnimeAdapter : ListAdapter<QuoteAnimeItem, QuoteAnimeViewHolder>(diffItemUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteAnimeViewHolder {
        return QuoteAnimeViewHolder(
            QuoteAnimeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: QuoteAnimeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class QuoteAnimeViewHolder(
    private val binding: QuoteAnimeItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(quoteAnimeItem: QuoteAnimeItem) {
        binding.quoteAnimeItemText.text = quoteAnimeItem.animeName
    }
}
