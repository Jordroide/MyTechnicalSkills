package com.jordroid.showcase.gallery.presenter.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jordroid.showcase.R
import com.jordroid.showcase.databinding.GalleryItemBinding
import com.jordroid.showcase.gallery.presenter.model.DoggoUi

/**
 * DiffUtils used for detect change in list
 */
private val diffItemUtils = object : DiffUtil.ItemCallback<DoggoUi>() {
    override fun areItemsTheSame(oldItem: DoggoUi, newItem: DoggoUi) =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: DoggoUi,
        newItem: DoggoUi
    ): Boolean {
        return oldItem == newItem
    }
}

class GalleryDoggoAdapter :
    PagingDataAdapter<DoggoUi, DoggoGalleryViewHolder>(diffItemUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoggoGalleryViewHolder {
        return DoggoGalleryViewHolder(
            GalleryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DoggoGalleryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

class DoggoGalleryViewHolder(private val binding: GalleryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var item: DoggoUi

    fun bind(doggoUi: DoggoUi) {
        item = doggoUi
        binding.galleryItemIv.load(item.url) {
            placeholder(R.drawable.ic_baseline_pets_24)
            crossfade(true)
            crossfade(750)
        }
    }
}
