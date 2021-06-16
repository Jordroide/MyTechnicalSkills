package com.jordroid.showcase.quote.anime.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.jordroid.showcase.databinding.QuoteAnimeFragmentBinding
import com.jordroid.showcase.quote.anime.presenter.view.adapter.QuoteAnimeAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuoteAnimeFragment : Fragment() {

    private val quoteAnimeViewModel: QuoteAnimeViewModel by viewModel()

    private var _binding: QuoteAnimeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = QuoteAnimeFragmentBinding.inflate(inflater, container, false)

        val quoteAnimeAdapter = QuoteAnimeAdapter()

        binding.quoteAnimeRv.adapter = quoteAnimeAdapter

        lifecycleScope.launchWhenStarted {
            quoteAnimeViewModel.getQuoteAnimeName().flowOn(Dispatchers.IO).collect {
                quoteAnimeAdapter.submitList(it)
            }
        }

        // TODO add swype refresh
        quoteAnimeViewModel.fetchAnimeData()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
