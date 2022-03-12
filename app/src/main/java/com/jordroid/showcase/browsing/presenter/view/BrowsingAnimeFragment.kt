package com.jordroid.showcase.browsing.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.jordroid.showcase.browsing.presenter.view.adapter.QuoteAnimeAdapter
import com.jordroid.showcase.browsing.presenter.viewmodel.BrowsingAnimeViewModel
import com.jordroid.showcase.databinding.BrowsingAnimeFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BrowsingAnimeFragment : Fragment() {

    private val viewModel: BrowsingAnimeViewModel by viewModel()

    private var _binding: BrowsingAnimeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BrowsingAnimeFragmentBinding.inflate(inflater, container, false)

        val quoteAnimeAdapter = QuoteAnimeAdapter()

        binding.quoteAnimeRv.adapter = quoteAnimeAdapter

        lifecycleScope.launchWhenStarted {
            viewModel.getQuoteAnimeName().collect {
                quoteAnimeAdapter.submitList(it)
            }
        }

        viewModel.fetchAnimeData()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
