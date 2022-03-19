package com.jordroid.showcase.gallery.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jordroid.showcase.databinding.GalleryFragmentBinding
import com.jordroid.showcase.gallery.presenter.view.adapter.GalleryDoggoAdapter
import com.jordroid.showcase.gallery.presenter.viewmodel.GalleryDoggoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryFragment : Fragment() {

    // ViewModel instance by Koin
    private val viewModel: GalleryDoggoViewModel by viewModel()

    // Binding
    private var _binding: GalleryFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GalleryFragmentBinding.inflate(layoutInflater, container, false)
        val rootView = binding.root

        val adapter = GalleryDoggoAdapter()

        binding.galleryRv.layoutManager =
            StaggeredGridLayoutManager(2, RecyclerView.VERTICAL).also {
                it.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
        binding.galleryRv.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.fetchDoggoImages().collect {
                adapter.submitData(it)
            }
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
