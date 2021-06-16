package com.jordroid.showcase.quote.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.jordroid.showcase.R
import com.jordroid.showcase.databinding.QuoteListFragmentBinding
import com.jordroid.showcase.quote.presenter.model.QuoteGenericItem
import com.jordroid.showcase.quote.presenter.model.QuoteItem
import com.jordroid.showcase.quote.presenter.view.adapter.QuoteAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import org.koin.android.ext.android.inject

class QuoteListFragment : Fragment() {

    private val quoteViewModel: QuoteViewModel by inject()

    private val quoteAdapter = QuoteAdapter { item ->
        onItemClick(item)
    }

    private var _binding: QuoteListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = QuoteListFragmentBinding.inflate(layoutInflater)
        val rootView = binding.root

        binding.quoteRv.adapter = quoteAdapter
        binding.quoteRefresh.setOnRefreshListener {
            binding.motionLayout.transitionToEnd()
            quoteViewModel.fetchData()
        }

        lifecycleScope.launchWhenStarted {
            quoteViewModel.getQuote().flowOn(Dispatchers.IO).collect {
                updateAdapter(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            quoteViewModel.quoteStatistic.collect {
                binding.chipNumberItem.text = getString(R.string.number_quote_item, it.numberItem)
                binding.chipNumberAnime.text =
                    getString(R.string.number_quote_anime, it.numberDistinctAnime)
            }
        }

        binding.searchTextLayout.doOnTextChanged { input, _, _, _ ->
            quoteViewModel.searchWith(input.toString())
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateAdapter(items: List<QuoteGenericItem>) {
        quoteAdapter.submitList(items)
        if (binding.quoteRefresh.isRefreshing) {
            binding.quoteRefresh.isRefreshing = false
        }
        binding.motionLayout.transitionToStart()
    }

    private fun onItemClick(quoteItem: QuoteItem) {
        // TODO implement
    }
}