package com.jordroid.showcase.quote.presenter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.jordroid.showcase.R
import com.jordroid.showcase.databinding.QuoteActivityBinding
import com.jordroid.showcase.quote.presenter.model.QuoteGenericItem
import com.jordroid.showcase.quote.presenter.model.QuoteItem
import com.jordroid.showcase.quote.presenter.view.adapter.QuoteAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import org.koin.android.ext.android.inject

class QuoteListActivity : AppCompatActivity() {

    private val viewModel: QuoteViewModel by inject()

    private val quoteAdapter = QuoteAdapter { item ->
        onItemClick(item)
    }

    private lateinit var binding: QuoteActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = QuoteActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.quoteRv.adapter = quoteAdapter
        binding.quoteRefresh.setOnRefreshListener {
            viewModel.fetchData()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getQuote().flowOn(Dispatchers.IO).collect {
                updateAdapter(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.quoteStatistic.collect {
                binding.chipNumberItem.text = getString(R.string.number_quote_item, it.numberItem)
                binding.chipNumberAnime.text = getString(R.string.number_quote_anime, it.numberDistinctAnime)
            }
        }

        binding.searchTextLayout.doOnTextChanged { input, _, _, _ ->
            viewModel.searchWith(input.toString())
        }
    }

    private fun updateAdapter(items: List<QuoteGenericItem>) {
        quoteAdapter.submitList(items)
        if (binding.quoteRefresh.isRefreshing) {
            binding.quoteRefresh.isRefreshing = false
        }
    }

    private fun onItemClick(quoteItem: QuoteItem) {
        // TODO implement
    }
}