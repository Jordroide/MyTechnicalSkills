package com.jordroid.showcase.gallery.presenter.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jordroid.showcase.R
import com.jordroid.showcase.databinding.GalleryItemBinding
import com.jordroid.showcase.gallery.presenter.model.DogPictureUi

/**
 * DiffUtils used for detect change in list
 */
private val diffItemUtils = object : DiffUtil.ItemCallback<DogPictureUi>() {
    override fun areItemsTheSame(oldItem: DogPictureUi, newItem: DogPictureUi) =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: DogPictureUi,
        newItem: DogPictureUi
    ): Boolean {
        return oldItem == newItem
    }
}

class GalleryDoggoAdapter :
    PagingDataAdapter<DogPictureUi, DoggoGalleryViewHolder>(diffItemUtils) {

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

    private lateinit var item: DogPictureUi

    fun bind(dogPictureUi: DogPictureUi) {
        item = dogPictureUi
        binding.galleryItemIv.load(item.url) {
            placeholder(R.drawable.ic_baseline_pets_24)
        }
    }
}
