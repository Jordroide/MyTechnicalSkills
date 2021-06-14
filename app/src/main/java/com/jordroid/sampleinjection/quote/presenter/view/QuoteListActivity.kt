package com.jordroid.sampleinjection.quote.presenter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jordroid.sampleinjection.databinding.QuoteActivityBinding
import com.jordroid.sampleinjection.quote.presenter.model.QuoteItem
import com.jordroid.sampleinjection.quote.presenter.view.adapter.QuoteAdapter
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
            viewModel.getVehicles().flowOn(Dispatchers.IO).collect {
                updateAdapter(it)
            }
        }
    }

    private fun updateAdapter(items: List<QuoteItem>) {
        quoteAdapter.submitList(items)
        if (binding.quoteRefresh.isRefreshing) {
            binding.quoteRefresh.isRefreshing = false
        }
    }

    private fun onItemClick(quoteItem: QuoteItem) {
        // TODO implement
    }
}